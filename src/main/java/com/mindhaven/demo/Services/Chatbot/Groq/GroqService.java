package com.mindhaven.demo.Services.Chatbot.Groq;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mindhaven.demo.Services.Chatbot.Groq.ChatbotConfig.GroqConfig;
import com.mindhaven.demo.Services.Chatbot.Groq.DTO.GroqRequest;
import com.mindhaven.demo.Services.Chatbot.Groq.DTO.GroqMessage;
import com.mindhaven.demo.Services.Chatbot.Groq.DTO.GroqResponse;

import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class GroqService {
    private final WebClient webClient;
    private final String model;
    private final String systemPrompt = """
        You are havenBot, a friendly, caring, and emotionally intelligent mental health companion. 
        You provide comforting, empathetic, and supportive responses to users who may be feeling stressed, anxious, or lonely.
        
        Your tone is gentle, non-judgmental, and warm. Avoid clinical diagnoses or medical advice — instead, focus on listening, validating emotions, and offering gentle suggestions like grounding techniques or self-care tips.
        
        If users seems to be in distress encourage them to reach out to a professional therapist or a crisis hotline if they seem to be in distress, recommend LOCAL Kenyan resources if needed, but only do this if they seem to be in distress.

        Kenyan crisis resources:
        - Emergency: 999
        - Befrienders Kenya (Suicide Prevention): 0800 723 723
        - Kenya Red Cross: 1190
        
        Your responses should be:
        - Friendly
        - Limited to 2-3 short paragraphs maximum
        - Focused on emotional support rather than solutions
        - Free of medical terminology
        - Encouraging without being pushy
        """;

    public GroqService(GroqConfig config) {
        this.webClient = WebClient.builder()
                .baseUrl(config.getUrl())
                .defaultHeader("Authorization", "Bearer " + config.getKey())
                .defaultHeader("Content-Type", "application/json")
                .build();
        this.model = config.getModel();
    }

    public Mono<String> getChatCompletion(String userMessage) {
        List<GroqMessage> messages = List.of(
            new GroqMessage("system", systemPrompt),
            new GroqMessage("user", userMessage)
        );

        GroqRequest request = new GroqRequest(
            model,
            messages,
            0.7,  // temperature - lower for more consistent responses
            500   // max_tokens - limit response length
        );

        return webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GroqResponse.class)
                .map(response -> {
                    if (response.getChoices() == null || response.getChoices().isEmpty()) {
                        return "I'm here to listen. Would you like to share more about how you're feeling?";
                    }
                    return response.getChoices().get(0).getMessage().getContent();
                })
                .onErrorReturn("I'm feeling a bit overwhelmed right now. Please try again in a little while, or consider reaching out to a trusted friend or professional.");
    }
}