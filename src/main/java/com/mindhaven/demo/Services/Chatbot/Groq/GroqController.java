package com.mindhaven.demo.Services.Chatbot.Groq;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindhaven.demo.Entities.User;
import com.mindhaven.demo.Repositories.UserRepository;
import com.mindhaven.demo.Services.Chatbot.Dialogflow.DTO.ChatResponse;
import com.mindhaven.demo.Services.Chatbot.Groq.DTO.ChatRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/groq/chat")
@CrossOrigin("*")

public class GroqController {
    private final GroqService groqService;
    private final List<String> crisisKeywords = List.of(
        "suicide", "kill myself", "end it all", "want to die",
        "self-harm", "hurting myself", "can't go on"
    );

    public GroqController(GroqService groqService) {
        this.groqService = groqService;
    }

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{userId}")
    public Mono<ResponseEntity<ChatResponse>> chat(@PathVariable Long userId, @RequestBody ChatRequest request) {
        String userMessage = request.getMessage().trim();

        String mbtiType = userRepository.findById(userId)
            .map(User::getMbtiType)
            .orElse(null);

        
        if (userMessage.isEmpty()) {
            return Mono.just(ResponseEntity.ok(
                new ChatResponse("I'm here to listen. Would you like to share what's on your mind?")
            ));
        }

        if (containsCrisisKeyword(userMessage.toLowerCase())) {
            return Mono.just(ResponseEntity.ok(
                new ChatResponse("I hear that you're in tremendous pain right now. " +
                    "While I'm here to listen, I want to encourage you to reach out to someone who can help immediately. " +
                    "You can contact a crisis hotline at [insert local crisis number] or text HOME to 741741. " +
                    "You don't have to go through this alone.")
            ));
        }

        return groqService.getChatCompletion(userMessage, mbtiType)
                .map(response -> ResponseEntity.ok(new ChatResponse(response)))
                .timeout(Duration.ofSeconds(15))
                .onErrorReturn(ResponseEntity.ok(
                    new ChatResponse("I'm having trouble finding the right words right now. " +
                        "Remember that whatever you're feeling is valid, and reaching out to a trusted person can help.")
                ));
    }

    private boolean containsCrisisKeyword(String message) {
        return crisisKeywords.stream().anyMatch(message::contains);
    }

    @Data
    @AllArgsConstructor
    private static class ChatResponse {
        private String response;
    }
}
