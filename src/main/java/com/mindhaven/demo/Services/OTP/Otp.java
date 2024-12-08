package com.mindhaven.demo.Services.OTP;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name="otp")
public class Otp{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

