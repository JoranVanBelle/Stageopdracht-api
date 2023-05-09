package com.stage.api.rest.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.stage.api.rest.entity.Weather;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void getWeatherNotification() throws Exception {
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(new Weather("KiteableNieuwpoort1", "Nieuwpoort", "10.00", "m/s", "151.00", "cm", "10.00", "deg", 1));

        
        Mockito.when(namedParameterJdbcTemplate.queryForList(Mockito.anyString(), Mockito.any(SqlParameterSource.class)))
			.thenReturn(mockWeatherList());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
        									.get("/api/notification/weather")
        									.accept(MediaType.TEXT_EVENT_STREAM_VALUE);
        
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content, containsString("id:"));
        assertThat(content, containsString(String.format("\"dataID\":\"%s\"", weatherList.get(0).getDataID())));
        assertThat(content, containsString(String.format("\"location\":\"%s\"", weatherList.get(0).getLocation())));
        assertThat(content, containsString(String.format("\"windspeed\":\"%s\"", weatherList.get(0).getWindspeed())));
        assertThat(content, containsString(String.format("\"windspeedunit\":\"%s\"", weatherList.get(0).getWindspeedUnit())));
        assertThat(content, containsString(String.format("\"waveheight\":\"%s\"", weatherList.get(0).getWaveheight())));
        assertThat(content, containsString(String.format("\"waveheightunit\":\"%s\"", weatherList.get(0).getWaveheightUnit())));
        assertThat(content, containsString(String.format("\"winddirection\":\"%s\"", weatherList.get(0).getWinddirection())));
        assertThat(content, containsString(String.format("\"winddirectionunit\":\"%s\"", weatherList.get(0).getWinddirectionUnit())));
        assertThat(content, containsString(String.format("\"timestamp\":%d", weatherList.get(0).getTimestamp())));
        assertThat(content, containsString("Sse weather"));
    }
    
	private List<Map<String, Object>> mockWeatherList() {
		List<Map<String, Object>> mockResult = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("dataid", "KiteableNieuwpoort1");
        map1.put("loc", "Nieuwpoort");
        map1.put("windspeed", "10.00");
        map1.put("windspeedunit", "m/s");
        map1.put("waveheight", "151.00");
        map1.put("waveheightunit", "cm");
        map1.put("winddirection", "10.00");
        map1.put("winddirectionunit", "deg");
        map1.put("timestampmeasurment", 1);
        
        mockResult.add(map1);
        
        return mockResult;
	}
}
