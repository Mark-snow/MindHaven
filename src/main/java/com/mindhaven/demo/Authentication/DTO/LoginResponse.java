package com.mindhaven.demo.Authentication.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private boolean success;
    private String message;
    private String token;
    private Long id;

    // Constructor
    public LoginResponse(boolean success, String message, String token, Long id) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.id = id;
    }

}
