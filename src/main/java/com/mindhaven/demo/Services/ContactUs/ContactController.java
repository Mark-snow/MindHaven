package com.mindhaven.demo.Services.ContactUs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindhaven.demo.Configurations.EmailConfig.EmailService;
import com.mindhaven.demo.Configurations.ResponseConfig.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/open/contact-us")
@CrossOrigin("*")

public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ApiResponse<String> sendMessage(@RequestBody MessageDto message) {
        messageService.sendMessage(message);
        logger.info("Message sent to admin");
        logger.info("Sending reply email to: {}", message.getEmail());
        emailService.sendReplyEmail(message.getEmail(), message.getName());
        logger.info("Message sent successfully to: {}", message.getEmail());

        return new ApiResponse<>(true, "Message sent successfully", null);
    }

}
