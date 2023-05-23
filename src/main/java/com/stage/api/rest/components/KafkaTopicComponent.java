package com.stage.api.rest.components;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import com.stage.api.rest.properties.KafkaTopicProperties;
import com.stage.api.rest.properties.ProducerProperties;

@Component
public class KafkaTopicComponent {

	private final KafkaTopicProperties kafkaProperties;
	private final ProducerProperties prodProperties;
	
	public KafkaTopicComponent(
			KafkaTopicProperties kafkaProperties,
			ProducerProperties prodProperties
	) {
		this.kafkaProperties = kafkaProperties;
		this.prodProperties = prodProperties;
	}
	
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, prodProperties.getBootstrapServer());
        configs.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, kafkaProperties.getSecurityProtocol());
        configs.put("sasl.jaas.config", String.format("org.apache.kafka.common.security.plain.PlainLoginModule required username='%s' password='%s'",System.getenv("PLAIN_LOGIN_MODULE_USERNAME"), System.getenv("PLAIN_LOGIN_MODULE_PASSWORD")));
        configs.put("sasl.mechanism", kafkaProperties.getSaslMechanism());
        configs.put("client.dns.lookup", kafkaProperties.getClientDnsLookup());
        configs.put("session.timeout.ms", kafkaProperties.getSessionTimeoutMs());
        configs.put("basic.auth.credentials.source", kafkaProperties.getBasicAuthCredentialsSource());
        configs.put("basic.auth.user.info", System.getenv("BASIC_AUTH_USER_INFO"));
        return new KafkaAdmin(configs);
    }
    
    @Bean
    public NewTopic topicFeedback() {
         return new NewTopic(kafkaProperties.getTopicFeedback(), 1, (short) 1);
    }
    
    @Bean
    public NewTopic topicSubscription() {
    	return new NewTopic(kafkaProperties.getTopicSubscription(), 1, (short) 1);
    }
	
}
