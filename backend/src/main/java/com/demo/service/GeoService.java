package com.demo.service;

//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeoService {
	
    private final String API_KEY = "57b8fe97274841a69d954a9f126913fe";

	public double[] getCoordinates(String location) throws Exception {
	    System.out.println("📍 Fetching coordinates for: " + location);

	    String url = "https://api.opencagedata.com/geocode/v1/json?q=" + location + "&key=" + API_KEY;
	    RestTemplate restTemplate = new RestTemplate();

	    String response = restTemplate.getForObject(url, String.class);
	    System.out.println("📦 OpenCage API Response:\n" + response); // FULL JSON

	    ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode json = objectMapper.readTree(response);

	    JsonNode results = json.get("results");
	    if (results == null || results.isEmpty()) {
	        System.out.println("❌ No results for: " + location);
	        throw new IllegalStateException("No coordinates found for: " + location);
	    }

	    JsonNode geometry = results.get(0).get("geometry");
	    double lat = geometry.get("lat").asDouble();
	    double lng = geometry.get("lng").asDouble();

	    System.out.println("✅ Coords found: " + lat + ", " + lng);
	    return new double[]{lat, lng};
	}

}
