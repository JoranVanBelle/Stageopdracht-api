package com.stage.api.rest.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.stage.api.rest.infrastructure.PtvInfrastructure;

@Service
public class PtvService {

	private final PtvInfrastructure ptvInfrastructure;
	
	public PtvService(PtvInfrastructure ptvInfrastructure) {
		this.ptvInfrastructure = ptvInfrastructure;
	}
	
	public String getGeocoding(String body) {
		
		String ptvUrl = String.format("%s%s", new JSONObject(body).getString("url"), System.getenv("PTV_API_TOKEN"));
		
		return ptvInfrastructure.getGeocoding(ptvUrl);
	}
	
}
