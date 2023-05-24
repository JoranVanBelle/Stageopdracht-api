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

import com.stage.SignOutRegistered;
import com.stage.SubscriptionRegistered;

@Configuration
public class SignoutConfiguration {

	  private final KafkaProperties kafkaProperties;

	  public SignoutConfiguration(KafkaProperties kafkaProperties) {
	    this.kafkaProperties = kafkaProperties;
	  }
	
	 @Bean
	  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SignOutRegistered>> SignoutContainerFactory() {
	    final var factory = new ConcurrentKafkaListenerContainerFactory<String, SignOutRegistered>();
	    factory.setConsumerFactory(consumerSignoutFactory());
	    factory.setCommonErrorHandler(new CommonLoggingErrorHandler());
	    return factory;
	  }

	 @Bean
	 public ConsumerFactory<String, SignOutRegistered> consumerSignoutFactory() {
	   final var config = kafkaProperties.buildConsumerProperties();
	   return new DefaultKafkaConsumerFactory<>(config);
	 }
	 
	 @Bean
	 public ProducerFactory<String, SignOutRegistered> signoutProducerFactory() {
	   return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
	 }

	 @Bean
	 public KafkaTemplate<String, SignOutRegistered> signoutKafkaTemplate() {
	   return new KafkaTemplate<>(signoutProducerFactory());
	 }
}
