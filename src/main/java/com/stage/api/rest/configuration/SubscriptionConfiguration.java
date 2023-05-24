package com.stage.api.rest.configuration;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import com.stage.SubscriptionRegistered;

@Configuration
public class SubscriptionConfiguration {

	  private final KafkaProperties kafkaProperties;

	  public SubscriptionConfiguration(KafkaProperties kafkaProperties) {
	    this.kafkaProperties = kafkaProperties;
	  }
	
	 @Bean
	  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SubscriptionRegistered>> subscriptionContainerFactory() {
	    final var factory = new ConcurrentKafkaListenerContainerFactory<String, SubscriptionRegistered>();
	    factory.setConsumerFactory(consumerSubscriptionFactory());
	    factory.setCommonErrorHandler(new CommonLoggingErrorHandler());
	    return factory;
	  }

	 @Bean
	 public ConsumerFactory<String, SubscriptionRegistered> consumerSubscriptionFactory() {
	   final var config = kafkaProperties.buildConsumerProperties();
	   return new DefaultKafkaConsumerFactory<>(config);
	 }
	 
	 @Bean
	 public ProducerFactory<String, SubscriptionRegistered> subscriptionProducerFactory() {
	   return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
	 }

	 @Bean
	 public KafkaTemplate<String, SubscriptionRegistered> subscriptionKafkaTemplate() {
	   return new KafkaTemplate<>(subscriptionProducerFactory());
	 }
}
