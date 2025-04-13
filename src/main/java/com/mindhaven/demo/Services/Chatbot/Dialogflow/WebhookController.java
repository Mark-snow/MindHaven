package com.mindhaven.demo.Services.Chatbot.Dialogflow;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("api/open/dialogflow/webhook")
@CrossOrigin("*")

public class WebhookController {

    @PostMapping
    public ResponseEntity<Map<String, Object>> handleDialogflowWebhook(@RequestBody Map<String, Object> request) {
        String intent = extractIntent(request);
        if ("Find Therapist".equals(intent) || "therapy - yes - custom".equals(intent)) {
            String location = extractParameter(request, "location");
            if (location != null) {
                String responseText = findTherapists(location);
                return ResponseEntity.ok(createResponse(responseText));
            } else {
                return ResponseEntity.ok(createResponse("Which city are you looking in?"));
            }
        }
        return ResponseEntity.ok(createResponse("I'm not sure how to help with that."));
    }

    private String extractIntent(Map<String, Object> request) {
        Map<String, Object> queryResult = (Map<String, Object>) request.get("queryResult");
        Map<String, Object> intent = (Map<String, Object>) queryResult.get("intent");
        return (String) intent.get("displayName");
    }

    private String extractParameter(Map<String, Object> request, String paramName) {
        Map<String, Object> parameters = (Map<String, Object>) ((Map<String, Object>) request.get("queryResult")).get("parameters");
        return parameters.get(paramName) != null ? parameters.get(paramName).toString() : null;
    }

    private Map<String, Object> createResponse(String text) {
        return Map.of("fulfillmentText", text);
    }

    private String findTherapists(String location) {
        // Call Google Places API
        String apiKey = "AIzaSyAKoljaCdNC4_RYeJAI7Iqsa6Wav9zFYSI";
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=therapist+in+" 
                     + location + "&key=" + apiKey;
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Parse response (simplified)
        return "Here are some therapists in " + location + ": " + response.getBody();
    }
}

