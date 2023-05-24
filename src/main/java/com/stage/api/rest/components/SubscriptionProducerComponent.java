package com.stage.api.rest.components;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.stage.SubscriptionRegistered;
import com.stage.api.rest.properties.KafkaTopicProperties;

@Component
public class SubscriptionProducerComponent {
    private final KafkaTemplate<String, SubscriptionRegistered> kafkaTemplate;
    private final KafkaTopicProperties kafkaTopicProperties;

    public SubscriptionProducerComponent(KafkaTemplate<String, SubscriptionRegistered> kafkaTemplate, KafkaTopicProperties kafkaTopicProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicProperties = kafkaTopicProperties;
    }

    public void sendMessage(SubscriptionRegistered message) {
        kafkaTemplate.send(kafkaTopicProperties.getTopicSubscription(), message);
    }
}
