package com.stage.api.rest.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.KafkaContainer;

import io.strimzi.test.container.StrimziKafkaContainer;

public class KafkaTestcontainer implements BeforeAllCallback, AfterAllCallback {
	
	private StrimziKafkaContainer strimziKafkaContainer;
	
	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		
		strimziKafkaContainer = new StrimziKafkaContainer()
		        .withBootstrapServers(container -> String.format("PLAINTEXT://%s:%s", container.getHost(), container.getMappedPort(9092)))
		        .withPort(9092)
		        .waitForRunning();
		
		strimziKafkaContainer.start();
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		// TODO Auto-generated method stub
		strimziKafkaContainer.close();
	}
	
	
}
