package com.stage.api.rest.extension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class KafkaTestcontainer implements BeforeAllCallback {
	
	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));
		
		kafka.start();
		
		System.setProperty("spring.kafka.consumer.bootstrapServer", kafka.getBootstrapServers());
		System.setProperty("spring.kafka.producer.bootstrapServer", kafka.getBootstrapServers());
	}

}
