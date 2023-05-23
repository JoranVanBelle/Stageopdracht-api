package com.stage.api.rest.service;

import org.springframework.stereotype.Service;

import com.stage.SignOutRegistered;
import com.stage.SubscriptionRegistered;
import com.stage.api.rest.infrastructure.EmailInfrastructure;
import com.stage.api.rest.infrastructure.SubscriptionInfrastructure;
import com.stage.api.rest.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

    private final SubscriptionInfrastructure subInfrastructure;
    private final SubscriptionRepository subRepository;
    private final EmailInfrastructure emailInfrastructure;
    
    public SubscriptionService(SubscriptionInfrastructure subInfrastructure, SubscriptionRepository subRepository, EmailInfrastructure emailInfrastructure) {
    	this.subInfrastructure = subInfrastructure;
    	this.subRepository = subRepository;
    	this.emailInfrastructure = emailInfrastructure;
    }
    
    public void publishSubscription(String body) {
    	subInfrastructure.publishSubscription(body);	
    }
    
    public void publishSignOut(String body) {
    	subInfrastructure.publishSignOut(body);	
    }
    
    public void postSubscription(SubscriptionRegistered sub) {
    	int response = subRepository.postSubscription(sub);
    }
    
    public void postSignout(SignOutRegistered signout) {
    	int response = subRepository.deleteSubscription(signout);
    }
    
    public void sendSubEmail(SubscriptionRegistered sub) {
    	emailInfrastructure.sendSubEmail(sub.getLocation(), sub.getUsername());
    }
    
    public void sendSignOutEmail(SignOutRegistered signout) {
    	emailInfrastructure.sendSignOutEmail(signout.getLocation(), signout.getUsername());
    }
}
