package com.stage.api.rest.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.kafka.consumer")
public class ConsumerProperties {

	@Value("bootstrap.servers")
	private String bootstrapServer;
	private String specificAvroReaderConfig;
	private String groupId;
	private String autoOffsetReset;
	private String keyDeserializer;
	private String valueDeserializer;
	@Value("schema-registry")
	private String schemaRegistry;
	
	public String getBootstrapServer() {
		return bootstrapServer;
	}
	
	public void setBootstrapServer(String bootstrapServer) {
		this.bootstrapServer = bootstrapServer;
	}
	
	public String getSpecificAvroReaderConfig() {
		return specificAvroReaderConfig;
	}

	public void setSpecificAvroReaderConfig(String specificAvroReaderConfig) {
		this.specificAvroReaderConfig = specificAvroReaderConfig;
	}

	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getAutoOffsetReset() {
		return autoOffsetReset;
	}
	
	public void setAutoOffsetReset(String autoOffsetReset) {
		this.autoOffsetReset = autoOffsetReset;
	}
	
	public String getKeyDeserializer() {
		return keyDeserializer;
	}
	
	public void setKeyDeserializer(String keyDeserializer) {
		this.keyDeserializer = keyDeserializer;
	}
	
	public String getValueDeserializer() {
		return valueDeserializer;
	}
	
	public void setValueDeserializer(String valueDeserializer) {
		this.valueDeserializer = valueDeserializer;
	}
	
	public String getSchemaRegistry() {
		return schemaRegistry;
	}
	
	public void setSchemaRegistry(String schemaRegistry) {
		this.schemaRegistry = schemaRegistry;
	}	
}
