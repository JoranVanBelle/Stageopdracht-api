package com.stage.api.rest.prodcon;

import java.util.Optional;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stage.FeedbackGiven;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;

@Configuration
public class FeedbackConsumerConfig {

	@Value("${api.feedback.schemaregistryurl}")
	private String schemaRegistryUrl;
	
	@Bean
    public KafkaConsumer<String, FeedbackGiven> kafkaConsumer() {
        return new KafkaConsumer<String, FeedbackGiven>(consumerConfig());
    }
	
	private Properties consumerConfig() {
		Properties settings = new Properties();
		settings.put("bootstrap.servers", "http://localhost:9092");
		settings.put("group.id", "api_consumer");
        settings.put("auto.offset.reset", "earliest");
        settings.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        settings.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        settings.put("specific.avro.reader", "true");
		settings.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, Optional.ofNullable(schemaRegistryUrl).orElseThrow(() -> new IllegalArgumentException("SCHEMA_REGISTRY_URL is required")));
		return settings;
	}
}
