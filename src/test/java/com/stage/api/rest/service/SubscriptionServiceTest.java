package com.stage.api.rest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stage.SignOutRegistered;
import com.stage.SubscriptionRegistered;
import com.stage.api.rest.infrastructure.SubscriptionInfrastructure;
import com.stage.api.rest.repository.SubscriptionRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SubscriptionService.class)
@ActiveProfiles("test")
public class SubscriptionServiceTest {

	@Autowired
	private SubscriptionService subscriptionService;
	
	@MockBean
	private SubscriptionRepository subRepo;
	
	@MockBean
	private SubscriptionInfrastructure subInfra;
	
	@Test
	public void publicSubscriptionTest() {
		Assertions.assertDoesNotThrow(() -> subscriptionService.publishSubscription(getSubBody()));
	}
	
	@Test
	public void publicSignoutTest() {
		Assertions.assertDoesNotThrow(() -> subscriptionService.publishSignOut(getSignoutBody()));
	}
	
	@Test
	public void postSubscriptionTest() {
		SubscriptionRegistered sub = new SubscriptionRegistered();
		
		Mockito.when(subRepo.postSubscription(sub)).thenReturn(1);
		
		Assertions.assertDoesNotThrow(() -> subscriptionService.postSubscription(sub));
	}
	
	@Test
	public void postSignoutTest() {
		SignOutRegistered signout = new SignOutRegistered();
		
		Mockito.when(subRepo.deleteSubscription(new SignOutRegistered())).thenReturn(1);
		
		Assertions.assertDoesNotThrow(() -> subscriptionService.postSignout(signout));
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
