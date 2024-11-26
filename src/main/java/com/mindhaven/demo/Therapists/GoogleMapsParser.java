package com.mindhaven.demo.Therapists;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleMapsParser {

    public List<TherapistDto> parseTherapistResponse(String jsonResponse) {
        List<TherapistDto> therapists = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode results = rootNode.get("results");

            if (results != null && results.isArray()) {
                for (JsonNode result : results) {
                    TherapistDto therapist = new TherapistDto();
                    therapist.setIcon(result.get("icon").asText());
                    therapist.setName(result.get("name").asText());
                    therapist.setRating(result.has("rating") ? result.get("rating").asDouble() : null);
                    therapist.setVicinity(result.get("vicinity").asText());

                    // Handle photos
                    List<String> photoReferences = new ArrayList<>();
                    if (result.has("photos")) {
                        for (JsonNode photo : result.get("photos")) {
                            photoReferences.add(photo.get("photo_reference").asText());
                        }
                    }
                    therapist.setPhotos(photoReferences);

                    therapists.add(therapist);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return therapists;
    }
}
