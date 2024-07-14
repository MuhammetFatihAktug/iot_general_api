package org.api.esp_api.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "topic_name", groupId = "my-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
