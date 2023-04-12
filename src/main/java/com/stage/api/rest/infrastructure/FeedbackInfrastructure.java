package com.stage.api.rest.infrastructure;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.stage.FeedbackGiven;
import com.stage.api.rest.properties.KafkaTopicProperties;

@Service
public class FeedbackInfrastructure {

	private final KafkaTemplate<String, FeedbackGiven> kafkaTemplate;
	private final KafkaTopicProperties kafkaProperties;
	
	public FeedbackInfrastructure(
		KafkaTemplate<String, FeedbackGiven> kafkaTemplate,
		KafkaTopicProperties kafkaProperties
	) {
		this.kafkaTemplate = kafkaTemplate;
		this.kafkaProperties = kafkaProperties;
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
			
			kafkaTemplate.send(kafkaProperties.getTopic(), fb.getFeedbackID(), fb);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
