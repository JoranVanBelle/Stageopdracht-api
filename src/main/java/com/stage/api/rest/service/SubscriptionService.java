package com.stage.api.rest.service;

import org.springframework.stereotype.Service;

import com.stage.SignOutRegistered;
import com.stage.SubscriptionRegistered;
import com.stage.api.rest.infrastructure.SubscriptionInfrastructure;
import com.stage.api.rest.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

    private final SubscriptionInfrastructure subInfrastructure;
    private final SubscriptionRepository subRepository;
    
    public SubscriptionService(SubscriptionInfrastructure subInfrastructure, SubscriptionRepository subRepository) {
    	this.subInfrastructure = subInfrastructure;
    	this.subRepository = subRepository;
    }
    
    public void publishSubscription(String body) {
    	subInfrastructure.publishSubscription(body);	
    }
    
    public void publishSignOut(String body) {
    	subInfrastructure.publishSignOut(body);	
    }
    
    public void postSubscription(SubscriptionRegistered sub) {
    	subRepository.postSubscription(sub);
    }
    
    public void postSignout(SignOutRegistered signout) {
    	subRepository.deleteSubscription(signout);
    }
}
