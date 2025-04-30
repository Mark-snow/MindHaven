package com.mindhaven.demo.Services.Chatbot.Groq.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // This is crucial for JSON deserialization
@AllArgsConstructor
public class ChatRequest {
    private String message;
}