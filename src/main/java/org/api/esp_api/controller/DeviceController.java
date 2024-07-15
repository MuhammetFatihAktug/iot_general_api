package org.api.esp_api.controller;

import org.api.esp_api.dto.DeviceRegisterRequestDTO;
import org.api.esp_api.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerDevice(@RequestBody DeviceRegisterRequestDTO deviceRegisterRequestDTO) {
        String deviceId = deviceService.registerDevice(deviceRegisterRequestDTO);
        return ResponseEntity.ok(deviceId);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteDevice(@RequestParam String id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok("Device Deleted.");
    }
}
