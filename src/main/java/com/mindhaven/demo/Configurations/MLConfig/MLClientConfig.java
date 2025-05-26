package com.mindhaven.demo.Configurations.MLConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MLClientConfig {

    @Value("${ml.api.url}")
    private String mlApiUrl;

    @Bean
    public WebClient mlWebClient() {
        return WebClient.builder()
            .baseUrl(mlApiUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
}
// This configuration class sets up a WebClient bean for making requests to the ML API.