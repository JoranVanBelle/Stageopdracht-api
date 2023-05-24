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

import com.stage.FeedbackGiven;
import com.stage.SubscriptionRegistered;

@Configuration
public class FeedbackConfiguration {

	  private final KafkaProperties kafkaProperties;

	  public FeedbackConfiguration(KafkaProperties kafkaProperties) {
	    this.kafkaProperties = kafkaProperties;
	  }
	
	 @Bean
	  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, FeedbackGiven>> feedbackContainerFactory() {
	    final var factory = new ConcurrentKafkaListenerContainerFactory<String, FeedbackGiven>();
	    factory.setConsumerFactory(consumerFeedbackFactory());
	    factory.setCommonErrorHandler(new CommonLoggingErrorHandler());
	    return factory;
	  }

	 @Bean
	 public ConsumerFactory<String, FeedbackGiven> consumerFeedbackFactory() {
	   final var config = kafkaProperties.buildConsumerProperties();
	   return new DefaultKafkaConsumerFactory<>(config);
	 }
	 
	 @Bean
	 public ProducerFactory<String, FeedbackGiven> feedbackProducerFactory() {
	   return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
	 }

	 @Bean
	 public KafkaTemplate<String, FeedbackGiven> feedbackKafkaTemplate() {
	   return new KafkaTemplate<>(feedbackProducerFactory());
	 }
}
