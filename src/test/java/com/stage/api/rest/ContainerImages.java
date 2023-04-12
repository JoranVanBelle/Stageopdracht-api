package com.stage.api.rest;

import org.testcontainers.utility.DockerImageName;

public interface ContainerImages {

	DockerImageName KAFKA_TEST_IMAGE = DockerImageName.parse("confluentinc/cp-kafka:6.2.1");
	DockerImageName POSTGRES_TEST_IMAGE = DockerImageName.parse("postgres:9.6.12");
	
}
