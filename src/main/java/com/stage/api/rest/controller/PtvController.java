package com.stage.api.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stage.api.rest.service.PtvService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/ptv")
public class PtvController {

	private final PtvService ptvService;
	private long lastTimeRequested = 0;
	
	public PtvController(PtvService ptvService) {
		this.ptvService = ptvService;
	}
	
	@GetMapping("")
	@Operation(description = "To get the geocode of a certain location.", summary = "Only 1 request per second possible")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public String getDistanceMatrixResponse(@RequestBody String body) {
		if(System.currentTimeMillis() - 1000 >= lastTimeRequested) {
			lastTimeRequested = System.currentTimeMillis();
			return ptvService.getGeocoding(body);
		}
		return "Try again in a few seconds";
	}
	
}
