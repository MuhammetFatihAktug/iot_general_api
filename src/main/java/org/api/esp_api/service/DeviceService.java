package org.api.esp_api.service;

import org.api.esp_api.dto.DeviceRegisterRequestDTO;
import org.api.esp_api.entities.Device;
import org.api.esp_api.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public String registerDevice(DeviceRegisterRequestDTO deviceRegisterRequestDTO) {
        String deviceId = generateUniqueId();

        Device device = Device.builder().name(deviceRegisterRequestDTO.name())
                .id(deviceId)
                .type(deviceRegisterRequestDTO.type())
                .build();

        deviceRepository.save(device);

        return deviceId;
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}
