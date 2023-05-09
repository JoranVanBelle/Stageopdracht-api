package com.stage.api.rest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import com.stage.api.rest.service.SubscriptionService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = SubscriptionController.class)
@ActiveProfiles("test")
public class SubscriptionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SubscriptionService SubscriptionService;
	
	@Test
	public void postSubscriptionTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/subscription/subscribe")
				.content(getSubBody())
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(200, result.getResponse().getStatus());
	}
	
	@Test
	public void postSignoutTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/subscription/signout")
				.content(getSignoutBody())
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(200, result.getResponse().getStatus());
	}
	
	private static String getSubBody() {
		return 
				"""
				{
				    "subscription": {
				        "Location": "Nieuwpoort",
				        "Username": "Joran"
				    }
				}
				""";
	}
	
	private static String getSignoutBody() {
		return 
				"""
				{
				    "signout": {
				        "Location": "Nieuwpoort",
				        "Username": "Joran"
				    }
				}
				""";
	}
}
