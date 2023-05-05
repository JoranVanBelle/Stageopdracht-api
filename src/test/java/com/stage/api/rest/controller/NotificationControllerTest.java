package com.stage.api.rest.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.stage.api.rest.service.NotificationService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = NotificationController.class)
@ActiveProfiles("test")
public class NotificationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private NotificationService notificationService;
	
	@Test
	public void getWeatherNotification() throws Exception {
		
		List<Weather> weatherList = new ArrayList<>();
		weatherList.add(new Weather("KiteableNieuwpoort1", "Nieuwpoort", "10.00", "m/s", "151.00", "cm", "10.00", "deg", 1));
		
		Mockito.when(notificationService.getWeathernotifications()).thenReturn(weatherList);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/notification/weather").accept(MediaType.TEXT_EVENT_STREAM_VALUE);
		
		MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();
		
       String content = result.getResponse().getContentAsString();
        assertThat(content, containsString("id:"));
        assertThat(content, containsString(String.format("\"dataID\":\"%s\"", weatherList.get(0).getDataID())));
        assertThat(content, containsString(String.format("\"location\":\"%s\"", weatherList.get(0).getLocation())));
        assertThat(content, containsString(String.format("\"windspeed\":\"%s\"", weatherList.get(0).getWindspeed())));
        assertThat(content, containsString(String.format("\"windspeedUnit\":\"%s\"", weatherList.get(0).getWindspeedUnit())));
        assertThat(content, containsString(String.format("\"waveheight\":\"%s\"", weatherList.get(0).getWaveheight())));
        assertThat(content, containsString(String.format("\"waveheightUnit\":\"%s\"", weatherList.get(0).getWaveheightUnit())));
        assertThat(content, containsString(String.format("\"winddirection\":\"%s\"", weatherList.get(0).getWinddirection())));
        assertThat(content, containsString(String.format("\"winddirectionUnit\":\"%s\"", weatherList.get(0).getWinddirectionUnit())));
        assertThat(content, containsString(String.format("\"timestamp\":%d", weatherList.get(0).getTimestamp())));
        assertThat(content, containsString("Sse weather"));
    }
}
