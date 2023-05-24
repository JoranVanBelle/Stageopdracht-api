package com.stage.api.rest.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.kafka")
public class KafkaTopicProperties {
	private String topicFeedback;
	private String topicSubscription;

	public String getTopicFeedback() {
		return topicFeedback;
	}

	public void setTopicFeedback(String topicFeedback) {
		this.topicFeedback = topicFeedback;
	}
	
	public String getTopicSubscription() {
		return topicSubscription;
	}
	
	public void setTopicSubscription(String topicSubscription) {
		this.topicSubscription = topicSubscription;
	}
}
