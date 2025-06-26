package com.eit.gateway.websocket;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.eit.gateway.service.WebSocketService;



@Component
public class WebSocketHandler extends TextWebSocketHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);
    
    @Autowired
    private WebSocketService webSocketService;
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("WebSocket connection established: {}", session.getId());
        webSocketService.registerSession(session);
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        LOGGER.debug("Received message from session {}: {}", session.getId(), payload);
        
        try {
            JSONObject requestJson = new JSONObject(payload);
            String messageType = requestJson.optString("type", "");
            
            switch (messageType) {
                case "subscribe":
                    handleSubscription(session, requestJson);
                    break;
                    
                case "unsubscribe":
                    handleUnsubscription(session, requestJson);
                    break;
                    
                case "ping":
                    webSocketService.sendPongResponse(session);
                    break;
                    
                default:
                    LOGGER.warn("Unknown message type from session {}: {}", session.getId(), messageType);
                    webSocketService.sendErrorMessage(session, "Unknown message type: " + messageType);
            }
            
        } catch (Exception e) {
            LOGGER.error("Error processing message from session {}", session.getId(), e);
            webSocketService.sendErrorMessage(session, "Invalid message format");
        }
    }
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LOGGER.error("Transport error for session {}", session.getId(), exception);
        webSocketService.removeSession(session);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LOGGER.info("WebSocket connection closed for session {}: {}", session.getId(), status);
        webSocketService.removeSession(session);
    }
    
    /**
     * Handle subscription request
     * 
     * @param session The WebSocket session
     * @param requestJson The request JSON object
     */
    private void handleSubscription(WebSocketSession session, JSONObject requestJson) {
        String userId = requestJson.optString("userId", "");
        String entryPoint = requestJson.optString("entryPoint", "web");
        
        // Check if it's a vehicle subscription
        String vin = requestJson.optString("vin", "");
        if (!vin.isEmpty()) {
            boolean success = webSocketService.subscribeToVehicle(session, vin, entryPoint);
            if (!success) {
                webSocketService.sendErrorMessage(session, "Failed to subscribe to vehicle: " + vin + 
                                                 ". Either the vehicle doesn't exist or you don't have access.");
            }
            return;
        }
        
        // If only userId is provided, it's a user-only subscription
        if (!userId.isEmpty()) {
            boolean success = webSocketService.subscribeToUser(session, userId, entryPoint);
            if (!success) {
                webSocketService.sendErrorMessage(session, "Failed to subscribe to user: " + userId);
            }
            return;
        }
        
        // If neither VIN nor userId is provided
        webSocketService.sendErrorMessage(session, "Either VIN or userId is required for subscription");
    }
    
    /**
     * Handle unsubscription request
     * 
     * @param session The WebSocket session
     * @param requestJson The request JSON object
     */
    private void handleUnsubscription(WebSocketSession session, JSONObject requestJson) {
        // Check for specific unsubscription type
        String subscriptionType = requestJson.optString("subscriptionType", "");
        
        if (!subscriptionType.isEmpty()) {
            webSocketService.unsubscribeByType(session, subscriptionType);
            return;
        }
        
        // Check if unsubscribing from a specific VIN
        String vin = requestJson.optString("vin", "");
        if (!vin.isEmpty()) {
            // Check if this session is actually subscribed to this VIN
            String sessionVin = webSocketService.getSessionVehicle(session);
            if (sessionVin != null && sessionVin.equals(vin)) {
                webSocketService.unsubscribeFromVehicle(session);
            } else {
                webSocketService.sendErrorMessage(session, "Not subscribed to vehicle: " + vin);
            }
            return;
        }
        
        // Check if unsubscribing from a specific user
        String userId = requestJson.optString("userId", "");
        if (!userId.isEmpty()) {
            // Check if this session is actually subscribed to this user
            String sessionUser = webSocketService.getSessionUser(session);
            if (sessionUser != null && sessionUser.equals(userId)) {
                webSocketService.unsubscribeFromUser(session);
            } else {
                webSocketService.sendErrorMessage(session, "Not subscribed to user: " + userId);
            }
            return;
        }
        
        // If no specifics provided, unsubscribe from all
        webSocketService.unsubscribeByType(session, null);
    }
}