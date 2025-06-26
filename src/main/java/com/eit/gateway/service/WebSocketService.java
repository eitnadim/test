package com.eit.gateway.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.eit.gateway.entity.VehicleAlerts;

@Service
public class WebSocketService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketService.class);

	// Maps to store active sessions with their subscriptions
	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
	private final Map<String, String> sessionVehicleMap = new ConcurrentHashMap<>();
	private final Map<String, String> sessionUserMap = new ConcurrentHashMap<>();
	private final Map<String, String> sessionEntryPointMap = new ConcurrentHashMap<>();

	// Maps to track user-vehicle relationships
	private final Map<String, Set<String>> userVehicleMap = new ConcurrentHashMap<>();
	private final Map<String, Boolean> validVehicles = new ConcurrentHashMap<>();

	public void registerSession(WebSocketSession session) {
		LOGGER.info("Registering session: {}", session.getId());
		sessions.put(session.getId(), session);
	}

	/**
	 * Remove a WebSocket session
	 * 
	 * @param session The WebSocket session to remove
	 */
	public void removeSession(WebSocketSession session) {
		LOGGER.info("Removing session: {}", session.getId());
		sessions.remove(session.getId());
		sessionVehicleMap.remove(session.getId());
		sessionUserMap.remove(session.getId());
		// sessionEntryPointMap.remove(session.getId());
	}

	/**
	 * Subscribe a session to a user
	 * 
	 * @param session    The WebSocket session
	 * @param userId     The user ID
	 * @param entryPoint The entry point (web, mobile, etc.)
	 * @return true if subscription was successful
	 */
	public boolean subscribeToUser(WebSocketSession session, String userId, String entryPoint) {
		if (userId == null || userId.isEmpty()) {
			LOGGER.warn("Attempted to subscribe session {} to null or empty userId", session.getId());
			return false;
		}

		LOGGER.info("Session {} subscribing to user {}", session.getId(), userId);

		// Here you would validate the user exists in your system
		// boolean userExists = userRepository.existsById(userId);
		boolean userExists = true; // For demo - replace with actual validation

		if (!userExists) {
			LOGGER.warn("Attempted to subscribe to non-existent user: {}", userId);
			return false;
		}

		sessionUserMap.put(session.getId(), userId);
//		sessionEntryPointMap.put(session.getId(), entryPoint);

		// Send confirmation
		JSONObject response = new JSONObject();
		response.put("type", "subscribed");
		response.put("userId", userId);
		response.put("subscriptionType", "user");
		sendMessage(session, response.toString());

		return true;
	}

	/**
	 * Check if a vehicle exists and user has access to it
	 * 
	 * @param vin    The vehicle VIN
	 * @param userId The user ID
	 * @return true if vehicle exists and user has access
	 */
	private boolean validateVehicleAccess(String vin) {
		
			LOGGER.info("Validating vehicle access for userId: {} and VIN: {}", vin);
			return true;
		
	}

	/**
	 * Subscribe a session to a vehicle
	 * 
	 * @param session    The WebSocket session
	 * @param vin        The vehicle VIN
	 * @param userId     The user ID
	 * @param entryPoint The entry point (web, mobile, etc.)
	 * @return true if subscription was successful
	 */
	public boolean subscribeToVehicle(WebSocketSession session, String vin, String entryPoint) {
		if (vin == null || vin.isEmpty()) {
			LOGGER.warn("Attempted to subscribe session {} to null or empty VIN", session.getId());
			return false;
		}

		// First check if user has access to this vehicle
//		if (!validateVehicleAccess(vin)) {
//			LOGGER.warn("User {} does not have access to vehicle {}", vin);
//			return false;
//		}

		LOGGER.info("Session {} subscribing to vehicle {} for user {}", session.getId(), vin);

		// Then add vehicle subscription
		sessionVehicleMap.put(session.getId(), vin);

		// Send confirmation
		JSONObject response = new JSONObject();
		response.put("type", "subscribed");
		response.put("vin", vin);
		response.put("subscriptionType", "vehicle");
		sendMessage(session, response.toString());

		return true;
	}

	/**
	 * Unsubscribe a session from a vehicle
	 * 
	 * @param session The WebSocket session
	 */
	public void unsubscribeFromVehicle(WebSocketSession session) {
		String sessionId = session.getId();
		LOGGER.info("Session {} unsubscribing from vehicle", sessionId);

		// Check if this is a vehicle subscription
		if (sessionVehicleMap.containsKey(sessionId)) {
			sessionVehicleMap.remove(sessionId);

			// Send confirmation
			JSONObject response = new JSONObject();
			response.put("type", "unsubscribed");
			response.put("subscriptionType", "vehicle");
			sendMessage(session, response.toString());
		} else {
			LOGGER.warn("Attempted to unsubscribe from vehicle for session without vehicle subscription: {}",
					sessionId);
		}
	}

	/**
	 * Unsubscribe a session from a user
	 * 
	 * @param session The WebSocket session
	 */
	public void unsubscribeFromUser(WebSocketSession session) {
		String sessionId = session.getId();
		LOGGER.info("Session {} unsubscribing from user", sessionId);

		// Check if this is a user subscription
		if (sessionUserMap.containsKey(sessionId)) {
			sessionUserMap.remove(sessionId);

			// Also remove vehicle subscription as it depends on user
			if (sessionVehicleMap.containsKey(sessionId)) {
				sessionVehicleMap.remove(sessionId);
				LOGGER.info("Also removing vehicle subscription for session {}", sessionId);
			}

			// Send confirmation
			JSONObject response = new JSONObject();
			response.put("type", "unsubscribed");
			response.put("subscriptionType", "user");
			sendMessage(session, response.toString());
		} else {
			LOGGER.warn("Attempted to unsubscribe from user for session without user subscription: {}", sessionId);
		}
	}

	/**
	 * Unsubscribe a session based on subscription type
	 * 
	 * @param session          The WebSocket session
	 * @param subscriptionType The type of subscription ("vehicle" or "user")
	 */
	public void unsubscribeByType(WebSocketSession session, String subscriptionType) {
		if ("vehicle".equalsIgnoreCase(subscriptionType)) {
			unsubscribeFromVehicle(session);
		} else if ("user".equalsIgnoreCase(subscriptionType)) {
			unsubscribeFromUser(session);
		} else if("all".equalsIgnoreCase(subscriptionType)) {
			// If type not specified, unsubscribe from both
			sessionVehicleMap.remove(session.getId());
			sessionUserMap.remove(session.getId());
//			sessionEntryPointMap.remove(session.getId());

			// Send generic unsubscribe confirmation
			JSONObject response = new JSONObject();
			response.put("type", "unsubscribed");
			response.put("subscriptionType", "all");
			sendMessage(session, response.toString());
		}
	}

	/**
	 * Check if a session has a vehicle subscription
	 * 
	 * @param session The WebSocket session
	 * @return true if the session has a vehicle subscription
	 */
	public boolean hasVehicleSubscription(WebSocketSession session) {
		return sessionVehicleMap.containsKey(session.getId());
	}

	/**
	 * Check if a session has a user subscription
	 * 
	 * @param session The WebSocket session
	 * @return true if the session has a user subscription
	 */
	public boolean hasUserSubscription(WebSocketSession session) {
		return sessionUserMap.containsKey(session.getId());
	}

	/**
	 * Get the vehicle VIN for a session if subscribed
	 * 
	 * @param session The WebSocket session
	 * @return the VIN or null if not subscribed to any vehicle
	 */
	public String getSessionVehicle(WebSocketSession session) {
		return sessionVehicleMap.get(session.getId());
	}

	/**
	 * Get the user ID for a session if subscribed
	 * 
	 * @param session The WebSocket session
	 * @return the user ID or null if not subscribed to any user
	 */
	public String getSessionUser(WebSocketSession session) {
		return sessionUserMap.get(session.getId());
	}

	/**
	 * Send user data to subscribed sessions
	 * 
	 * @param userId The user ID
	 * @param data   The JSON data to send
	 */
	public void sendUserData(String userId, String data) {
		if (data == null || data.isEmpty() || userId == null || userId.isEmpty()) {
			LOGGER.warn("Cannot send empty user data or invalid userId");
			return;
		}

		LOGGER.debug("Sending user data for user: {}", userId);

		int recipientCount = 0;

		for (Map.Entry<String, String> entry : sessionUserMap.entrySet()) {
			String sessionId = entry.getKey();
			String subscribedUserId = entry.getValue();

			// Skip if not subscribed to this user
			if (!userId.equals(subscribedUserId)) {
				continue;
			}

			WebSocketSession session = sessions.get(sessionId);

			// Skip if session is no longer active
			if (session == null || !session.isOpen()) {
				continue;
			}

			try {
				// Create the response with type
				JSONObject response = new JSONObject();
				response.put("type", "userData");
				response.put("data", new JSONObject(data));
				response.put("timestamp", System.currentTimeMillis());

				// Send to the client
				session.sendMessage(new TextMessage(response.toString()));
				recipientCount++;
			} catch (IOException e) {
				LOGGER.error("Error sending user data to session {}", sessionId, e);
				removeSession(session);
			}
		}

		LOGGER.debug("User data sent to {} WebSocket clients", recipientCount);
	}

	/**
	 * Send vehicle data to subscribed sessions
	 * 
	 * @param vin  The vehicle VIN
	 * @param data The JSON data to send
	 */
	public void sendVehicleData(String vin, String data) {
		if (data == null || data.isEmpty() || vin == null || vin.isEmpty()) {
			LOGGER.warn("Cannot send empty vehicle data or invalid VIN");
			return;
		}

		LOGGER.debug("Sending vehicle data for VIN: {}", vin);

		int recipientCount = 0;

		for (Map.Entry<String, String> entry : sessionVehicleMap.entrySet()) {
			String sessionId = entry.getKey();
			String subscribedVin = entry.getValue();

			// Skip if not subscribed to this vehicle
			if (!vin.equals(subscribedVin)) {
				continue;
			}

			WebSocketSession session = sessions.get(sessionId);

			// Skip if session is no longer active
			if (session == null || !session.isOpen()) {
				continue;
			}

			try {
				// Create the response with type
				JSONObject response = new JSONObject();
				response.put("type", "vehicleData");
				response.put("data", new JSONObject(data));
				response.put("timestamp", System.currentTimeMillis());

				// Send to the client
				session.sendMessage(new TextMessage(response.toString()));
				recipientCount++;
			} catch (IOException e) {
				LOGGER.error("Error sending vehicle data to session {}", sessionId, e);
				removeSession(session);
			}
		}

		LOGGER.debug("Vehicle data sent to {} WebSocket clients", recipientCount);
	}

	/**
	 * Send a message to WebSocket clients based on vehicle, user, and entry point
	 * 
	 * @param message    The JSON message to send
	 * @param vin        The vehicle VIN (can be null if sending to user sessions
	 *                   only)
	 * @param entryPoint The entry point (e.g., "VTS", "web", "mobile")
	 * @param userId     The user ID (can be null if sending to vehicle sessions
	 *                   only)
	 */
	public void sendMessageToWebSocket(String message, String vin, CopyOnWriteArrayList<VehicleAlerts> tempAlertsForVins, String userId) {
		if (message == null || message.isEmpty()) {
			LOGGER.warn("Cannot send empty message to WebSocket");
			return;
		}

		LOGGER.debug("Sending message to WebSocket clients - VIN: {}, , userId: {}", vin,
				userId);

		// Count of sessions that received the message
		int recipientCount = 0;

		// Find all sessions that match the criteria (vehicle, user, or both)
		for (String sessionId : sessions.keySet()) {
			// Get the session
			WebSocketSession session = sessions.get(sessionId);

			// Skip if session is no longer active
			if (session == null || !session.isOpen()) {
				continue;
			}

			boolean shouldSend = false;
			String messageType = "data"; // Default type
			Map<String, String> userSessionList = new HashMap<>();

			// Check if this is a vehicle message and session is subscribed to this vehicle
			if (vin != null && !vin.isEmpty()) {
				String sessionVin = sessionVehicleMap.getOrDefault(sessionId, "");
				if (vin.equals(sessionVin)) {
					shouldSend = true;
					messageType = "vehicleData";
				}
			}

			// Check if this is a user message and session is subscribed to this user
			if (!shouldSend && userId != null && !userId.isEmpty()) {
				String sessionUser = sessionUserMap.getOrDefault(sessionId, "");
				List<String> users = Arrays.asList(userId.split(","));
				for (String user : users) {
					LOGGER.debug("Check {} value {} ", user, sessionUser);
					if (user.equals(sessionUser)) {
					 LOGGER.debug("User {} is subscribed to session {}", user, sessionUser);	
						shouldSend = true;
//						messageType = "userData";
						userSessionList.put(user, "userData");
						LOGGER.debug("User {} is subscribed to session {}", sessionUser, sessionId);
//						break;
					}
				}
//	            if (userId.equals(sessionUser)) {
//	                shouldSend = true;
//	                messageType = "userData";
//	            }
			}

			// Check entry point if specified
//			if (shouldSend && entryPoint != null && !entryPoint.isEmpty()) {
//				String sessionEntryPoint = sessionEntryPointMap.getOrDefault(sessionId, "");
//				if (!entryPoint.equals(sessionEntryPoint)) {
//					shouldSend = false; // Entry point doesn't match
//				}
//			}

			// Skip if criteria don't match
			if (!shouldSend) {
				continue;
			}

			try {
				// Create the response with type
				JSONObject response = new JSONObject();
				response.put("type", messageType);
				response.put("data", new JSONObject(message));
				response.put("alerts", tempAlertsForVins);
				response.put("timestamp", System.currentTimeMillis());

				// Add VIN if this is vehicle data
				if (vin != null && !vin.isEmpty() && "vehicleData".equals(messageType)) {
					response.put("vin", vin);
				}

				// Add userId if this is user data
//	            if (userId != null && !userId.isEmpty() && "userData".equals(messageType)) {
//	                response.put("userId", userId);
//	            }
				if (userSessionList.size() > 0) {
					LOGGER.info("User session list size: {}", userSessionList.size());
					for (Map.Entry<String, String> entry : userSessionList.entrySet()) {
						LOGGER.error("User {} is subscribed to session {}", entry.getKey(), sessionId);
						if (entry.getValue().equals("userData")) {
							response.put("userId", entry.getKey());
							session.sendMessage(new TextMessage(response.toString()));
							recipientCount++;
						}
					}

				}

				// Send to the client
				if ("vehicleData".equals(messageType)) {
					session.sendMessage(new TextMessage(response.toString()));
					recipientCount++;
				}

				LOGGER.debug("Sent {} message to session {}", messageType, sessionId);
			} catch (IOException e) {
				LOGGER.error("Error sending message to session {}", sessionId, e);

				// Remove problematic session
				removeSession(session);
			}
		}

		LOGGER.debug("Message sent to {} WebSocket clients", recipientCount);
	}

	/**
	 * Send an error message to a session
	 * 
	 * @param session      The WebSocket session
	 * @param errorMessage The error message
	 */
	public void sendErrorMessage(WebSocketSession session, String errorMessage) {
		JSONObject error = new JSONObject();
		error.put("type", "error");
		error.put("message", errorMessage);
		sendMessage(session, error.toString());
	}

	/**
	 * Send a pong response to a ping request
	 * 
	 * @param session The WebSocket session
	 */
	public void sendPongResponse(WebSocketSession session) {
		JSONObject response = new JSONObject();
		response.put("type", "pong");
		response.put("timestamp", System.currentTimeMillis());
		sendMessage(session, response.toString());
	}

	/**
	 * Send a message to a session
	 * 
	 * @param session The WebSocket session
	 * @param message The message text
	 */
	private void sendMessage(WebSocketSession session, String message) {
		try {
			if (session.isOpen()) {
				session.sendMessage(new TextMessage(message));
			} else {
				LOGGER.warn("Attempted to send message to closed session: {}", session.getId());
				removeSession(session);
			}
		} catch (IOException e) {
			LOGGER.error("Error sending message to session: {}", session.getId(), e);
			removeSession(session);
		}
	}

	/**
	 * Get count of active sessions
	 * 
	 * @return Number of active sessions
	 */
	public int getActiveSessionCount() {
		return sessions.size();
	}

	/**
	 * Get count of active vehicle subscriptions
	 * 
	 * @return Number of active vehicle subscriptions
	 */
	public int getActiveVehicleSubscriptionCount() {
		return sessionVehicleMap.size();
	}

	/**
	 * Get count of active user subscriptions
	 * 
	 * @return Number of active user subscriptions
	 */
	public int getActiveUserSubscriptionCount() {
		return sessionUserMap.size();
	}

	/**
	 * Broadcast vehicle data updates to all subscribed sessions Scheduled to run
	 * every 5 seconds by default
	 */
	@Scheduled(fixedRate = 5000)
	public void broadcastVehicleUpdates() {
		if (sessions.isEmpty()) {
			return; // No active sessions
		}

		LOGGER.debug("Broadcasting vehicle updates to {} sessions", sessions.size());

		for (Map.Entry<String, WebSocketSession> entry : sessions.entrySet()) {
			String sessionId = entry.getKey();
			WebSocketSession session = entry.getValue();

			// Skip if no vehicle subscription
			if (!sessionVehicleMap.containsKey(sessionId)) {
				continue;
			}

			if (!session.isOpen()) {
				LOGGER.warn("Session closed during broadcast: {}", sessionId);
				removeSession(session);
				continue;
			}

			// Here you would fetch and send the latest vehicle data
			// String vin = sessionVehicleMap.get(sessionId);
			// String userId = sessionUserMap.getOrDefault(sessionId, "system");
			// String entryPoint = sessionEntryPointMap.getOrDefault(sessionId, "web");
			// sendVehicleDataUpdate(session, vin);
		}
	}

	/**
	 * Get count of total active subscriptions (vehicle or user)
	 * 
	 * @return Number of active subscriptions
	 */
	public int getActiveSubscriptionCount() {
		// Get unique session IDs that have either type of subscription
		Set<String> subscribedSessions = new HashSet<>();
		subscribedSessions.addAll(sessionVehicleMap.keySet());
		subscribedSessions.addAll(sessionUserMap.keySet());

		return subscribedSessions.size();
	}
}