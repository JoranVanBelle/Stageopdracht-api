package com.stage.api.rest.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stage.SignOutRegistered;
import com.stage.SubscriptionRegistered;

@Repository
public class SubscriptionRepository {
	
	  private final NamedParameterJdbcTemplate jdbcTemplate;

	  public SubscriptionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		  this.jdbcTemplate = jdbcTemplate;
	  }
	
	public int postSubscription(SubscriptionRegistered sub) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("subscriptionID", String.format("%s%s", sub.getUsername(), sub.getLocation()));
		paramSource.addValue("emailaddress", sub.getUsername());
		paramSource.addValue("location", sub.getLocation());
		try {			
			return jdbcTemplate.update("INSERT INTO keepUpdated(SubscriptionID, Location, Email) VALUES (:subscriptionID, :location, :emailaddress);", paramSource);
		} catch (Exception e) {
			System.out.println("User already subscribed");
			return 0;
		}
	}
	
	public int deleteSubscription(SignOutRegistered signout) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("subscriptionID", String.format("%s%s", signout.getUsername(), signout.getLocation()));
		try {
			int response = jdbcTemplate.update("DELETE FROM keepUpdated WHERE SubscriptionID = :subscriptionID;", paramSource);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("User already unsubscribed");
			return 0;
		}
	}
	
}
