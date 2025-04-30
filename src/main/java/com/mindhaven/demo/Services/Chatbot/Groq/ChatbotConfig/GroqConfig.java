package com.mindhaven.demo.Services.Chatbot.Groq.ChatbotConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "groq.api")

@Getter
@Setter
public class GroqConfig {
    private String key;
    private String url;
    private String model;

}
