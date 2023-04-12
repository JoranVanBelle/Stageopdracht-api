package com.stage.api.rest.components;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;

import com.stage.FeedbackGiven;
import com.stage.api.rest.properties.ConsumerProperties;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

@Component
public class FeedbackConsumerComponent {
	
	private final ConsumerProperties properties;
	
	public FeedbackConsumerComponent(ConsumerProperties properties) {
		this.properties = properties;
	}
	
    @Bean
    public ConsumerFactory<String, FeedbackGiven> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        
        props.put(
          ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          properties.getBootstrapServer());
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG,
        		properties.getSpecificAvroReaderConfig());
        props.put(
          ConsumerConfig.GROUP_ID_CONFIG, 
          properties.getGroupId());
        props.put(
        	ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
        	properties.getAutoOffsetReset());
        props.put(
          ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 
          properties.getKeyDeserializer());
        props.put(
          ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
          properties.getValueDeserializer());
        props.put(
        	AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
        	properties.getSchemaRegistry());
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FeedbackGiven> 
      kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FeedbackGiven> factory =
          new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
