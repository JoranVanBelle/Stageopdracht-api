package com.stage.api.rest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.stage.api.rest.entity.Weather;
import com.stage.api.rest.repository.WeatherRepository;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    
    public WeatherService(WeatherRepository weatherRepository) {
    	this.weatherRepository = weatherRepository;
    }
	
	public List<String> getLocations() {
		List<String> locations = new ArrayList<>();
		List<Weather> weatherList = weatherRepository.getLocations();
		
		for(Weather weather : weatherList) {
			locations.add(weather.getLocation());
		}
		
		return locations;
		
	}
	
	public List<Weather> getWeather() {
		return weatherRepository.getWeather();
	}
	
	public Weather getWeatherByLocation(String location) {
		return weatherRepository.getWeatherByLocation(location);
	}
	
	public Map<String, String> getWindspeedByLocation(String location) {
		
		Map<String, String> windspeed = new HashMap<>();
		Weather weather = weatherRepository.getWindspeedByLocation(location);
		
		windspeed.put("value", weather.getWindspeed());
		windspeed.put("unit", weather.getWindspeedUnit());
		
		return windspeed;
	}
	
	public Map<String, String> getWaveheightByLocation(String location) {
		
		Map<String, String> waveheight = new HashMap<>();
		Weather weather = weatherRepository.getWaveheightByLocation(location);
		
		waveheight.put("value", weather.getWaveheight());
		waveheight.put("unit", weather.getWaveheightUnit());
		
		return waveheight;
	}
	
	public Map<String, String> getWinddirectionByLocation(String location) {
		
		Map<String, String> winddirection = new HashMap<>();
		Weather weather = weatherRepository.getWinddirectionByLocation(location);
		
		winddirection.put("value", weather.getWinddirection());
		winddirection.put("unit", weather.getWinddirectionUnit());
		
		return winddirection;		
	}
}
