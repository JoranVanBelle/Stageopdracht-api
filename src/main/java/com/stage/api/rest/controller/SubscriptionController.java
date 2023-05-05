package com.stage.api.rest.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stage.api.rest.service.SubscriptionService;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

	private final SubscriptionService subService;
	
	public SubscriptionController(SubscriptionService subService) {
		this.subService = subService;
	}
	
	@PostMapping("/subscribe")
	public void postSubscription(@RequestBody String body) {
		subService.publishSubscription(body);
	}
	
	@PostMapping("/signout")
	public void postSignout(@RequestBody String body) {
		subService.publishSignOut(body);
	}
	
}
