package com.stage.api.rest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.stage.api.rest.entity.Weather;
import com.stage.api.rest.repository.WeatherRepository;

@Service
public class NotificationService {

    private final WeatherRepository weatherRepository;
    private final Map<String, Long> weatherTimestamps;
    
    public NotificationService(WeatherRepository weatherRepository) {
    	this.weatherRepository = weatherRepository;
    	weatherTimestamps = new HashMap<>();
    }
    
	public List<Weather> getWeathernotifications() {
		List<Weather> allWeather =  weatherRepository.getWeather();
		List<Weather> updatedWeather = new ArrayList<>();
		
		for(Weather w : allWeather) {
			if(!weatherTimestamps.containsKey(w.getLocation()) || weatherTimestamps.get(w.getLocation()) != w.getTimestamp()) {
				weatherTimestamps.put(w.getLocation(), w.getTimestamp());
				updatedWeather.add(w);
			}
		}
		
		return updatedWeather;
	}
}
