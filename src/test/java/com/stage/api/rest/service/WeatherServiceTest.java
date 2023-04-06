package com.stage.api.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stage.api.rest.entity.Weather;
import com.stage.api.rest.repository.WeatherRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WeatherService.class)
@ActiveProfiles("test")
public class WeatherServiceTest {
	
	@Autowired
	private WeatherService weatherService;
	
	@MockBean
	private WeatherRepository weatherRepository;
	
	@Test
	public void getLocationTest() {
		
		List<Weather> weatherList = new ArrayList<>();
		weatherList.add(new Weather("KiteableNieuwpoort1", "Nieuwpoort", "10.00", "m/s", "151.00", "cm", "10.00", "deg", 1));
		weatherList.add(new Weather("KiteableDe Panne1", "De Panne", "11.00", "m/s", "155.00", "cm", "11.00", "deg", 1));
		
		Mockito.when(weatherRepository.getLocations()).thenReturn(weatherList);
		
		List<String> expected = List.of("Nieuwpoort", "De Panne");
		
		List<String> response = weatherService.getLocations();
		
		Assertions.assertEquals(expected, response);
	}
	
	@Test
	public void getWeatherTest() {
		List<Weather> weatherList = new ArrayList<>();
		weatherList.add(new Weather("KiteableNieuwpoort1", "Nieuwpoort", "10.00", "m/s", "151.00", "cm", "10.00", "deg", 1));
		weatherList.add(new Weather("KiteableDe Panne1", "De Panne", "11.00", "m/s", "155.00", "cm", "11.00", "deg", 1));
		
		Mockito.when(weatherRepository.getWeather()).thenReturn(weatherList);
		
		List<Weather> response = weatherService.getWeather();
		
		Assertions.assertEquals("KiteableNieuwpoort1", response.get(0).getDataID());
		Assertions.assertEquals("KiteableDe Panne1", response.get(1).getDataID());
	}
	
	@Test
	public void getWeatherByLocation() {
		Weather weather = new Weather("KiteableNieuwpoort1", "Nieuwpoort", "10.00", "m/s", "151.00", "cm", "10.00", "deg", 1);
		
		Mockito.when(weatherRepository.getWeatherByLocation(Mockito.anyString())).thenReturn(weather);
		
		Weather response = weatherService.getWeatherByLocation("Nieuwpoort");
		
		Assertions.assertEquals("KiteableNieuwpoort1", response.getDataID());
		Assertions.assertEquals("Nieuwpoort", response.getLocation());
		Assertions.assertEquals("10.00", response.getWindspeed());
		Assertions.assertEquals("m/s", response.getWindspeedUnit());
		Assertions.assertEquals("151.00", response.getWaveheight());
		Assertions.assertEquals("cm", response.getWaveheightUnit());
		Assertions.assertEquals("10.00", response.getWinddirection());
		Assertions.assertEquals("deg", response.getWinddirectionUnit());
		Assertions.assertEquals(1, response.getTimestamp());
	}
	
	@Test
	public void getWindspeedByLocation() {
		Weather weather = new Weather("KiteableNieuwpoort1", "Nieuwpoort", "10.00", "m/s", "151.00", "cm", "10.00", "deg", 1);
		
		Mockito.when(weatherRepository.getWindspeedByLocation(Mockito.anyString())).thenReturn(weather);
		
		Map<String, String> response = weatherService.getWindspeedByLocation("Nieuwpoort");
		
		Assertions.assertEquals("10.00", response.get("value"));
		Assertions.assertEquals("m/s", response.get("unit"));
	}
	
	@Test
	public void getWaveheightByLocation() {
		Weather weather = new Weather("KiteableNieuwpoort1", "Nieuwpoort", "10.00", "m/s", "151.00", "cm", "10.00", "deg", 1);
		
		Mockito.when(weatherRepository.getWaveheightByLocation(Mockito.anyString())).thenReturn(weather);
		
		Map<String, String> response = weatherService.getWaveheightByLocation("Nieuwpoort");
		
		Assertions.assertEquals("151.00", response.get("value"));
		Assertions.assertEquals("cm", response.get("unit"));
	}
	
	@Test
	public void getWinddirectionByLocation() {
		Weather weather = new Weather("KiteableNieuwpoort1", "Nieuwpoort", "10.00", "m/s", "151.00", "cm", "10.00", "deg", 1);
		
		Mockito.when(weatherRepository.getWinddirectionByLocation(Mockito.anyString())).thenReturn(weather);
		
		Map<String, String> response = weatherService.getWinddirectionByLocation("Nieuwpoort");
		
		Assertions.assertEquals("10.00", response.get("value"));
		Assertions.assertEquals("deg", response.get("unit"));
	}
}
