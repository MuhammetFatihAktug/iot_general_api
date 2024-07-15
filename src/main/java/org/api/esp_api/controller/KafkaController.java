package org.api.esp_api.controller;

import org.api.esp_api.service.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {
    private final KafkaProducerService kafkaProducerService;

    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("topicName") String topicName, @RequestParam("message") String message) {
        kafkaProducerService.sendMessage(message, topicName);
        return ResponseEntity.ok("Message sent to Kafka topic");
    }
}