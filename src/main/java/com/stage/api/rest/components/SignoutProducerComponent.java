package com.stage.api.rest.components;

import org.springframework.kafka.core.KafkaTemplate;

import com.stage.SignOutRegistered;
import com.stage.api.rest.properties.KafkaTopicProperties;

public class SignoutProducerComponent {
    private final KafkaTemplate<String, SignOutRegistered> kafkaTemplate;
    private final KafkaTopicProperties kafkaTopicProperties;

    public SignoutProducerComponent(KafkaTemplate<String, SignOutRegistered> kafkaTemplate, KafkaTopicProperties kafkaTopicProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicProperties = kafkaTopicProperties;
    }

    public void sendMessage(SignOutRegistered message) {
        kafkaTemplate.send(kafkaTopicProperties.getTopicSubscription(), message);
    }
}
