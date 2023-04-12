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
import com.stage.api.rest.repository.FeedbackRepository;
import com.stage.api.rest.service.FeedbackService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(KafkaTestcontainer.class)
public class FeedbackIntegration {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Test
	public void getAllFeedback() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/feedback")
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
        
        Assertions.assertEquals("JoranNieuwpoort1", first.getString("feedbackID"));
        Assertions.assertEquals("Nieuwpoort", first.getString("location"));
        Assertions.assertEquals("Joran", first.getString("username"));
        Assertions.assertEquals("The waves are amazing", first.getString("feedback"));
        Assertions.assertEquals(1, first.getInt("timestamp"));
        
        Assertions.assertEquals("JoranDe Panne1", second.getString("feedbackID"));
        Assertions.assertEquals("De Panne", second.getString("location"));
        Assertions.assertEquals("Joran", second.getString("username"));
        Assertions.assertEquals("There is too little wind here", second.getString("feedback"));
        Assertions.assertEquals(1, second.getInt("timestamp"));
	}
	
	@Test
	public void getFeedbackByLocation_Nieuwpoort() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/feedback/Nieuwpoort")
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
        
        Assertions.assertEquals(1, list.size());
        
        Assertions.assertEquals("JoranNieuwpoort1", first.getString("feedbackID"));
        Assertions.assertEquals("Nieuwpoort", first.getString("location"));
        Assertions.assertEquals("Joran", first.getString("username"));
        Assertions.assertEquals("The waves are amazing", first.getString("feedback"));
        Assertions.assertEquals(1, first.getInt("timestamp"));
	}
	
	@Test
	public void getFeedbackByLocation_DePanne() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/feedback/De Panne")
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
        
        Assertions.assertEquals(1, list.size());
        
        Assertions.assertEquals("JoranDe Panne1", first.getString("feedbackID"));
        Assertions.assertEquals("De Panne", first.getString("location"));
        Assertions.assertEquals("Joran", first.getString("username"));
        Assertions.assertEquals("There is too little wind here", first.getString("feedback"));
        Assertions.assertEquals(1, first.getInt("timestamp"));
	}
	
	@Test
	public void getFeedbackByLocation_WrongLocation() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/feedback/Kortrijk")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String resultString = result.getResponse().getContentAsString();
		
		JSONArray jsonArray = new JSONArray(resultString);
        List<JSONObject> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            list.add(jsonObject);
        }
        
        Assertions.assertEquals(0, list.size());
	}
	
	@Test
	public void postFeedback_newLocation() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/feedback")
				.content(getBodyNewLocation())
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder).andReturn();
		
		Thread.sleep(2000);
		
		RequestBuilder requestBuilderGet = MockMvcRequestBuilders
				.get("/feedback/Westende")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilderGet).andReturn();
		
		String resultString = result.getResponse().getContentAsString();

		JSONArray jsonArray = new JSONArray(resultString);
        List<JSONObject> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            list.add(jsonObject);
        }
        
        JSONObject first = list.get(0);
        
        Assertions.assertEquals(1, list.size());
        
        Assertions.assertEquals("LobkeWestende2", first.getString("feedbackID"));
        Assertions.assertEquals("Westende", first.getString("location"));
        Assertions.assertEquals("Lobke", first.getString("username"));
        Assertions.assertEquals("The water is pretty cold", first.getString("feedback"));
        Assertions.assertEquals(2, first.getInt("timestamp"));
	}
	
	@Test
	public void postFeedback_oldLocation() throws Exception {
		
		System.out.println(getBodyOldLocation());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/feedback")
				.content(getBodyOldLocation());
		
		mockMvc.perform(requestBuilder);
		
		Thread.sleep(2000);
		
		RequestBuilder requestBuilderGet = MockMvcRequestBuilders
				.get("/feedback/Nieuwpoort")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilderGet).andReturn();
		
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

        Assertions.assertEquals("JoranNieuwpoort1", second.getString("feedbackID"));
        Assertions.assertEquals("Nieuwpoort", second.getString("location"));
        Assertions.assertEquals("Joran", second.getString("username"));
        Assertions.assertEquals("The waves are amazing", second.getString("feedback"));
        Assertions.assertEquals(1, second.getInt("timestamp"));
        
        Assertions.assertEquals("JoranNieuwpoort2", first.getString("feedbackID"));
        Assertions.assertEquals("Nieuwpoort", first.getString("location"));
        Assertions.assertEquals("Joran", first.getString("username"));
        Assertions.assertEquals("The water is pretty cold", first.getString("feedback"));
        Assertions.assertEquals(2, first.getInt("timestamp"));
	}
	
	private static String getBodyOldLocation() {
		return 
				"""
				{
				    "feedback": {
				        "Location": "Nieuwpoort",
				        "Username": "Joran",
				        "Comment": "The water is pretty cold",
				        "SentAt": 2
				    }
				}
				""";
	}
	
	private static String getBodyNewLocation() {
		return 
				"""
				{
				    "feedback": {
				        "Location": "Westende",
				        "Username": "Lobke",
				        "Comment": "The water is pretty cold",
				        "SentAt": 2
				    }
				}
				""";
	}
	
}
