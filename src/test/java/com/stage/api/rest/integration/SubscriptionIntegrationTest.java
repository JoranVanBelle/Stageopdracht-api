package com.stage.api.rest.integration;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.stage.api.rest.extension.KafkaTestcontainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(KafkaTestcontainer.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class SubscriptionIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void subscribeToEmails() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/subscription/subscribe")
				.content(getSubBody())
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder).andReturn();
		
		Thread.sleep(2000);
		
		
		
		Map<String, Object> res = jdbcTemplate.queryForList("SELECT * FROM keepUpdated").get(0);
		
		Assertions.assertEquals("Nieuwpoort", res.get("Location"));
		Assertions.assertEquals("joran.vanbelle2@student.hogent.be", res.get("Email"));
	}
	
	@Test
	public void signoutToEmails() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/subscription/signout")
				.content(getSignoutBody())
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder).andReturn();
		
		Thread.sleep(7000);
		
		List<Map<String, Object>> res = jdbcTemplate.queryForList("SELECT * FROM keepUpdated");
		
		Assertions.assertEquals(0, res.size());
	}
	
	private static String getSubBody() {
		return 
				"""
				{
				    "subscription": {
				        "Location": "Nieuwpoort",
				        "Username": "joran.vanbelle2@student.hogent.be"
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
				        "Username": "joran.vanbelle2@student.hogent.be"
				    }
				}
				""";
	}
}
