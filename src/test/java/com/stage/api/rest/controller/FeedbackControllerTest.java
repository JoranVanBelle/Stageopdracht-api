package com.stage.api.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import com.stage.api.rest.entity.Feedback;
import com.stage.api.rest.service.FeedbackService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = FeedbackController.class)
@ActiveProfiles("test")
public class FeedbackControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FeedbackService feedbackService;
	
	@Test
	public void getFeedbackLocationTest() throws Exception {
		List<Feedback> feedbackList = new ArrayList<>();
		feedbackList.add(new Feedback("JoranNieuwpoort1", "Nieuwpoort", "Joran", "The waves are amazing", 1));
		feedbackList.add(new Feedback("LobkeDe Panne1", "De Panne", "Lobke", "The waves are a little low", 1));
		feedbackList.add(new Feedback("JoranNieuwpoort2", "Nieuwpoort", "Joran", "The waves are still amazing", 2));
		feedbackList.add(new Feedback("LobkeDe Panne2", "De Panne", "Lobke", "The wind is pretty cold", 2));
		
		Mockito.when(feedbackService.getFeedbackFromLocation("Nieuwpoort")).thenReturn(feedbackList.stream().filter(elem -> elem.getLocation().equals("Nieuwpoort")).collect(Collectors.toList()));
		Mockito.when(feedbackService.getFeedbackFromLocation("De Panne")).thenReturn(feedbackList.stream().filter(elem -> elem.getLocation().equals("De Panne")).collect(Collectors.toList()));
		
		RequestBuilder requestBuilderNieuwpoort = MockMvcRequestBuilders
				.get("/api/feedback/Nieuwpoort")
				.accept(MediaType.APPLICATION_JSON);
		
		RequestBuilder requestBuilderDePanne = MockMvcRequestBuilders
				.get("/api/feedback/De Panne")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult resultNieuwpoort = mockMvc.perform(requestBuilderNieuwpoort).andReturn();
		MvcResult resultDePanne = mockMvc.perform(requestBuilderDePanne).andReturn();
		
		String nieuwpoortExpected = "[{\"feedbackID\":\"JoranNieuwpoort1\",\"location\":\"Nieuwpoort\",\"username\":\"Joran\",\"feedback\":\"The waves are amazing\",\"timestamp\":1},{\"feedbackID\":\"JoranNieuwpoort2\",\"location\":\"Nieuwpoort\",\"username\":\"Joran\",\"feedback\":\"The waves are still amazing\",\"timestamp\":2}]";
		String dePanneExpected = "[{\"feedbackID\":\"LobkeDe Panne1\",\"location\":\"De Panne\",\"username\":\"Lobke\",\"feedback\":\"The waves are a little low\",\"timestamp\":1},{\"feedbackID\":\"LobkeDe Panne2\",\"location\":\"De Panne\",\"username\":\"Lobke\",\"feedback\":\"The wind is pretty cold\",\"timestamp\":2}]";
		
		Assertions.assertEquals(nieuwpoortExpected, resultNieuwpoort.getResponse().getContentAsString());
		Assertions.assertEquals(dePanneExpected, resultDePanne.getResponse().getContentAsString());
	}
	
	@Test
	public void getFeedbackTest() throws Exception {
		List<Feedback> feedbackList = new ArrayList<>();
		feedbackList.add(new Feedback("JoranNieuwpoort1", "Nieuwpoort", "Joran", "The waves are amazing", 1));
		feedbackList.add(new Feedback("LobkeDe Panne1", "De Panne", "Lobke", "The waves are a little low", 1));
		feedbackList.add(new Feedback("JoranNieuwpoort2", "Nieuwpoort", "Joran", "The waves are still amazing", 2));
		feedbackList.add(new Feedback("LobkeDe Panne2", "De Panne", "Lobke", "The wind is pretty cold", 2));
		
		Mockito.when(feedbackService.getFeedback()).thenReturn(feedbackList);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/feedback")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "[{\"feedbackID\":\"JoranNieuwpoort1\",\"location\":\"Nieuwpoort\",\"username\":\"Joran\",\"feedback\":\"The waves are amazing\",\"timestamp\":1},{\"feedbackID\":\"LobkeDe Panne1\",\"location\":\"De Panne\",\"username\":\"Lobke\",\"feedback\":\"The waves are a little low\",\"timestamp\":1},{\"feedbackID\":\"JoranNieuwpoort2\",\"location\":\"Nieuwpoort\",\"username\":\"Joran\",\"feedback\":\"The waves are still amazing\",\"timestamp\":2},{\"feedbackID\":\"LobkeDe Panne2\",\"location\":\"De Panne\",\"username\":\"Lobke\",\"feedback\":\"The wind is pretty cold\",\"timestamp\":2}]";
		
		Assertions.assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	public void postFeedbackTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/feedback")
				.content(getBody())
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(200, result.getResponse().getStatus());
	}
	
	private static String getBody() {
		return 
				"""
				{
				    "feedback": {
				        "Location": "Nieuwpoort",
				        "Username": "Joran",
				        "Comment": "The waves are amazing!!",
				        "SentAt": 1
				    }
				}
				""";
	}
}
