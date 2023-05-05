package com.stage.api.rest.infrastructure;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.stage.FeedbackGiven;
import com.stage.SignOutRegistered;
import com.stage.SubscriptionRegistered;
import com.stage.api.rest.properties.KafkaTopicProperties;

@Service
public class SubscriptionInfrastructure {

	private final KafkaTemplate<String, SubscriptionRegistered> kafkaSubscriptionTemplate;
	private final KafkaTemplate<String, SignOutRegistered> kafkaSignOutTemplate;
	private final KafkaTopicProperties kafkaProperties;
	
	public SubscriptionInfrastructure(
			KafkaTemplate<String, SubscriptionRegistered> kafkaSubscriptionTemplate,
			KafkaTemplate<String, SignOutRegistered> kafkaSignOutTemplate,
			KafkaTopicProperties kafkaProperties
	) {
		this.kafkaSubscriptionTemplate = kafkaSubscriptionTemplate;
		this.kafkaSignOutTemplate = kafkaSignOutTemplate;
		this.kafkaProperties = kafkaProperties;
	}
	
	public void publishSubscription(String body) {
		try {
			JSONObject json = new JSONObject(body);
			JSONObject subJson = json.getJSONObject("subscription");
			String location = subJson.getString("Location");
			String username = subJson.getString("Username");
			
			SubscriptionRegistered sub = new SubscriptionRegistered();
			sub.setSubscriptionID(String.format("%s%s%s", username, location, System.currentTimeMillis()));
			sub.setUsername(username);
			sub.setLocation(location);
			sub.setTimestamp(System.currentTimeMillis());
			
			kafkaSubscriptionTemplate.send(kafkaProperties.getTopicSubscription(), sub.getSubscriptionID(), sub);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void publishSignOut(String body) {
		try {
			JSONObject json = new JSONObject(body);
			JSONObject signout = json.getJSONObject("signout");
			String location = signout.getString("Location");
			String username = signout.getString("Username");
			
			SignOutRegistered sub = new SignOutRegistered();
			sub.setSubscriptionID(String.format("%s%s%s", username, location, System.currentTimeMillis()));
			sub.setUsername(username);
			sub.setLocation(username);
			sub.setTimestamp(System.currentTimeMillis());
			kafkaSignOutTemplate.send(kafkaProperties.getTopicSubscription(), sub.getSubscriptionID(), sub);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
