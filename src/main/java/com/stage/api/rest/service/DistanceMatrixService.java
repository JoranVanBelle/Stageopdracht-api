package com.stage.api.rest.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.stage.api.rest.infrastructure.DistanceMatrixInfrastructure;

@Service
public class DistanceMatrixService {

	private final DistanceMatrixInfrastructure distanceMatrixInfrastructure;
	
	public DistanceMatrixService(DistanceMatrixInfrastructure distanceMatrixInfrastructure) {
		this.distanceMatrixInfrastructure = distanceMatrixInfrastructure;
	}
	
	public String getDistanceMatrixResponse(String body) {
		
		String distanceMatrixUrl = String.format("%s%s", new JSONObject(body).getString("url"), System.getenv("DISTANCE_MATRIX_API_TOKEN"));
		
		return distanceMatrixInfrastructure.getDistanceMatrixResponse(distanceMatrixUrl);
	}
	
}
