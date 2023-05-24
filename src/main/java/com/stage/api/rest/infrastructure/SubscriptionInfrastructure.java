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

	private final KafkaTemplate<String, SubscriptionRegistered> subscriptionKafkaTemplate;
	private final KafkaTemplate<String, SignOutRegistered> signoutKafkaTemplate;
	private final KafkaTopicProperties kafkaProperties;
	
	public SubscriptionInfrastructure(
			KafkaTemplate<String, SubscriptionRegistered> subscriptionKafkaTemplate,
			KafkaTemplate<String, SignOutRegistered> signoutKafkaTemplate,
			KafkaTopicProperties kafkaProperties
	) {
		this.subscriptionKafkaTemplate = subscriptionKafkaTemplate;
		this.signoutKafkaTemplate = signoutKafkaTemplate;
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
			
			subscriptionKafkaTemplate.send(kafkaProperties.getTopicSubscription(), sub.getSubscriptionID(), sub);
			
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
			
			SignOutRegistered signoutRegistered = new SignOutRegistered();
			signoutRegistered.setSubscriptionID(String.format("%s%s%s", username, location, System.currentTimeMillis()));
			signoutRegistered.setUsername(username);
			signoutRegistered.setLocation(location);
			signoutRegistered.setTimestamp(System.currentTimeMillis());
			signoutKafkaTemplate.send(kafkaProperties.getTopicSubscription(), signoutRegistered.getSubscriptionID(), signoutRegistered);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
