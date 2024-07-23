package org.api.esp_api.config;

import org.api.esp_api.service.DeviceService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final DeviceService deviceService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Map<String, Long> lastMessageTime = new ConcurrentHashMap<>();
    private final long TIMEOUT_THRESHOLD;

    public MyWebSocketHandler(DeviceService deviceService, long timeoutThreshold) {
        this.deviceService = deviceService;
        this.TIMEOUT_THRESHOLD = timeoutThreshold;
        startTimeoutChecker();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
        String ipAddress = remoteAddress != null ? remoteAddress.getAddress().getHostAddress() : null;

        String uri = Objects.requireNonNull(session.getUri()).toString();
        Map<String, Object> attributes = getAttributes(uri);

        System.out.println("Connected: " + ipAddress);
        System.out.println("Attributes: " + attributes);

        deviceService.saveDevice(ipAddress, attributes);
        sessions.put(ipAddress, session);
        lastMessageTime.put(ipAddress, System.currentTimeMillis());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
        String ipAddress = remoteAddress != null ? remoteAddress.getAddress().getHostAddress() : null;

        String payload = message.getPayload();
        session.sendMessage(new TextMessage("Data: " + payload));

        if (ipAddress != null) {
            lastMessageTime.put(ipAddress, System.currentTimeMillis());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
        String ipAddress = remoteAddress != null ? remoteAddress.getAddress().getHostAddress() : null;

        sessions.remove(ipAddress);
        lastMessageTime.remove(ipAddress);
        deviceService.deleteDevice(ipAddress);
    }

    private void startTimeoutChecker() {
        scheduler.scheduleAtFixedRate(() -> {
            long currentTime = System.currentTimeMillis();
            for (Map.Entry<String, Long> entry : lastMessageTime.entrySet()) {
                if (currentTime - entry.getValue() > TIMEOUT_THRESHOLD) {
                    String ipAddress = entry.getKey();
                    WebSocketSession session = sessions.get(ipAddress);
                    if (session != null && session.isOpen()) {
                        try {
                            session.close(CloseStatus.SESSION_NOT_RELIABLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    sessions.remove(ipAddress);
                    lastMessageTime.remove(ipAddress);
                    deviceService.deleteDevice(ipAddress);
                }
            }
        }, TIMEOUT_THRESHOLD, TIMEOUT_THRESHOLD, TimeUnit.MILLISECONDS);
    }

    private Map<String, Object> getAttributes(String uri) {
        Map<String, Object> attributes = new HashMap<>();
        String[] params = uri.split("\\?");
        String[] queryParams = params.length > 1 ? params[1].split("&") : new String[0];
        for (String param : queryParams) {
            String[] keyValue = param.split("=");
            if (keyValue.length > 1) {
                attributes.put(keyValue[0], keyValue[1]);
            }
        }
        return attributes;
    }
}