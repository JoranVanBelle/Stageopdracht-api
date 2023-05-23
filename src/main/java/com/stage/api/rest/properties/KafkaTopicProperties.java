package com.stage.api.rest.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.kafka")
public class KafkaTopicProperties {
	private String topicFeedback;
	private String topicSubscription;
	@Value("bootstrap.servers")
	private String bootstrapServer;
	@Value("security.protocol")
	private String securityProtocol;
	@Value("sasl.mechanism")
	private String saslMechanism;
	@Value("client.dns.lookup")
	private String clientDnsLookup;
	@Value("session.timeout.ms")
	private String sessionTimeoutMs;
	@Value("basic.auth.credentials.source")
	private String basicAuthCredentialsSource;

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
	
	public String getBootstrapServer() {
		return bootstrapServer;
	}
	
	public void setBootstrapServer(String bootstrapServer) {
		this.bootstrapServer = bootstrapServer;
	}
	
	public String getSecurityProtocol() {
		return securityProtocol;
	}

	public void setSecurityProtocol(String securityProtocol) {
		this.securityProtocol = securityProtocol;
	}

	public String getSaslMechanism() {
		return saslMechanism;
	}

	public void setSaslMechanism(String saslMechanism) {
		this.saslMechanism = saslMechanism;
	}

	public String getClientDnsLookup() {
		return clientDnsLookup;
	}

	public void setClientDnsLookup(String clientDnsLookup) {
		this.clientDnsLookup = clientDnsLookup;
	}

	public String getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	public void setSessionTimeoutMs(String sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}

	public String getBasicAuthCredentialsSource() {
		return basicAuthCredentialsSource;
	}

	public void setBasicAuthCredentialsSource(String basicAuthCredentialsSource) {
		this.basicAuthCredentialsSource = basicAuthCredentialsSource;
	}
}
