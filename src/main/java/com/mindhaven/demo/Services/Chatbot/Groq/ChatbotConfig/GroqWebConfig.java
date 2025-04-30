package com.mindhaven.demo.Services.Chatbot.Groq.ChatbotConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GroqWebConfig implements WebMvcConfigurer {
    
    private final SafetyInterceptor safetyInterceptor;

    public GroqWebConfig(SafetyInterceptor safetyInterceptor) {
        this.safetyInterceptor = safetyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(safetyInterceptor).addPathPatterns("/api/groq/**");
    }
}
