package org.api.esp_api.config;

import org.api.esp_api.service.DeviceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final DeviceService deviceService;

    @Value("${websocket.timeout.threshold}")
    private long timeoutThreshold;

    public WebSocketConfig(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler(), "/ws/**").setAllowedOrigins("*");
    }

    @Bean
    public TextWebSocketHandler myWebSocketHandler() {
        return new MyWebSocketHandler(deviceService, timeoutThreshold);
    }
}