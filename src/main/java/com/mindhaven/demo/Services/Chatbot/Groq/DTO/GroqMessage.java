package com.mindhaven.demo.Services.Chatbot.Groq.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GroqMessage {
    private String role;  // "system", "user", or "assistant"
    private String content;

    public GroqMessage() {}

    public GroqMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
}