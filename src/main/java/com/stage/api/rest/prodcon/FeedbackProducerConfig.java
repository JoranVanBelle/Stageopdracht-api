package com.stage.api.rest.prodcon;

import java.util.Optional;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stage.FeedbackGiven;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;

@Configuration
public class FeedbackProducerConfig {
	
	@Value("${api.feedback.schemaregistryurl}")
	private String schemaRegistryUrl;

	@Bean
    public KafkaProducer<String, FeedbackGiven> kafkaProducer() {
        return new KafkaProducer<String, FeedbackGiven>(producerConfig());
    }
	
	private Properties producerConfig() {
		Properties settings = new Properties();
		settings.put("bootstrap.servers", "http://localhost:9092");
        settings.put("acks", "all");
        settings.put("retries", 0);
        settings.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        settings.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
		settings.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, Optional.ofNullable(schemaRegistryUrl).orElseThrow(() -> new IllegalArgumentException("SCHEMA_REGISTRY_URL is required")));
		return settings;
	}
	
}
