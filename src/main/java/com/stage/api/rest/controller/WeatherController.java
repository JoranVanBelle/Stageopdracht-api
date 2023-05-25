package com.stage.api.rest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stage.api.rest.entity.Weather;
import com.stage.api.rest.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
	
	private final WeatherService weatherService;
	
	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}
	  
	@GetMapping(value="/locations")
	@Operation(deprecated = true, description = "To get a list of all the locations")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public List<String> getLocations() {
		return weatherService.getLocations();
	}
	
	@GetMapping(value="")
	@Operation(description = "To get a list of the weather of all available locations")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public List<Weather> getWeather() {
		return weatherService.getWeather();
	}
	
	@GetMapping(value="/{location}")
	@Operation(description = "To get the weather of a certain location")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public Weather getWeatherByLocation(@PathVariable String location) {
		return weatherService.getWeatherByLocation(location);
	}
	
	@GetMapping(value="/windspeed/{location}")
	@Operation(deprecated = true, description = "To get the windspeed of a certain location")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public Map<String, String> getWindspeedByLocation(@PathVariable String location) {		
		return weatherService.getWindspeedByLocation(location);
	}
	
	@GetMapping(value="/waveheight/{location}")
	@Operation(deprecated = true, description = "To get the waveheight of a certain location")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public Map<String, String> getWaveheightByLocation(@PathVariable String location) {
		return weatherService.getWaveheightByLocation(location);
	}
	
	@GetMapping(value="/winddirection/{location}")
	@Operation(deprecated = true, description = "To get the winddirection of a certain location")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	public Map<String, String> getWinddirectionByLocation(@PathVariable String location) {
		return weatherService.getWinddirectionByLocation(location);
	}
}
