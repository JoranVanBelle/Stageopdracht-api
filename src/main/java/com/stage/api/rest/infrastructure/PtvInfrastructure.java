package com.stage.api.rest.infrastructure;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

@Service
public class PtvInfrastructure {

	public String getGeocoding(String ptvUrl) {
		String apiResponse = "";
		
		HttpClient client = getNewHttpClient();
		HttpRequest request;
		try {
			request = HttpRequest.newBuilder()
					.GET()
					.uri(new URI(ptvUrl))
					.build();
			
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			apiResponse = response.body();
			
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return apiResponse;
	}
	
	public HttpClient getNewHttpClient() {
		return HttpClient.newHttpClient();
	}
	
}
