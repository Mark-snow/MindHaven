package com.mindhaven.demo.Services.Gnews;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GNewsApiService {

    private final RestTemplate restTemplate;

    @Value("${gnews.api.key}")
    private String apiKey;

    public GNewsApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getMentalHealthArticles() {
        String url = "https://gnews.io/api/v4/search?q=mental+health&lang=en&apikey=" + apiKey; // Replace with the actual WHO API URL
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("key", "mental-health")
                .toUriString();

        return restTemplate.getForObject(uri, String.class);
    }

    public String getMentalHealthNews() {
        String url = "https://gnews.io/api/v4/search?q=mental+health+news&lang=en&apikey=" + apiKey; // Replace with the actual WHO API URL
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("key", "mental-health-news")
                .toUriString();

        return restTemplate.getForObject(uri, String.class);
    }


    public String getCustomMentalHealthArticles(String keywords) {
        String filter = spaceToPlus(keywords);
        String url = "https://gnews.io/api/v4/search?q=mental+health+"+ filter + "&lang=en&apikey=" + apiKey; // Replace with the actual WHO API URL
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("key", filter)
                .toUriString();

        return restTemplate.getForObject(uri, String.class);
    }

    //replaces whitespaces with addition signs
    public String spaceToPlus(String str) {
        StringBuffer output = new StringBuffer();

        for(int i=0; i<str.length(); i++) {
            if(Character.isAlphabetic(str.charAt(i))) {
                output.append(str.charAt(i));
            }
            else if(Character.isWhitespace(str.charAt(i))) {
                output.append("+");
            }
        }

        return output.toString();

    }
} 

