package com.mindhaven.demo.Services.OTP;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

@Getter
@Setter
@Document(collection = "otp")
public class Otp{

    @Id
    private Long id;
    private String email;
    private String otp;
    private Date createdAt;

    // Default constructor

    public Otp() {}

    public Otp(String email, String otp) {
        this.email = email;
        this.otp = otp;
        this.createdAt = new Date();
    }

}

