package com.stage.api.rest.components;


import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import com.stage.FeedbackGiven;
import com.stage.SignOutRegistered;
import com.stage.SubscriptionRegistered;
import com.stage.api.rest.properties.ProducerProperties;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;

@Component
public class SubscriptionProducerComponent {
	
	private final ProducerProperties properties;
	
	public SubscriptionProducerComponent(ProducerProperties properties) {
		this.properties = properties;
	}
	
    @Bean
    public ProducerFactory<String, SubscriptionRegistered> subscriptionProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        
        configProps.put(
          ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          properties.getBootstrapServer());
        configProps.put(
        	ProducerConfig.ACKS_CONFIG,
        	properties.getAcks());
        configProps.put(
            	ProducerConfig.RETRIES_CONFIG,
            	properties.getRetries());
        configProps.put(
          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
          properties.getKeySerializer());
        configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
          properties.getValueSerializer());
        configProps.put(
        	AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
        	properties.getSchemaRegistry());
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ProducerFactory<String, SignOutRegistered> signOutProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        
        configProps.put(
          ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          properties.getBootstrapServer());
        configProps.put(
        	ProducerConfig.ACKS_CONFIG,
        	properties.getAcks());
        configProps.put(
            	ProducerConfig.RETRIES_CONFIG,
            	properties.getRetries());
        configProps.put(
          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
          properties.getKeySerializer());
        configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
          properties.getValueSerializer());
        configProps.put(
        	AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
        	properties.getSchemaRegistry());
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    
    @Bean
    public KafkaTemplate<String, SubscriptionRegistered> kafkaSubscribeTemplate(ProducerFactory<String, SubscriptionRegistered> subscriptionProducerFactory) {
        return new KafkaTemplate<>(subscriptionProducerFactory);
    }
    
    @Bean
    public KafkaTemplate<String, SignOutRegistered> kafkaSignOutTemplate(ProducerFactory<String, SignOutRegistered> signOutProducerFactory) {
        return new KafkaTemplate<>(signOutProducerFactory);
    }
}
