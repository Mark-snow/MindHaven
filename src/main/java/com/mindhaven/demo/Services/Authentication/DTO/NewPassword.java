package com.mindhaven.demo.Services.Authentication.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPassword {
    private String email;
    private String otp;
    private String newPassword;
}

