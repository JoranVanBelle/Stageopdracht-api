package com.stage.api.rest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.stage.api.rest.service.DistanceMatrixService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = DistanceMatrixController.class)
@ActiveProfiles("test")
public class DistanceMatrixControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DistanceMatrixService distanceMatrixService;
	
	@Test
	public void getDistanceMatrixTest() throws Exception {
		String distanceMatrix = "This is the distance matrix from location A to location B";
		
		Mockito.when(distanceMatrixService.getDistanceMatrixResponse(Mockito.anyString())).thenReturn(distanceMatrix);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/distancematrix")
				.content("http://url/to/distanceMatrix.ai")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(200, result.getResponse().getStatus());
		Assertions.assertEquals(distanceMatrix, result.getResponse().getContentAsString());
	}
	
	@Test
	public void getDistanceMatrixTwiceTest() throws Exception {
		String distanceMatrix = "This is the distance matrix from location A to location B";
		
		Mockito.when(distanceMatrixService.getDistanceMatrixResponse(Mockito.anyString())).thenReturn(distanceMatrix);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/distancematrix")
				.content("http://url/to/distanceMatrix.ai")
				.accept(MediaType.APPLICATION_JSON);
		
		Thread.sleep(1000);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MvcResult result2 = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(200, result.getResponse().getStatus());
		Assertions.assertEquals(distanceMatrix, result.getResponse().getContentAsString());
		
		Assertions.assertEquals(200, result2.getResponse().getStatus());
		assertThat(result2.getResponse().getContentAsString(), containsString("few seconds"));
	}
	
}
