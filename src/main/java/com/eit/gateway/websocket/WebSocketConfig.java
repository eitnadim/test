package com.eit.gateway.websocket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
@EnableScheduling
public class WebSocketConfig implements WebSocketConfigurer {
    
    @Autowired
    private WebSocketHandler webSocketHandler;
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Register the WebSocket handler with the endpoint path
        registry.addHandler(webSocketHandler, "/mvt/vehicle")
                .setAllowedOrigins("*"); // For production, restrict to specific origins
    }
    
    /**
     * Configure WebSocket container settings
     */
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        
        // Set buffer sizes
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        
        // Set timeouts (in milliseconds)
        container.setMaxSessionIdleTimeout(60000L); // 1 minute idle timeout
        
        return container;
    }
}