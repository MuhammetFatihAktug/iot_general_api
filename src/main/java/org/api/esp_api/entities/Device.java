package org.api.esp_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table()
@Builder
public class Device {
    @Id
    private String id;
    private String name;
    private String type;
    private String registeredAt;

    @PrePersist
    protected void onCreate() {
        this.registeredAt = LocalDateTime.now().toString();
    }


}