package com.stage.api.rest.integration;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.stage.api.rest.extension.KafkaTestcontainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(KafkaTestcontainer.class)
public class WeatherIntegrationTest {


	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getAllWeather() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/weather")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String resultString = result.getResponse().getContentAsString();
		
		JSONArray jsonArray = new JSONArray(resultString);
        List<JSONObject> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            list.add(jsonObject);
        }
        
        JSONObject first = list.get(0);
        JSONObject second = list.get(1);

        Assertions.assertEquals(2, list.size());
        
        Assertions.assertEquals("De PanneKiteable1", first.getString("dataID"));
        Assertions.assertEquals("De Panne", first.getString("location"));
        Assertions.assertEquals("10.00", first.getString("windspeed"));
        Assertions.assertEquals("m/s", first.getString("windspeedUnit"));
        Assertions.assertEquals("151.00", first.getString("waveheight"));
        Assertions.assertEquals("cm", first.getString("waveheightUnit"));
        Assertions.assertEquals("10.00", first.getString("winddirection"));
        Assertions.assertEquals("deg", first.getString("winddirectionUnit"));
        Assertions.assertEquals(1, first.getInt("timestamp"));
        
        Assertions.assertEquals("NieuwpoortKiteable1", second.getString("dataID"));
        Assertions.assertEquals("Nieuwpoort", second.getString("location"));
        Assertions.assertEquals("9.00", second.getString("windspeed"));
        Assertions.assertEquals("m/s", second.getString("windspeedUnit"));
        Assertions.assertEquals("151.00", second.getString("waveheight"));
        Assertions.assertEquals("cm", second.getString("waveheightUnit"));
        Assertions.assertEquals("10.00", second.getString("winddirection"));
        Assertions.assertEquals("deg", second.getString("winddirectionUnit"));
        Assertions.assertEquals(1, second.getInt("timestamp"));
  
	}
	
	@Test
	public void getLocations() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/weather/locations")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String resultString = result.getResponse().getContentAsString();
        
		String expected = "[\"De Panne\",\"Nieuwpoort\"]";
		
        Assertions.assertEquals(expected, resultString);
	}
	
	@Test
	public void getWeatherForLocationTest_Nieuwpoort() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/weather/Nieuwpoort")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String resultString = result.getResponse().getContentAsString();

        JSONObject first = new JSONObject(resultString);
        
        Assertions.assertEquals("NieuwpoortKiteable1", first.getString("dataID"));
        Assertions.assertEquals("Nieuwpoort", first.getString("location"));
        Assertions.assertEquals("9.00", first.getString("windspeed"));
        Assertions.assertEquals("m/s", first.getString("windspeedUnit"));
        Assertions.assertEquals("151.00", first.getString("waveheight"));
        Assertions.assertEquals("cm", first.getString("waveheightUnit"));
        Assertions.assertEquals("10.00", first.getString("winddirection"));
        Assertions.assertEquals("deg", first.getString("winddirectionUnit"));
        Assertions.assertEquals(1, first.getInt("timestamp"));
        
	}
	
	@Test
	public void getWeatherForLocationTest_DePanne() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/weather/De Panne")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String resultString = result.getResponse().getContentAsString();

        JSONObject first = new JSONObject(resultString);
        
        
        
        Assertions.assertEquals("De PanneKiteable1", first.getString("dataID"));
        Assertions.assertEquals("De Panne", first.getString("location"));
        Assertions.assertEquals("10.00", first.getString("windspeed"));
        Assertions.assertEquals("m/s", first.getString("windspeedUnit"));
        Assertions.assertEquals("151.00", first.getString("waveheight"));
        Assertions.assertEquals("cm", first.getString("waveheightUnit"));
        Assertions.assertEquals("10.00", first.getString("winddirection"));
        Assertions.assertEquals("deg", first.getString("winddirectionUnit"));
        Assertions.assertEquals(1, first.getInt("timestamp"));
        
	}
	
	@Test
	public void getWindspeedTest_Nieuwpoort() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/weather/windspeed/Nieuwpoort")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String resultString = result.getResponse().getContentAsString();

        JSONObject first = new JSONObject(resultString);
        
        Assertions.assertEquals("m/s", first.getString("unit"));
        Assertions.assertEquals("9.00", first.getString("value"));
        
	}
	
	@Test
	public void getWindspeedTest_DePanne() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/weather/windspeed/De Panne")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String resultString = result.getResponse().getContentAsString();

        JSONObject first = new JSONObject(resultString);
        
        Assertions.assertEquals("m/s", first.getString("unit"));
        Assertions.assertEquals("10.00", first.getString("value"));
        
	}
	
	@Test
	public void getWaveheightTest_Nieuwpoort() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/weather/waveheight/Nieuwpoort")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String resultString = result.getResponse().getContentAsString();

        JSONObject first = new JSONObject(resultString);
        
        Assertions.assertEquals("cm", first.getString("unit"));
        Assertions.assertEquals("151.00", first.getString("value"));
        
	}
	
	@Test
	public void getWaveheightTest_DePanne() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/weather/waveheight/De Panne")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String resultString = result.getResponse().getContentAsString();

        JSONObject first = new JSONObject(resultString);
        
        Assertions.assertEquals("cm", first.getString("unit"));
        Assertions.assertEquals("151.00", first.getString("value"));
        
	}
	
	@Test
	public void getWinddirectionTest_Nieuwpoort() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/weather/winddirection/Nieuwpoort")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String resultString = result.getResponse().getContentAsString();

        JSONObject first = new JSONObject(resultString);
        
        Assertions.assertEquals("deg", first.getString("unit"));
        Assertions.assertEquals("10.00", first.getString("value"));
        
	}
	
	@Test
	public void getWinddirectionTest_DePanne() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/weather/winddirection/De Panne")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String resultString = result.getResponse().getContentAsString();

        JSONObject first = new JSONObject(resultString);
        
        Assertions.assertEquals("deg", first.getString("unit"));
        Assertions.assertEquals("10.00", first.getString("value"));
        
	}	
}
