package com.stage.api.rest.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.stage.api.rest.entity.Weather;
import com.stage.api.rest.repository.WeatherRepository;
import com.stage.api.rest.service.NotificationService;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
@RequestMapping("/api/notification")
public class NotificationController {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	
	public NotificationController(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@GetMapping(value="/weather", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter  getWeatherNotification() {
	    SseEmitter emitter = new SseEmitter();
	    ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
	    sseMvcExecutor.execute(() -> {
	    	NotificationService notificationService;
	        try {
	        	notificationService = createNotificationService();
	            for (int i = 0; true; i++) {
	            	for(Weather w : notificationService.getWeathernotifications()) {
		                SseEventBuilder event = SseEmitter.event()	
		  	                  .data(createNotificationContent(w)+"\n\n")
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
	
	public NotificationService createNotificationService() {
		return new NotificationService(new WeatherRepository(jdbcTemplate));
	}
	
	private JSONObject createNotificationContent(Weather w) {
		JSONObject content = new JSONObject();
		content.put("dataID", w.getDataID());
		content.put("location", w.getLocation());
		content.put("windspeed", w.getWindspeed());
		content.put("windspeedunit", w.getWindspeedUnit());
		content.put("waveheight", w.getWaveheight());
		content.put("waveheightunit", w.getWaveheightUnit());
		content.put("winddirection", w.getWinddirection());
		content.put("winddirectionunit", w.getWinddirectionUnit());
		content.put("timestamp", w.getTimestamp());
		
		return content;
	}
}