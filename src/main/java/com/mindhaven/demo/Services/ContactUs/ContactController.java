package com.mindhaven.demo.Services.ContactUs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindhaven.demo.Configurations.EmailConfig.EmailService;
import com.mindhaven.demo.Configurations.ResponseConfig.ApiResponse;

@RestController
@RequestMapping("/api/open/contact-us")
@CrossOrigin("*")

public class ContactController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ApiResponse<String> sendMessage(@RequestBody MessageDto message) {
        messageService.sendMessage(message);
        emailService.sendReplyEmail(message.getEmail(), message.getName());
        return new ApiResponse<>(true, "Message sent successfully", null);
    }

}
