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

import com.stage.api.rest.infrastructure.DistanceMatrixInfrastructure;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DistanceMatrixService.class)
@ActiveProfiles("test")
public class DistanceMatrixServiceTest {

	@Autowired
	private DistanceMatrixService distanceMatrixService;
	
	@MockBean
	private DistanceMatrixInfrastructure distanceMatrixInfrastructure;
	
	@Test
	public void getDistanceMatrixTest() {
		String distanceMatrix = "This is the distance matrix from location A to location B";
		
		Mockito.when(distanceMatrixInfrastructure.getDistanceMatrixResponse(Mockito.anyString())).thenReturn(distanceMatrix);
		
		Map<String, String> body = new HashMap<>();
		body.put("url", "http://url/to/distanceMatrix.ai");
		
		String response = distanceMatrixService.getDistanceMatrixResponse(new JSONObject(body).toString());
		
		Assertions.assertEquals(distanceMatrix, response);
	}
	
	@Test // JSONError
	public void getDistanceMatrixWrongBodyTest() {
		String distanceMatrix = "This is the distance matrix from location A to location B";
		
		Mockito.when(distanceMatrixInfrastructure.getDistanceMatrixResponse(Mockito.anyString())).thenReturn(distanceMatrix);
		
		Map<String, String> body = new HashMap<>();
		body.put("NotAnUrl", "http://url/to/distanceMatrix.ai");
		
		Assertions.assertThrows(JSONException.class, () -> distanceMatrixService.getDistanceMatrixResponse(new JSONObject(body).toString()));
	}
	
}
