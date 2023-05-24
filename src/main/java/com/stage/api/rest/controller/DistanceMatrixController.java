package com.stage.api.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stage.api.rest.service.DistanceMatrixService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/distancematrix")
public class DistanceMatrixController {

	private final DistanceMatrixService distanceMatrixService;
	private long lastTimeRequested = 0;
	
	public DistanceMatrixController(DistanceMatrixService distanceMatrixService) {
		this.distanceMatrixService = distanceMatrixService;
	}
	
	@GetMapping("")
	@Operation(description = "To get the distance matrix from a public api.", summary = "Only 1 request per second possible")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public String getDistanceMatrixResponse(@RequestBody String body) {
		if(System.currentTimeMillis() - 1000 >= lastTimeRequested || lastTimeRequested == 0) {
			lastTimeRequested = System.currentTimeMillis();
			return distanceMatrixService.getDistanceMatrixResponse(body);
		}
		return "Try again in a few seconds";
	}
}
