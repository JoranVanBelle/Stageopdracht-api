package com.stage.api.rest.entity;

public class Feedback {
	
	private String feedbackID;
	private String location;
	private String username;
	private String feedback;
	private long timestamp;
	
	public Feedback(String feedbackID, String location, String username, String feedback, long timestamp) {
		super();
		this.feedbackID = feedbackID;
		this.location = location;
		this.username = username;
		this.feedback = feedback;
		this.timestamp = timestamp;
	}
	public String getFeedbackID() {
		return feedbackID;
	}
	public void setFeedbackID(String feedbackID) {
		this.feedbackID = feedbackID;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
