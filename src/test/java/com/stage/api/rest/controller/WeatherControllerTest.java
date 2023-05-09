package com.stage.api.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.stage.api.rest.entity.Weather;
import com.stage.api.rest.service.WeatherService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = WeatherController.class)
@ActiveProfiles("test")
class WeatherControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private WeatherService weatherService;
	
	@Test
	public void getLocationsTest() throws Exception {
		
		List<String> locations = List.of("Nieuwpoort", "De Panne");
		
		Mockito.when(weatherService.getLocations()).thenReturn(locations);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/api/weather/locations").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "[\"Nieuwpoort\",\"De Panne\"]";
		
		Assertions.assertEquals(expected, result.getResponse().getContentAsString());
		Assertions.assertEquals("application/json", result.getResponse().getContentType());
	}
	
	@Test
	public void getWeatherTest() throws Exception {
		
		List<Weather> weatherList = new ArrayList<>();
		weatherList.add(new Weather("KiteableNieuwpoort1", "Nieuwpoort", "10.00", "m/s", "151.00", "cm", "10.00", "deg", 1));
		weatherList.add(new Weather("KiteableDe Panne1", "De Panne", "11.00", "m/s", "155.00", "cm", "11.00", "deg", 1));
		
		Mockito.when(weatherService.getWeather()).thenReturn(weatherList);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/api/weather").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = expectedGetWeatherTest();
				
		Assertions.assertEquals(expected, result.getResponse().getContentAsString());
		Assertions.assertEquals("application/json", result.getResponse().getContentType());
	}
	
	@Test
	public void getWeatherByLocationTest() throws Exception {
		Weather weather = new Weather("KiteableNieuwpoort1", "Nieuwpoort", "10.00", "m/s", "151.00", "cm", "10.00", "deg", 1);
		
		Mockito.when(weatherService.getWeatherByLocation(Mockito.anyString())).thenReturn(weather);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/api/weather/Nieuwpoort").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = expectedGetWeatherByLocationTest();
		
		Assertions.assertEquals(expected, result.getResponse().getContentAsString());
		Assertions.assertEquals("application/json", result.getResponse().getContentType());
	}
	
	@Test
	public void getWindspeedByLocation() throws Exception {
		Map<String, String> windspeed = new HashMap<>();
		windspeed.put("value", "10.00");
		windspeed.put("unit", "m/s");
		
		Mockito.when(weatherService.getWindspeedByLocation(Mockito.anyString())).thenReturn(windspeed);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/api/weather/windspeed/Nieuwpoort").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		

		String expected = "{\"unit\":\"m/s\",\"value\":\"10.00\"}";
		
		Assertions.assertEquals(expected, result.getResponse().getContentAsString());
		Assertions.assertEquals("application/json", result.getResponse().getContentType());
	}
	
	@Test
	public void getWaveheightByLocation() throws Exception {
		Map<String, String> windspeed = new HashMap<>();
		windspeed.put("value", "151.00");
		windspeed.put("unit", "cm");
		
		Mockito.when(weatherService.getWaveheightByLocation(Mockito.anyString())).thenReturn(windspeed);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/api/weather/waveheight/Nieuwpoort").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"unit\":\"cm\",\"value\":\"151.00\"}";
		
		Assertions.assertEquals(expected, result.getResponse().getContentAsString());
		Assertions.assertEquals("application/json", result.getResponse().getContentType());
	}
	
	@Test
	public void getWinddirectiontByLocation() throws Exception {
		Map<String, String> windspeed = new HashMap<>();
		windspeed.put("value", "10.00");
		windspeed.put("unit", "deg");
		
		Mockito.when(weatherService.getWinddirectionByLocation(Mockito.anyString())).thenReturn(windspeed);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/api/weather/winddirection/Nieuwpoort").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"unit\":\"deg\",\"value\":\"10.00\"}";
		
		Assertions.assertEquals(expected, result.getResponse().getContentAsString());
		Assertions.assertEquals("application/json", result.getResponse().getContentType());
	}
	
	private String expectedGetWeatherTest() {
		return "[{\"dataID\":\"KiteableNieuwpoort1\",\"location\":\"Nieuwpoort\",\"windspeed\":\"10.00\",\"windspeedUnit\":\"m/s\",\"waveheight\":\"151.00\",\"waveheightUnit\":\"cm\",\"winddirection\":\"10.00\",\"winddirectionUnit\":\"deg\",\"timestamp\":1},{\"dataID\":\"KiteableDe Panne1\",\"location\":\"De Panne\",\"windspeed\":\"11.00\",\"windspeedUnit\":\"m/s\",\"waveheight\":\"155.00\",\"waveheightUnit\":\"cm\",\"winddirection\":\"11.00\",\"winddirectionUnit\":\"deg\",\"timestamp\":1}]";
	}
	
	private String expectedGetWeatherByLocationTest() {
		return "{\"dataID\":\"KiteableNieuwpoort1\",\"location\":\"Nieuwpoort\",\"windspeed\":\"10.00\",\"windspeedUnit\":\"m/s\",\"waveheight\":\"151.00\",\"waveheightUnit\":\"cm\",\"winddirection\":\"10.00\",\"winddirectionUnit\":\"deg\",\"timestamp\":1}";
	}
}