package com.mindhaven.demo.Services.ContactUs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendMessage(MessageDto message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("mindhaven25@gmail.com");
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText("Name: " + message.getName() + "\nPhone: " + message.getPhoneNumber() + "\nEmail: " + message.getEmail() + "\n\nMessage:\n" + message.getMessage());
        emailSender.send(mailMessage);
    }
}
