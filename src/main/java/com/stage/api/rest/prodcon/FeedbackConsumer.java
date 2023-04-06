package com.stage.api.rest.prodcon;

import java.time.Duration;
import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.stage.FeedbackGiven;
import com.stage.api.rest.service.FeedbackService;

@Service
public class FeedbackConsumer {
	
	@Value("${api.feedback.topic}")
	private String topic;
	
	@Autowired
	private KafkaConsumer<String, FeedbackGiven> consumer;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@KafkaListener(id = "api", topics = "Kiten.Feedback")
	public void putFeedbackInDatabase() {
		consumer.subscribe(Arrays.asList(topic));
		ConsumerRecords<String, FeedbackGiven> records = consumer.poll(Duration.ofMillis(100));
		
		for(ConsumerRecord<String, FeedbackGiven> record : records) {
			FeedbackGiven fbg = record.value();
			feedbackService.postFeedbackInDatabase(fbg);
		}	
	}
}
