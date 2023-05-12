package com.stage.api.rest.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stage.api.rest.service.SubscriptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

	private final SubscriptionService subService;
	
	public SubscriptionController(SubscriptionService subService) {
		this.subService = subService;
	}
	
	@PostMapping("/subscribe")
	@Operation(description = "To subscribe to the emails of a certain location")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public void postSubscription(@RequestBody String body) {
		subService.publishSubscription(body);
	}
	
	@PostMapping("/signout")
	@Operation(description = "To unsubscribe of the emails of a certain location")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public void postSignout(@RequestBody String body) {
		subService.publishSignOut(body);
	}
	
}
