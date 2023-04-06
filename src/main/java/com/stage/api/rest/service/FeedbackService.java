package com.stage.api.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stage.FeedbackGiven;
import com.stage.api.rest.entity.Feedback;
import com.stage.api.rest.repository.FeedbackRepository;

@Service
public class FeedbackService {
	
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	public List<Feedback> getFeedbackFromLocation(String location) {
		return feedbackRepository.getFeedbackForLocation(location);
	}
	
	public List<Feedback> getFeedback() {
		return feedbackRepository.getFeedback();
	}
	
	public void publishFeedback(String body) {
		feedbackRepository.publishFeedback(body);
	}
	
	public void postFeedbackInDatabase(FeedbackGiven feedback) {
		feedbackRepository.postFeedback(feedback);
	}
	
}
