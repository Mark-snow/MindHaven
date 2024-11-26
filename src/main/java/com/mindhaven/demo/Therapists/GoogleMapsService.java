package com.mindhaven.demo.Therapists;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleMapsService {

    @Value("${google.maps.key}")
    private String apiKey;

    @Autowired
    private GoogleMapsParser googleMapsParser;

    public GoogleMapsService(GoogleMapsParser googleMapsParser) {
        this.googleMapsParser = googleMapsParser;
    }

    public List<TherapistDto> findNearbyTherapists(double latitude, double longitude, int radius) {
        String url = String.format(
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%f,%f&radius=%d&type=doctor&keyword=therapist&key=%s",
            latitude, longitude, radius, apiKey
        );

        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(url, String.class);

        return googleMapsParser.parseTherapistResponse(jsonResponse);
    }
}

