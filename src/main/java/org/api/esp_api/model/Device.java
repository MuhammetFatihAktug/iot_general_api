package org.api.esp_api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Device {
    @Id
    private String id;
    private String name;
    private String type;
    private String registeredAt;

    public Device() {

    }

    @PrePersist
    protected void onCreate() {
        this.registeredAt = LocalDateTime.now().toString();
    }

    public Device(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(String registeredAt) {
        this.registeredAt = registeredAt;
    }
}