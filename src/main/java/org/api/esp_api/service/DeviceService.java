package org.api.esp_api.service;

import org.api.esp_api.model.Device;
import org.api.esp_api.model.DeviceRegisterRequest;
import org.api.esp_api.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public String registerDevice(DeviceRegisterRequest deviceRegisterRequest) {
        String deviceId = generateUniqueId();
        deviceRepository.save(new Device(deviceId, deviceRegisterRequest.getName(), deviceRegisterRequest.getType()));
        return deviceId;
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}
