package com.stage.api.rest.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

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

import com.stage.api.rest.service.PtvService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PtvController.class)
@ActiveProfiles("test")
public class PtvControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PtvService ptvService;
	
	@Test
	public void getGeocodingTest() throws Exception {
		String geoCoding = "This is the geocoding of the place where the treasure is buried";
		
		Mockito.when(ptvService.getGeocoding(Mockito.anyString())).thenReturn(geoCoding);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/ptv")
				.content("https://url/to/ptv.com")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(200, result.getResponse().getStatus());
		Assertions.assertEquals(geoCoding, result.getResponse().getContentAsString());
	}
	
	@Test
	public void getGeocodingTwiceTest() throws Exception {
		String geoCoding = "This is the geocoding of the place where the treasure is buried";
		
		Mockito.when(ptvService.getGeocoding(Mockito.anyString())).thenReturn(geoCoding);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/ptv")
				.content("https://url/to/ptv.com")
				.accept(MediaType.APPLICATION_JSON);
		
		Thread.sleep(1000);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MvcResult result2 = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(200, result.getResponse().getStatus());
		Assertions.assertEquals(geoCoding, result.getResponse().getContentAsString());
		
		Assertions.assertEquals(200, result2.getResponse().getStatus());
		assertThat(result2.getResponse().getContentAsString(), containsString("few seconds"));
	}
	
}
