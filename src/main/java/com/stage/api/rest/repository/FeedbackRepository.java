package com.stage.api.rest.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stage.FeedbackGiven;
import com.stage.api.rest.entity.Feedback;

@Repository
public class FeedbackRepository {
	
	private final NamedParameterJdbcTemplate jdbcTemplate;
	
	public FeedbackRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
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
