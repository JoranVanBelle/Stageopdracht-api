package com.stage.api.rest.components;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.stage.FeedbackGiven;
import com.stage.api.rest.properties.KafkaTopicProperties;

@Component
public class FeedbackProducerComponent {
	
    private final KafkaTemplate<String, FeedbackGiven> kafkaTemplate;
    private final KafkaTopicProperties kafkaTopicProperties;

    public FeedbackProducerComponent(KafkaTemplate<String, FeedbackGiven> kafkaTemplate, KafkaTopicProperties kafkaTopicProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicProperties = kafkaTopicProperties;
    }

    public void sendMessage(FeedbackGiven message) {
        kafkaTemplate.send(kafkaTopicProperties.getTopicFeedback(), message);
    }
	
}
