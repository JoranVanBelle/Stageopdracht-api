package com.stage.api.rest.integration;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.stage.api.rest.controller.DistanceMatrixController;
import com.stage.api.rest.infrastructure.DistanceMatrixInfrastructure;
import com.stage.api.rest.service.DistanceMatrixService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {DistanceMatrixController.class, DistanceMatrixService.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DistanceMatrixIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DistanceMatrixInfrastructure distanceMatrixInfrastructure;
	
	@Test
	public void distanceMatrixIntegrationTest() throws Exception {
		
		String distanceMatrix = "This is the distance matrix between A and B";
		
		Mockito.when(distanceMatrixInfrastructure.getDistanceMatrixResponse(Mockito.anyString())).thenReturn(distanceMatrix);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/distancematrix/0,0/1,1")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(200, result.getResponse().getStatus());
		Assertions.assertEquals(distanceMatrix, result.getResponse().getContentAsString());
	}
	
}
