package com.mindhaven.demo.Services.Chatbot.Dialogflow.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatResponse {

    private String reply;
    private String sessionId;

    public ChatResponse(String reply, String sessionId) {
        this.reply = reply;
        this.sessionId = sessionId;
    }
}
