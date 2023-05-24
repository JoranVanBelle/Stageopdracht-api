package com.stage.api.rest.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stage.FeedbackGiven;
import com.stage.api.rest.entity.Feedback;
import com.stage.api.rest.infrastructure.FeedbackInfrastructure;
import com.stage.api.rest.repository.FeedbackRepository;
import com.stage.api.rest.service.FeedbackService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FeedbackService.class)
@ActiveProfiles("test")
public class FeedbackServiceTest {
	
	@Autowired
	private FeedbackService feedbackService;
	
	@MockBean
	private FeedbackRepository feedbackRepository;
	
	@MockBean
	private FeedbackInfrastructure feedbackInfrastructure;
	
	@Test
	public void getFeedbackLocationTest() {
		List<Feedback> feedbackList = new ArrayList<>();
		feedbackList.add(new Feedback("JoranNieuwpoort1", "Nieuwpoort", "Joran", "The waves are amazing", 1));
		feedbackList.add(new Feedback("LobkeDe Panne1", "De Panne", "Lobke", "The waves are a little low", 1));
		feedbackList.add(new Feedback("JoranNieuwpoort2", "Nieuwpoort", "Joran", "The waves are still amazing", 2));
		feedbackList.add(new Feedback("LobkeDe Panne2", "De Panne", "Lobke", "The wind is pretty cold", 2));
		
		Mockito.when(feedbackRepository.getFeedbackForLocation("Nieuwpoort")).thenReturn(feedbackList.stream().filter(elem -> elem.getLocation().equals("Nieuwpoort")).collect(Collectors.toList()));
		Mockito.when(feedbackRepository.getFeedbackForLocation("De Panne")).thenReturn(feedbackList.stream().filter(elem -> elem.getLocation().equals("De Panne")).collect(Collectors.toList()));
		
		List<Feedback> nieuwpoortResponse = feedbackService.getFeedbackFromLocation("Nieuwpoort");
		List<Feedback> dePanneResponse = feedbackService.getFeedbackFromLocation("De Panne");
		
		Assertions.assertEquals("JoranNieuwpoort2", nieuwpoortResponse.get(1).getFeedbackID());
		Assertions.assertEquals("Nieuwpoort", nieuwpoortResponse.get(1).getLocation());
		Assertions.assertEquals("Joran", nieuwpoortResponse.get(1).getUsername());
		Assertions.assertEquals("The waves are still amazing", nieuwpoortResponse.get(1).getFeedback());
		Assertions.assertEquals(2, nieuwpoortResponse.get(1).getTimestamp());

		Assertions.assertEquals("LobkeDe Panne2", dePanneResponse.get(1).getFeedbackID());
		Assertions.assertEquals("De Panne", dePanneResponse.get(1).getLocation());
		Assertions.assertEquals("Lobke", dePanneResponse.get(1).getUsername());
		Assertions.assertEquals("The wind is pretty cold", dePanneResponse.get(1).getFeedback());
		Assertions.assertEquals(2, dePanneResponse.get(1).getTimestamp());
	}
	
	@Test
	public void getFeedback() {
		List<Feedback> feedbackList = new ArrayList<>();
		feedbackList.add(new Feedback("JoranNieuwpoort1", "Nieuwpoort", "Joran", "The waves are amazing", 1));
		feedbackList.add(new Feedback("LobkeDe Panne1", "De Panne", "Lobke", "The waves are a little low", 1));
		feedbackList.add(new Feedback("JoranNieuwpoort2", "Nieuwpoort", "Joran", "The waves are still amazing", 2));
		feedbackList.add(new Feedback("LobkeDe Panne2", "De Panne", "Lobke", "The wind is pretty cold", 2));
		
		Mockito.when(feedbackRepository.getFeedback()).thenReturn(feedbackList);
		
		List<Feedback> feedbackResponse = feedbackService.getFeedback();
		
		Assertions.assertEquals(4, feedbackResponse.size());

		Assertions.assertEquals("LobkeDe Panne2", feedbackResponse.get(3).getFeedbackID());
		Assertions.assertEquals("JoranNieuwpoort2", feedbackResponse.get(2).getFeedbackID());
		Assertions.assertEquals("LobkeDe Panne1", feedbackResponse.get(1).getFeedbackID());
		Assertions.assertEquals("JoranNieuwpoort1", feedbackResponse.get(0).getFeedbackID());
	}
	
	@Test
	public void publishFeedback() {		
		Assertions.assertDoesNotThrow(() -> feedbackService.publishFeedback(getBody()));
	}
	
	@Test
	public void postFeedbackInDatabase() {
		FeedbackGiven fbg = new FeedbackGiven();
		
		Mockito.when(feedbackRepository.postFeedback(fbg)).thenReturn(1);
		
		Assertions.assertDoesNotThrow(() -> feedbackService.postFeedbackInDatabase(fbg));
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
