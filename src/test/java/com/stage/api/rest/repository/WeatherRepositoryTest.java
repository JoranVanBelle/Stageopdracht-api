package com.stage.api.rest.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stage.api.rest.entity.Weather;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WeatherRepository.class)
@ActiveProfiles("test")
public class WeatherRepositoryTest {

	@Autowired
	private WeatherRepository weatherRepository;
	
	@MockBean
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Test
	public void getLocationsTest() throws SQLException {
		
		List<Map<String, Object>> mockResult = mockWeatherList();

		Mockito.when(jdbcTemplate.queryForList(Mockito.anyString(), Mockito.any(SqlParameterSource.class)))
			.thenReturn(mockResult);
		
		List<Weather> response = weatherRepository.getLocations();
		
		Assertions.assertEquals(2, response.size());
		Assertions.assertEquals("NieuwpoortKiteable1", response.get(0).getDataID());
		Assertions.assertEquals("De PanneKiteable1", response.get(1).getDataID());
		
	}
	
	@Test
	public void getWeatherTest() {
		List<Map<String, Object>> mockResult = mockWeatherList();

		Mockito.when(jdbcTemplate.queryForList(Mockito.anyString(), Mockito.any(SqlParameterSource.class)))
			.thenReturn(mockResult);
		
		List<Weather> response = weatherRepository.getWeather();
		
		Assertions.assertEquals(2, response.size());
		Assertions.assertEquals("NieuwpoortKiteable1", response.get(0).getDataID());
		Assertions.assertEquals("De PanneKiteable1", response.get(1).getDataID());
	}
	
	@Test
	public void getWeatherByLocationTest() {
		List<Map<String, Object>> mockResult = mockWeather();

		Mockito.when(jdbcTemplate.queryForList(Mockito.anyString(), Mockito.any(SqlParameterSource.class)))
		.thenReturn(mockResult);
	
		Weather response = weatherRepository.getWeatherByLocation("Nieuwpoort");
		
		Assertions.assertEquals("NieuwpoortKiteable2", response.getDataID());
	}
	
	@Test
	public void getWindspeedByLocation() {
		List<Map<String, Object>> mockResult = mockWeather();

		Mockito.when(jdbcTemplate.queryForList(Mockito.anyString(), Mockito.any(SqlParameterSource.class)))
		.thenReturn(mockResult);
	
		Weather response = weatherRepository.getWindspeedByLocation("Nieuwpoort");
		
		Assertions.assertEquals("NieuwpoortKiteable2", response.getDataID());
	}
	
	@Test
	public void getWaveheightByLocation() {
		List<Map<String, Object>> mockResult = mockWeather();

		Mockito.when(jdbcTemplate.queryForList(Mockito.anyString(), Mockito.any(SqlParameterSource.class)))
		.thenReturn(mockResult);
	
		Weather response = weatherRepository.getWaveheightByLocation("Nieuwpoort");
		
		Assertions.assertEquals("NieuwpoortKiteable2", response.getDataID());
	}
	
	@Test
	public void getWinddirectionByLocation() {
		List<Map<String, Object>> mockResult = mockWeather();

		Mockito.when(jdbcTemplate.queryForList(Mockito.anyString(), Mockito.any(SqlParameterSource.class)))
		.thenReturn(mockResult);
	
		Weather response = weatherRepository.getWinddirectionByLocation("Nieuwpoort");
		
		Assertions.assertEquals("NieuwpoortKiteable2", response.getDataID());
	}
	
	private List<Map<String, Object>> mockWeatherList() {
		List<Map<String, Object>> mockResult = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("dataid", "NieuwpoortKiteable1");
        map1.put("loc", "Nieuwpoort");
        map1.put("windspeed", "10.00");
        map1.put("windspeedunit", "m/s");
        map1.put("waveheight", "151.00");
        map1.put("waveheightunit", "cm");
        map1.put("winddirection", "10.00");
        map1.put("winddirectionunit", "deg");
        map1.put("timestampmeasurment", 1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("dataid", "De PanneKiteable1");
        map2.put("loc", "De Panne");
        map2.put("windspeed", "11.00");
        map2.put("windspeedunit", "m/s");
        map2.put("waveheight", "155.00");
        map2.put("waveheightunit", "cm");
        map2.put("winddirection", "11.00");
        map2.put("winddirectionunit", "deg");
        map2.put("timestampmeasurment", 1);
        
        mockResult.add(map1);
        mockResult.add(map2);
        
        return mockResult;
	}
	
	private List<Map<String, Object>> mockWeather() {
		List<Map<String, Object>> mockResult = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("dataid", "NieuwpoortKiteable1");
        map1.put("loc", "Nieuwpoort");
        map1.put("windspeed", "10.00");
        map1.put("windspeedunit", "m/s");
        map1.put("waveheight", "151.00");
        map1.put("waveheightunit", "cm");
        map1.put("winddirection", "10.00");
        map1.put("winddirectionunit", "deg");
        map1.put("timestampmeasurment", 1);
        
        Map<String, Object> map2 = new HashMap<>();
        map1.put("dataid", "NieuwpoortKiteable2");
        map1.put("loc", "Nieuwpoort");
        map1.put("windspeed", "10.00");
        map1.put("windspeedunit", "m/s");
        map1.put("waveheight", "151.00");
        map1.put("waveheightunit", "cm");
        map1.put("winddirection", "10.00");
        map1.put("winddirectionunit", "deg");
        map1.put("timestampmeasurment", 2);
        
        mockResult.add(map1);
        mockResult.add(map2);
        
        return mockResult;
	}
}
