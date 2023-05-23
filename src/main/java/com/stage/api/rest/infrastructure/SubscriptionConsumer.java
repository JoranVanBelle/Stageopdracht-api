package com.stage.api.rest.infrastructure;

import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.stage.SignOutRegistered;
import com.stage.SubscriptionRegistered;
import com.stage.api.rest.service.SubscriptionService;

@Service
public class SubscriptionConsumer {

	private final SubscriptionService subscriptionService;
	
	public SubscriptionConsumer(
			SubscriptionService subscriptionService
	) {
		this.subscriptionService = subscriptionService;
	}
	
	@KafkaListener(id="api.subscription", topics = "Kiten.subscription")
	public void saveSubscription(GenericRecord record) {
		
		if(record.getSchema().getName().contains("SubscriptionRegistered")) {
			SubscriptionRegistered sub= (SubscriptionRegistered) SpecificData.get().deepCopy(record.getSchema(), record);
			subscriptionService.postSubscription(sub);
			subscriptionService.sendSubEmail(sub);
		} else if(record.getSchema().getName().contains("SignOutRegistered")) {
			SignOutRegistered signout = (SignOutRegistered) SpecificData.get().deepCopy(record.getSchema(), record);
			subscriptionService.postSignout(signout);
			subscriptionService.sendSignOutEmail(signout);
		}
		
	}
	
}
