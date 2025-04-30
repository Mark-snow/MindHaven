package com.mindhaven.demo.Services.Chatbot.Groq.ChatbotConfig;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.google.common.util.concurrent.RateLimiter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SafetyInterceptor implements HandlerInterceptor {
    
    private final RateLimiter rateLimiter;

    public SafetyInterceptor() {
        this.rateLimiter = RateLimiter.create(5); // 5 requests per second
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!rateLimiter.tryAcquire()) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Please take a moment to breathe. You can try again in a little while.");
            return false;
        }
        return true;
    }
}