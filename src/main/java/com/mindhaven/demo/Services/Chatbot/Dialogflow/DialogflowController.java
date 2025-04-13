package com.mindhaven.demo.Services.Chatbot.Dialogflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mindhaven.demo.Services.Chatbot.Dialogflow.DTO.ChatRequest;
import com.mindhaven.demo.Services.Chatbot.Dialogflow.DTO.ChatResponse;


@RestController
@RequestMapping("/api/chat/havenBot")
@CrossOrigin ("*")
public class DialogflowController {

    @Autowired
    private DialogflowService dialogflowService;

    @PostMapping("/{userId}")
    public ChatResponse chat(@PathVariable Long userId, @RequestBody ChatRequest request) {
        String sessionId = userId.toString();
        String reply = dialogflowService.detectIntentTexts(request.getMessage(), sessionId);
        return new ChatResponse(reply, sessionId);
    }
}

