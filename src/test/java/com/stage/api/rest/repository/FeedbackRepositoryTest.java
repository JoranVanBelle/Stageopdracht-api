package com.stage.api.rest.repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stage.FeedbackGiven;
import com.stage.api.rest.entity.Feedback;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FeedbackRepository.class)
@ActiveProfiles("test")
public class FeedbackRepositoryTest {

	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@MockBean
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@MockBean
	private KafkaProducer<String, FeedbackGiven> producer;
	
	@Test
	public void getFeedbackForLocationTest() {
		List<Map<String, Object>> mockResult = mockFeedback();
		
		Mockito.when(jdbcTemplate.queryForList(Mockito.anyString(), Mockito.any(SqlParameterSource.class)))
			.thenReturn(mockResult);
		
		List<Feedback> response = feedbackRepository.getFeedbackForLocation("Nieuwpoort");
		
		Assertions.assertEquals(2, response.size());
		Assertions.assertEquals("JoranNieuwpoort1", response.get(0).getFeedbackID());
		Assertions.assertEquals("JoranNieuwpoort2", response.get(1).getFeedbackID());
	}
	
	@Test
	public void getFeedbackTest() {
		List<Map<String, Object>> mockResult = mockFeedbackList();
		
		Mockito.when(jdbcTemplate.queryForList(Mockito.anyString(), Mockito.any(SqlParameterSource.class)))
			.thenReturn(mockResult);
		
		List<Feedback> response = feedbackRepository.getFeedback();
		
		Assertions.assertEquals(2, response.size());
		Assertions.assertEquals("JoranNieuwpoort1", response.get(0).getFeedbackID());
		Assertions.assertEquals("LobkeDe Panne1", response.get(1).getFeedbackID());
	}
	
	@Test
	public void publishFeedbackTest() {		
		feedbackRepository.publishFeedback(getBody());
		
		verify(producer, times(1)).send(any(ProducerRecord.class));
		verify(producer, times(1)).flush();
	}
	
	@Test
	public void postFeedbackTest() {
		FeedbackGiven feedback = new FeedbackGiven();
        feedback.setFeedbackID("JoranNieuwpoort1");
        feedback.setLocation("Nieuwpoort");
        feedback.setUsername("Joran");
        feedback.setComment("The waves are amazing");
        feedback.setSentAt(1);
        
        Mockito.when(jdbcTemplate.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class)))
        	.thenReturn(1);
        
        feedbackRepository.postFeedback(feedback);
        
        verify(jdbcTemplate, times(1)).update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class));
	}
	
	private List<Map<String, Object>> mockFeedbackList() {
		List<Map<String, Object>> mockResult = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("feedbackid", "JoranNieuwpoort1");
        map1.put("loc", "Nieuwpoort");
        map1.put("username", "Joran");
        map1.put("feedback", "The waves are amazing");
        map1.put("timestampfeedback", 1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("feedbackid", "LobkeDe Panne1");
        map2.put("loc", "De Panne");
        map2.put("username", "Lobke");
        map2.put("feedback", "The waves are pretty low");
        map2.put("timestampfeedback", 1);
        
        mockResult.add(map1);
        mockResult.add(map2);
        
        return mockResult;
	}
	
	private List<Map<String, Object>> mockFeedback() {
		List<Map<String, Object>> mockResult = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("feedbackid", "JoranNieuwpoort1");
        map1.put("loc", "Nieuwpoort");
        map1.put("username", "Joran");
        map1.put("feedback", "The waves are amazing");
        map1.put("timestampfeedback", 1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("feedbackid", "JoranNieuwpoort2");
        map2.put("loc", "Nieuwpoort");
        map2.put("username", "Joran");
        map2.put("feedback", "The waves are still amazing");
        map2.put("timestampfeedback", 2);
        
        mockResult.add(map1);
        mockResult.add(map2);
        
        return mockResult;
	}
	
	private static String getBody() {
		return 
				"""
				{
				    "feedback": {
				        "Location": "Nieuwpoort",
				        "Username": "Joran",
				        "Comment": "The waves are amazing!!",
				        "SentAt": 1
				    }
				}
				""";
	}
}
