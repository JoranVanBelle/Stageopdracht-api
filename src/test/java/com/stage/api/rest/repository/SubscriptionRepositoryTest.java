package com.stage.api.rest.repository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stage.FeedbackGiven;
import com.stage.SignOutRegistered;
import com.stage.SubscriptionRegistered;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SubscriptionRepository.class)
@ActiveProfiles("test")
public class SubscriptionRepositoryTest {

	@Autowired
	private SubscriptionRepository subRepository;
	
	@MockBean
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@MockBean
	private KafkaProducer<String, FeedbackGiven> producer;
	
	@Test
	public void postSubscriptionTest() {
		SubscriptionRegistered sub = new SubscriptionRegistered();
		
        
        Mockito.when(jdbcTemplate.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class)))
        	.thenReturn(1);
        
        subRepository.postSubscription(sub);
        
        verify(jdbcTemplate, times(1)).update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class));
	}
	
	@Test
	public void deleteSubscriptionTest() {
		SignOutRegistered signout = new SignOutRegistered();
        
        Mockito.when(jdbcTemplate.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class)))
        	.thenReturn(1);
        
        subRepository.deleteSubscription(signout);
        
        verify(jdbcTemplate, times(1)).update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class));
	}
}
