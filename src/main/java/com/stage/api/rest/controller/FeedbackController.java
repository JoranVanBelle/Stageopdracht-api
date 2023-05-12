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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
	
	private final FeedbackService feedbackService;
	
	public FeedbackController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}
	  
	@GetMapping("/{location}")
	@Operation(description = "To get all the feedback of a certain location")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public List<Feedback> getFeedbackLocation(@PathVariable String location) {
		return feedbackService.getFeedbackFromLocation(location);
	}
	
	@GetMapping("")
	@Operation(description = "To get all the feedback of all the locations")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public List<Feedback> getFeedback() {
		return feedbackService.getFeedback();
	}
	
	@PostMapping("")
	@Operation(description = "To post feedback about the situation at a specific location")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public void postFeedback(@RequestBody String body) {
		feedbackService.publishFeedback(body);
	}

}
