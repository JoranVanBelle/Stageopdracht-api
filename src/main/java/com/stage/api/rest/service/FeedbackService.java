package com.stage.api.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stage.FeedbackGiven;
import com.stage.api.rest.entity.Feedback;
import com.stage.api.rest.infrastructure.FeedbackInfrastructure;
import com.stage.api.rest.repository.FeedbackRepository;

@Service
public class FeedbackService {
	
	private final FeedbackRepository feedbackRepository;
	private final FeedbackInfrastructure feedbackInfrastructure;
	
    public FeedbackService(
    		FeedbackRepository feedbackRepository,
    		FeedbackInfrastructure feedbackInfrastructure
    ) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackInfrastructure = feedbackInfrastructure;
    }
	
	public List<Feedback> getFeedbackFromLocation(String location) {
		return feedbackRepository.getFeedbackForLocation(location);
	}
	
	public List<Feedback> getFeedback() {
		return feedbackRepository.getFeedback();
	}
	
	public void publishFeedback(String body) {
		feedbackInfrastructure.publishFeedback(body);
	}
	
	public void postFeedbackInDatabase(FeedbackGiven feedback) {
		feedbackRepository.postFeedback(feedback);
	}
	
}
