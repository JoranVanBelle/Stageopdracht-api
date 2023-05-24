package com.stage.api.rest.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stage.api.rest.infrastructure.PtvInfrastructure;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PtvService.class)
@ActiveProfiles("test")
public class PtvServiceTest {

	@Autowired
	private PtvService ptvService;
	
	@MockBean
	private PtvInfrastructure ptvInfrastructure;
	
	
	@Test
	public void getDistanceMatrixTest() {
		String distanceMatrix = "This is the distance matrix from location A to location B";
		
		Mockito.when(ptvInfrastructure.getGeocoding(Mockito.anyString())).thenReturn(distanceMatrix);
		
		Map<String, String> body = new HashMap<>();
		body.put("url", "http://url/to/ptv.com");
		
		String response = ptvService.getGeocoding(new JSONObject(body).toString());
		
		Assertions.assertEquals(distanceMatrix, response);
	}
	
	@Test // JSONError
	public void getDistanceMatrixWrongBodyTest() {
		String distanceMatrix = "This is the distance matrix from location A to location B";
		
		Mockito.when(ptvInfrastructure.getGeocoding(Mockito.anyString())).thenReturn(distanceMatrix);
		
		Map<String, String> body = new HashMap<>();
		body.put("NotAnUrl", "http://url/to/ptv.com");
		
		Assertions.assertThrows(JSONException.class, () -> ptvService.getGeocoding(new JSONObject(body).toString()));
	}
}
