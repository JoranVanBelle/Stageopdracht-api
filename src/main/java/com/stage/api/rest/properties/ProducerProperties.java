package com.stage.api.rest.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@ConfigurationProperties(prefix="spring.kafka.producer")
public class ProducerProperties {
	
	private String bootstrapServer;
	private String acks;
	private int retries;
	private String keySerializer;
	private String valueSerializer;
	private String schemaRegistry;
	
	public String getBootstrapServer() {
		return bootstrapServer;
	}
	
	public void setBootstrapServer(String bootstrapServer) {
		this.bootstrapServer = bootstrapServer;
	}
	
	public String getAcks() {
		return acks;
	}
	
	public void setAcks(String acks) {
		this.acks = acks;
	}
	
	public int getRetries() {
		return retries;
	}
	
	public void setRetries(int retries) {
		this.retries = retries;
	}
	
	public String getKeySerializer() {
		return keySerializer;
	}
	
	public void setKeySerializer(String keySerializer) {
		this.keySerializer = keySerializer;
	}
	
	public String getValueSerializer() {
		return valueSerializer;
	}
	
	public void setValueSerializer(String valueSerializer) {
		this.valueSerializer = valueSerializer;
	}
	
	public String getSchemaRegistry() {
		return schemaRegistry;
	}
	public void setSchemaRegistry(String schemaRegistry) {
		this.schemaRegistry = schemaRegistry;
	}
}
