package com.mindhaven.demo.Services.Chatbot.Groq.ChatbotConfig;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ResourceProvider {
    private final Map<String, String> kenyanMentalHealthResources;

    public ResourceProvider() {
        this.kenyanMentalHealthResources = Map.of(
            "Emergency", "999 (National Police Emergency)",
            "Mental Health Helpline", "1190 (Kenya Red Cross Psychological Support Line)",
            "Suicide Prevention", "0800 723 723 (Befrienders Kenya)",
            "Childline Kenya", "116 (Free helpline for children & teens)",
            "Nairobi Women’s Hospital", "0800 720 801 (Gender-Based Violence Support)",
            "Basic Needs Kenya", "+254 20 386 2213 (Mental health NGO)",
            "Amani Counselling Centre", "+254 722 202 137 (Affordable therapy)"
        );
    }

    public String getCrisisResources() {
        return String.join("\n", 
            "If you're in distress, please reach out to these Kenyan support services:",
            "- Emergency: " + kenyanMentalHealthResources.get("Emergency"),
            "- Mental Health Helpline: " + kenyanMentalHealthResources.get("Mental Health Helpline"),
            "- Suicide Prevention: " + kenyanMentalHealthResources.get("Suicide Prevention"),
            "- For children/teens: " + kenyanMentalHealthResources.get("Childline Kenya"),
            "\nYou’re not alone. These services are free and confidential."
        );
    }
}