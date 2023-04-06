package com.stage.api.rest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stage.api.rest.entity.Weather;
import com.stage.api.rest.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherController {
	
	@Autowired
	private WeatherService weatherService;
	  
	@GetMapping(value="/locations")
	public List<String> getLocations() {
		return weatherService.getLocations();
	}
	
	@GetMapping(value="")
	public List<Weather> getWeather() {
		return weatherService.getWeather();
	}
	
	@GetMapping(value="/{location}")
	public Weather getWeatherByLocation(@PathVariable String location) {
		return weatherService.getWeatherByLocation(location);
	}
	
	@GetMapping(value="/windspeed/{location}")
	public Map<String, String> getWindspeedByLocation(@PathVariable String location) {		
		return weatherService.getWindspeedByLocation(location);
	}
	
	@GetMapping(value="/waveheight/{location}")
	public Map<String, String> getWaveheightByLocation(@PathVariable String location) {
		return weatherService.getWaveheightByLocation(location);
	}
	
	@GetMapping(value="/winddirection/{location}")
	public Map<String, String> getWinddirectionByLocation(@PathVariable String location) {
		return weatherService.getWinddirectionByLocation(location);
	}
}