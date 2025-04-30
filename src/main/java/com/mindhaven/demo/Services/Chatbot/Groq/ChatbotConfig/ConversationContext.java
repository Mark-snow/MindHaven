package com.mindhaven.demo.Services.Chatbot.Groq.ChatbotConfig;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConversationContext {
    private final List<String> conversationHistory = new ArrayList<>();

    public void addUserMessage(String message) {
        conversationHistory.add("User: " + message);
    }

    public void addBotMessage(String message) {
        conversationHistory.add("MindHaven: " + message);
    }

    public String getConversationContext() {
        return String.join("\n", conversationHistory);
    }
}