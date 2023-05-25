package com.stage.api.rest.service;

import org.springframework.stereotype.Service;

import com.stage.api.rest.infrastructure.PtvInfrastructure;

@Service
public class PtvService {

	private final PtvInfrastructure ptvInfrastructure;
	
	public PtvService(PtvInfrastructure ptvInfrastructure) {
		this.ptvInfrastructure = ptvInfrastructure;
	}
	
	public String getGeocoding(String location) {
		
		String ptvUrl = String.format("https://api.myptv.com/geocoding/v1/locations/by-text?searchText=%s&countryFilter=BE&apiKey=%s", location.replaceAll(" ", "%20"), System.getenv("PTV_API_TOKEN"));
		
		return ptvInfrastructure.getGeocoding(ptvUrl);
	}
	
}
