package com.stage.api.rest.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.stage.api.rest.entity.Weather;
import com.stage.api.rest.service.NotificationService;
import com.stage.api.rest.service.WeatherService;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	private final NotificationService notificationService;
	
	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	@GetMapping(value="/weather", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter  getWeatherNotification() {
	    SseEmitter emitter = new SseEmitter();
	    ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
	    sseMvcExecutor.execute(() -> {
	        try {
	            for (int i = 0; true; i++) {
	            	for(Weather w : notificationService.getWeathernotifications()) {
		                SseEventBuilder event = SseEmitter.event()	
		  	                  .data(w)
		  	                  .id(String.valueOf(i))
		  	                  .name("Sse weather");
		  	                emitter.send(event);
		  	                Thread.sleep(1000);
	            	}
	            }
	        } catch (Exception ex) {
	            emitter.completeWithError(ex);
	        }
	    });
	    return emitter;
	}
}
