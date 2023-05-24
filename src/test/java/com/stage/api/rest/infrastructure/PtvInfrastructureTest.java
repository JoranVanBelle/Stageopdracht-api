package com.stage.api.rest.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PtvInfrastructure.class)
@ActiveProfiles("test")
public class PtvInfrastructureTest {
	
    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;
    
    private PtvInfrastructure ptvInfrastructureSpy = Mockito.spy(new PtvInfrastructure());

    @Test
    public void testGetDistanceMatrixResponse() throws Exception {
        String expectedResponse = "API response";

        // Mocking HttpClient and HttpResponse
        
        when(ptvInfrastructureSpy.getNewHttpClient()).thenReturn(httpClient);
        
        when(httpClient.send(any(HttpRequest.class), Mockito.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(expectedResponse);

        // Mocking distanceMatrixUrl
        String distanceMatrixUrl = "http://example.com";

        String actualResponse = ptvInfrastructureSpy.getGeocoding(distanceMatrixUrl);

        // Verify that the HttpClient and HttpRequest were invoked with the correct arguments
        Mockito.verify(httpClient).send(Mockito.eq(HttpRequest.newBuilder()
                .GET()
                .uri(new URI(distanceMatrixUrl))
                .build()), Mockito.<HttpResponse.BodyHandler<String>>any());

        assertEquals(expectedResponse, actualResponse);
    }
}
