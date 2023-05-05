package com.stage.api.rest.infrastructure;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.stage.FeedbackGiven;
import com.stage.api.rest.service.FeedbackService;

@Service
public class FeedbackConsumer {
	
	private final FeedbackService feedbackService;
	
	public FeedbackConsumer(
			FeedbackService feedbackService
	) {
		this.feedbackService = feedbackService;
	}
	
	@KafkaListener(id = "api.feedback", topics = "Kiten.Feedback")
	public void putFeedbackInDatabase(FeedbackGiven feedback) {		
		feedbackService.postFeedbackInDatabase(feedback);
	}
}
