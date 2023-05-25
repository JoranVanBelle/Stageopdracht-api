package com.stage.api.rest.service;

import org.springframework.stereotype.Service;

import com.stage.api.rest.infrastructure.DistanceMatrixInfrastructure;

@Service
public class DistanceMatrixService {

	private final DistanceMatrixInfrastructure distanceMatrixInfrastructure;
	
	public DistanceMatrixService(DistanceMatrixInfrastructure distanceMatrixInfrastructure) {
		this.distanceMatrixInfrastructure = distanceMatrixInfrastructure;
	}
	
	public String getDistanceMatrixResponse(String origin, String dest) {
		
		String distanceMatrixUrl = String.format("https://api.distancematrix.ai/maps/api/distancematrix/json?origins=%s&destinations=%s&mode=driving&departure_time=now&key=%s", origin, dest, System.getenv("DISTANCE_MATRIX_API_TOKEN"));
		
		return distanceMatrixInfrastructure.getDistanceMatrixResponse(distanceMatrixUrl);
	}
	
}
