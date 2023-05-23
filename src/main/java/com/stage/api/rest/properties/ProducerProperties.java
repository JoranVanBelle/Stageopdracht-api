package com.stage.api.rest.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.kafka.producer")
public class ProducerProperties {
	
	@Value("bootstrap.servers")
	private String bootstrapServer;
	private String acks;
	private int retries;
	private String keySerializer;
	private String valueSerializer;
	@Value("schema-registry")
	private String schemaRegistry;
	@Value("value.subject.name.strategy")
	private String valueSubjectNameStrategy;
	
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
	
	public String getValueSubjectNameStrategy() {
		return valueSubjectNameStrategy;
	}
	
	public void setValueSubjectNameStrategy(String valueSubjectNameStrategy) {
		this.valueSubjectNameStrategy = valueSubjectNameStrategy;
	}
}
