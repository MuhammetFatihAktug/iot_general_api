package org.api.esp_api.repository;

import org.api.esp_api.model.Device;
import org.api.esp_api.model.DeviceRegisterRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}
