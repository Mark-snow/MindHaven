package com.mindhaven.demo.Services.Chatbot.Dialogflow;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class DialogflowService {

    @Value("${dialogflow.project.id}")
    private String projectId;
    
    public String detectIntentTexts(String text, String sessionId) {
        try (InputStream credentialsStream = new ClassPathResource("dialogflow-service-account.json").getInputStream()) {
            SessionsClient sessionsClient = SessionsClient.create(
                SessionsSettings.newBuilder()
                    .setCredentialsProvider(() -> GoogleCredentials.fromStream(credentialsStream))
                    .build()
            );

            SessionName session = SessionName.of(projectId, sessionId);
            TextInput.Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode("en");
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

            DetectIntentRequest request = DetectIntentRequest.newBuilder()
                    .setSession(session.toString())
                    .setQueryInput(queryInput)
                    .build();

            DetectIntentResponse response = sessionsClient.detectIntent(request);
            QueryResult result = response.getQueryResult();

            return result.getFulfillmentText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error communicating with Dialogflow.";
        }
    }
}

