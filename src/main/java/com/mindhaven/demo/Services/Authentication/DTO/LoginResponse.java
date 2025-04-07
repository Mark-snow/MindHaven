package com.mindhaven.demo.Services.Authentication.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private boolean success;
    private String message;
    private String token;
    private Long userId;
    private String streak;


    // Constructor
    public LoginResponse(boolean success, String message, String token, Long userId, String streak) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.userId = userId;
        this.streak = streak;
    }

}
