package com.stage.api.rest.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.stage.FeedbackGiven;
import com.stage.SignOutRegistered;
import com.stage.SubscriptionRegistered;
import com.stage.api.rest.properties.KafkaTopicProperties;

@Configuration
@EnableConfigurationProperties(KafkaTopicProperties.class)
public class KafkaTopicConfiguration {

	private final KafkaTopicProperties kafkaTopicProperties;
	private final KafkaProperties kafkaProperties;
	
	public KafkaTopicConfiguration(
			KafkaTopicProperties kafkaTopicProperties,
			KafkaProperties kafkaProperties
	) {
		this.kafkaTopicProperties = kafkaTopicProperties;
		this.kafkaProperties = kafkaProperties;
	}
	
    @Bean
    public KafkaAdmin kafkaAdmin(@Value("${spring.kafka.bootstrap-servers: localhost:9092}") String bootstrapServers) {
        return new KafkaAdmin(kafkaProperties.buildAdminProperties());
    }
    
    @Bean
    public NewTopic topicFeedback() {
         return new NewTopic(kafkaTopicProperties.getTopicFeedback(), 1, (short) 1);
    }
    
    @Bean
    public NewTopic topicSubscription() {
    	return new NewTopic(kafkaTopicProperties.getTopicSubscription(), 1, (short) 1);
    }
}
