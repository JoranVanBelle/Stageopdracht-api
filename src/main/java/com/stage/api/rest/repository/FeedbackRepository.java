package com.stage.api.rest.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stage.FeedbackGiven;
import com.stage.api.rest.entity.Feedback;

@Repository
public class FeedbackRepository {
	
	@Value("${api.feedback.topic}")
	private String topic;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private KafkaProducer<String, FeedbackGiven> producer;
	
	public List<Feedback> getFeedbackForLocation(String location) {
		
		List<Feedback> feedback = new ArrayList<>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("location", location);
		List<Map<String, Object>> feedbackList = jdbcTemplate.queryForList("SELECT * FROM Feedback WHERE Loc = :location ORDER BY TimestampFeedback DESC;", paramSource);
		
		for(Map<String, Object> fb : feedbackList) {
			feedback.add(createFeedbackObject(fb));
		}
		
		return feedback;
	}
	
	public List<Feedback> getFeedback() {
		List<Feedback> feedback = new ArrayList<>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		List<Map<String, Object>> feedbackList = jdbcTemplate.queryForList("SELECT * FROM Feedback ORDER BY TimestampFeedback DESC;", paramSource);
		
		for(Map<String, Object> fb : feedbackList) {
			feedback.add(createFeedbackObject(fb));
		}
		
		return feedback;
	}
	
	public void publishFeedback(String body) {
		try {
			JSONObject json = new JSONObject(body);
			JSONObject feedback = json.getJSONObject("feedback");
			String location = feedback.getString("Location");
			String username = feedback.getString("Username");
			String comment = feedback.getString("Comment");
			int time = feedback.getInt("SentAt");
			
			FeedbackGiven fb = new FeedbackGiven();
			fb.setFeedbackID(String.format("%s%s%s", username, location, time));
			fb.setLocation(location);
			fb.setUsername(username);
			fb.setComment(comment);
			fb.setSentAt((long) time);
			
			final ProducerRecord<String, FeedbackGiven> record = new ProducerRecord<String, FeedbackGiven>(topic, fb.getFeedbackID(), fb);
			producer.send(record);
			producer.flush();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public int postFeedback(FeedbackGiven feedback) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("feedbackID", feedback.getFeedbackID());
		paramSource.addValue("location", feedback.getLocation());
		paramSource.addValue("username", feedback.getUsername());
		paramSource.addValue("feedback", feedback.getComment());
		paramSource.addValue("timestamp", feedback.getSentAt());
		int rowsAffected = jdbcTemplate.update("INSERT INTO Feedback(FeedbackID, Loc, Username, Feedback, TimestampFeedback) VALUES (:feedbackID, :location, :username, :feedback, :timestamp)", paramSource);
		
		return rowsAffected;
	}
	
	private Feedback createFeedbackObject(Map<String, Object> fb) {
		return new Feedback(fb.get("feedbackid").toString(), fb.get("loc").toString(), fb.get("username").toString(), fb.get("feedback").toString(), Long.parseLong(fb.get("timestampfeedback").toString()));
	}
}
