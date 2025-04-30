package com.mindhaven.demo.Services.Chatbot.Groq.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class GroqResponse {
    private List<GroqChoice> choices;
    private String error;  // Optional field for error handling

    @Data
    public static class GroqChoice {
        private GroqMessage message;
        private String finish_reason;
        private int index;
    }
}