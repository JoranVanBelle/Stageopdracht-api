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
		paramSource.addValue("emailaddress", sub.getUsername());
		paramSource.addValue("location", sub.getLocation());
		return jdbcTemplate.update("INSERT INTO keepUpdated(Location, Email) VALUES (:location, :emailaddress);", paramSource);
	}
	
	public int deleteSubscription(SignOutRegistered signout) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("emailaddress", signout.getUsername());
		paramSource.addValue("location", signout.getLocation());
		return jdbcTemplate.update("DELETE FROM keepUpdated WHERE Location = :location AND Email = :emailaddress", paramSource);
	}
	
}
