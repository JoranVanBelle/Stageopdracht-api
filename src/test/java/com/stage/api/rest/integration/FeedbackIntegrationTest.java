package com.stage.api.rest.integration;

import static org.awaitility.Awaitility.await;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
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
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class FeedbackIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void getAllFeedback() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/feedback")
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
				.get("/api/feedback/Nieuwpoort")
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
				.get("/api/feedback/De Panne")
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
				.get("/api/feedback/Kortrijk")
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
				.post("/api/feedback")
				.content(getBodyNewLocation())
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder).andReturn();
		
		await().atMost(5, TimeUnit.SECONDS).until(feedbackIsAdded());
		
		RequestBuilder requestBuilderGet = MockMvcRequestBuilders
				.get("/api/feedback/Westende")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilderGet).andReturn();
		
		String resultString = result.getResponse().getContentAsString();

		JSONArray jsonArray = new JSONArray(resultString);
        List<JSONObject> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            list.add(jsonObject);
            System.err.println(jsonObject);
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
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/feedback")
				.content(getBodyOldLocation());
		
		mockMvc.perform(requestBuilder);

		await().atMost(5, TimeUnit.SECONDS).until(feedbackIsAdded());
		
		RequestBuilder requestBuilderGet = MockMvcRequestBuilders
				.get("/api/feedback/Nieuwpoort")
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
	
	private Callable<Boolean> feedbackIsAdded() {
		List<Map<String, Object>> res = jdbcTemplate.queryForList("SELECT * FROM Feedback");
		int rowsAffected = res.size();
		return () -> rowsAffected > 0;
	}
	
}
