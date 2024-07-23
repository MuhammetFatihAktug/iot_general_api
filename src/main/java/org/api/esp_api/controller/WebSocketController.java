package org.api.esp_api.controller;

import lombok.AllArgsConstructor;
import org.api.esp_api.entities.Device;
import org.api.esp_api.model.WebSocketInfo;
import org.api.esp_api.repository.DeviceRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/ws", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class WebSocketController {
    private final DeviceRepository repository;

    @GetMapping("/getWebSocketInfo")
    public ResponseEntity<WebSocketInfo> getWebSocketInfo(@RequestParam String id) {
        Optional<Device> optional = repository.findById(id);
        String wsURL = "unknown";

        if (optional.isPresent()) {
            Device device = optional.get();
            wsURL = device.getName() + "/" + device.getType();
        }
        return ResponseEntity.ok(WebSocketInfo.builder()
                .server("192.168.1.5")
                .port(8080)
                .url("/ws/data/" + wsURL)
                .build());
    }
}