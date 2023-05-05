package com.stage.api.rest.components;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import com.stage.api.rest.properties.KafkaTopicProperties;
import com.stage.api.rest.properties.ProducerProperties;

@Component
public class KafkaTopicComponent {

	private final KafkaTopicProperties kafkaProperties;
	private final ProducerProperties prodProperties;
	
	public KafkaTopicComponent(
			KafkaTopicProperties kafkaProperties,
			ProducerProperties prodProperties
	) {
		this.kafkaProperties = kafkaProperties;
		this.prodProperties = prodProperties;
	}
	
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, prodProperties.getBootstrapServer());
        return new KafkaAdmin(configs);
    }
    
    @Bean
    public NewTopic topicFeedback() {
         return new NewTopic(kafkaProperties.getTopicFeedback(), 1, (short) 1);
    }
    
    @Bean
    public NewTopic topicSubscription() {
    	return new NewTopic(kafkaProperties.getTopicSubscription(), 1, (short) 1);
    }
	
}
