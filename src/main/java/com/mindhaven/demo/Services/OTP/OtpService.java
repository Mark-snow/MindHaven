package com.mindhaven.demo.Services.OTP;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    private final JavaMailSender mailSender;

    public OtpService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Generate a random OTP
    public String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000); 
    }

    // Send OTP to admin email
    public void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Admin password reset request.");
        message.setText("Your One-Time Password (OTP) is " + otp + "\n" +
                        "If this request was not initiated by you, please update your password immediately to protect your account");
        mailSender.send(message);
    }
}
