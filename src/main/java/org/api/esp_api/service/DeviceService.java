package org.api.esp_api.service;

import org.api.esp_api.entities.Device;
import org.api.esp_api.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public void saveDevice(String ipAddress, Map<String, Object> attributes) {
        Device existingDevice = deviceRepository.findByIpAddress(ipAddress);
        if (existingDevice != null) {
            existingDevice.setName(attributes.get("name").toString());
            existingDevice.setType(attributes.get("type").toString());
            existingDevice.setStatus("active");
            deviceRepository.save(existingDevice);
        } else {
            Device device = Device.builder()
                    .ipAddress(ipAddress)
                    .name(attributes.get("name").toString())
                    .type(attributes.get("type").toString())
                    .status("active")
                    .build();
            deviceRepository.save(device);
        }
    }

    @Transactional
    public void deleteDevice(String ipAddress) {
        deviceRepository.deleteDeviceByIpAddress(ipAddress);
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}