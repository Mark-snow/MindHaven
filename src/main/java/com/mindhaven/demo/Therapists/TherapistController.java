package com.mindhaven.demo.Therapists;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/therapists")
public class TherapistController {

    private final GoogleMapsService googleMapsService;

    public TherapistController(GoogleMapsService googleMapsService) {
        this.googleMapsService = googleMapsService;
    }

    @GetMapping("/nearby")
    public List<TherapistDto> getNearbyTherapists(
        @RequestParam double latitude,
        @RequestParam double longitude,
        @RequestParam int radius) {
        return googleMapsService.findNearbyTherapists(latitude, longitude, radius);
    }
}
