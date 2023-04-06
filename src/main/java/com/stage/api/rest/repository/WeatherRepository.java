package com.stage.api.rest.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stage.api.rest.entity.Weather;

@Repository
public class WeatherRepository {
	
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public List<Weather> getLocations() {
	
	List<Weather> weather = new ArrayList<>();
	MapSqlParameterSource paramSource = new MapSqlParameterSource();
	List<Map<String, Object>> weatherList = jdbcTemplate.queryForList("SELECT DISTINCT ON (Loc) * FROM Kiten;", paramSource);
	
	for(Map<String, Object> w : weatherList) {
			weather.add(createWeatherObject(w));
		}
		
		return weather;
	}
	
	public List<Weather> getWeather() {
		List<Weather> weather = new ArrayList<>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		List<Map<String, Object>> weatherList = jdbcTemplate.queryForList("SELECT DISTINCT ON (Loc) * FROM Kiten ORDER BY Loc, TimestampMeasurment DESC;", paramSource);
		for(Map<String, Object> w : weatherList) {
			weather.add(createWeatherObject(w));
		}
		
		return weather;
	}
	
	public Weather getWeatherByLocation(String location) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("location", location);
		List<Map<String, Object>> weatherList = jdbcTemplate.queryForList("SELECT * FROM Kiten WHERE Loc = :location ORDER BY TimestampMeasurment DESC LIMIT 1;", paramSource);
		
		return createWeatherObject(weatherList.get(0));
	}
	
	public Weather getWindspeedByLocation(String location) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("location", location);
		List<Map<String, Object>> weatherList = jdbcTemplate.queryForList("SELECT * FROM Kiten WHERE Loc = :location ORDER BY TimestampMeasurment DESC LIMIT 1;", paramSource);
		
		return createWeatherObject(weatherList.get(0));
	}
	
	public Weather getWaveheightByLocation(String location) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("location", location);
		List<Map<String, Object>> weatherList = jdbcTemplate.queryForList("SELECT * FROM Kiten WHERE Loc = :location ORDER BY TimestampMeasurment DESC LIMIT 1;", paramSource);
		
		return createWeatherObject(weatherList.get(0));
	}
	
	public Weather getWinddirectionByLocation(String location) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("location", location);
		List<Map<String, Object>> weatherList = jdbcTemplate.queryForList("SELECT * FROM Kiten WHERE Loc = :location ORDER BY TimestampMeasurment DESC LIMIT 1;", paramSource);
		
		return createWeatherObject(weatherList.get(0));
	}
	
	private static Weather createWeatherObject(Map<String, Object> rs) {
		return new Weather(rs.get("dataid").toString(), rs.get("loc").toString(), rs.get("windspeed").toString(), rs.get("windspeedunit").toString(), rs.get("waveheight").toString(), rs.get("waveheightunit").toString(), rs.get("winddirection").toString(), rs.get("winddirectionunit").toString(), (int) rs.get("timestampmeasurment"));
	}
}
	