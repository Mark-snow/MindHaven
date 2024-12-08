package com.mindhaven.demo.Services.Therapists;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/therapists")
@CrossOrigin("*")

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
