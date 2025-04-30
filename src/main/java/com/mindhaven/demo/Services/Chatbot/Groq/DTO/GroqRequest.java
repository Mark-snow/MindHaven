package com.mindhaven.demo.Services.Chatbot.Groq.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class GroqRequest {
    private String model;
    private List<GroqMessage> messages;
    private double temperature;
    private int max_tokens;

    public GroqRequest() {}

    public GroqRequest(String model, List<GroqMessage> messages, double temperature, int max_tokens) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
    }
}