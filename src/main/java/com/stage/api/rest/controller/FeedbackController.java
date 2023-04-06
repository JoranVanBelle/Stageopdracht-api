package com.stage.api.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stage.api.rest.entity.Feedback;
import com.stage.api.rest.service.FeedbackService;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
	
	@Autowired
	private FeedbackService feedbackService;
	  
	@GetMapping("/{location}")
	public List<Feedback> getFeedbackLocation(@PathVariable String location) {
		return feedbackService.getFeedbackFromLocation(location);
	}
	
	@GetMapping("")
	public List<Feedback> getFeedback() {
		return feedbackService.getFeedback();
	}
	
	@PostMapping("")
	public void postFeedback(@RequestBody String body) {
		feedbackService.publishFeedback(body);
	}

}
