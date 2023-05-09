package com.stage.api.rest.infrastructure;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.stage.FeedbackGiven;
import com.stage.api.rest.properties.KafkaTopicProperties;

@Service
public class FeedbackInfrastructure {

	private final KafkaTemplate<String, FeedbackGiven> kafkaFeedbackTemplate;
	private final KafkaTopicProperties kafkaProperties;
	
	public FeedbackInfrastructure(
		KafkaTemplate<String, FeedbackGiven> kafkaFeedbackTemplate,
		KafkaTopicProperties kafkaProperties
	) {
		this.kafkaFeedbackTemplate = kafkaFeedbackTemplate;
		this.kafkaProperties = kafkaProperties;
	}
	
	public void publishFeedback(String body) {
		try {
			JSONObject json = new JSONObject(body);
			JSONObject feedback = json.getJSONObject("feedback");
			
			String location = feedback.getString("Location");
			String username = feedback.getString("Username");
			String comment = feedback.getString("Comment");
			long time = feedback.getLong("SentAt");
			
			FeedbackGiven fb = new FeedbackGiven();
			fb.setFeedbackID(String.format("%s%s%s", username, location, time));
			fb.setLocation(location);
			fb.setUsername(username);
			fb.setComment(comment);
			fb.setSentAt(time);
			
			kafkaFeedbackTemplate.send(kafkaProperties.getTopicFeedback(), fb.getFeedbackID(), fb);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
