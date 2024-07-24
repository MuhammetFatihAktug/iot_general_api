package org.api.esp_api.service;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaTopicService {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    private AdminClient adminClient;

    public KafkaTopicService(@Value("${kafka.bootstrap-servers}") String bootstrapServers) {
        Map<String, Object> config = Collections.singletonMap(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        this.adminClient = AdminClient.create(config);
    }

    public void createTopicIfNotExists(String topicName) throws ExecutionException, InterruptedException {
        ListTopicsResult topics = adminClient.listTopics();
        Set<String> topicNames = topics.names().get();

        if (!topicNames.contains(topicName)) {
            try {
                adminClient.createTopics(Collections.singleton(new NewTopic(topicName, 1, (short) 1))).all().get();
                System.out.println("Topic created: " + topicName);
            } catch (TopicExistsException e) {
                // Topic zaten mevcut
            }
        }
    }
}