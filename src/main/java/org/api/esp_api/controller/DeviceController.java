package org.api.esp_api.controller;

import org.api.esp_api.model.Device;
import org.api.esp_api.model.DeviceRegisterRequest;
import org.api.esp_api.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerDevice(@RequestBody DeviceRegisterRequest deviceRegisterRequest) {
        String deviceId = deviceService.registerDevice(deviceRegisterRequest);
        return ResponseEntity.ok(deviceId);
    }
}
