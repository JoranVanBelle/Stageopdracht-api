package com.stage.api.rest.infrastructure;

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

import com.stage.api.rest.controller.PtvController;
import com.stage.api.rest.service.PtvService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {PtvController.class, PtvService.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PtvIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PtvInfrastructure ptvInfrastructure;
	
	@Test
	public void distanceMatrixIntegrationTest() throws Exception {
		
		String distanceMatrix = "This is the geocode of location A";
		
		Mockito.when(ptvInfrastructure.getGeocoding(Mockito.anyString())).thenReturn(distanceMatrix);
		

		Map<String, String> body = new HashMap<>();
		body.put("url", "http://url/to/distanceMatrix.ai");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/ptv")
				.content(new JSONObject(body).toString())
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(200, result.getResponse().getStatus());
		Assertions.assertEquals(distanceMatrix, result.getResponse().getContentAsString());
	}
	
}
