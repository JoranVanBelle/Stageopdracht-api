package com.stage.api.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stage.api.rest.entity.Weather;
import com.stage.api.rest.repository.WeatherRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NotificationService.class)
@ActiveProfiles("test")
public class NotificationServiceTest {

	@Autowired
	private NotificationService notificationService;
	
	@MockBean
	private WeatherRepository weatherRepository;
	
	@Test
	public void getWeathernotifications() {
		List<Weather> weatherList = new ArrayList<>();
		weatherList.add(new Weather("KiteableNieuwpoort1", "Nieuwpoort", "10.00", "m/s", "151.00", "cm", "10.00", "deg", 1));
		weatherList.add(new Weather("KiteableDe Panne1", "De Panne", "11.00", "m/s", "155.00", "cm", "11.00", "deg", 1));
		
		Mockito.when(weatherRepository.getWeather()).thenReturn(weatherList);
		
		List<Weather> response = notificationService.getWeathernotifications();
		
		Assertions.assertEquals("KiteableNieuwpoort1", response.get(0).getDataID());
		Assertions.assertEquals("KiteableDe Panne1", response.get(1).getDataID());
	}
}
