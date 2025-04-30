package com.mindhaven.demo.Services.ContactUs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {

    private String name;
    private String phoneNumber;
    private String email;
    private String subject;
    private String message;

}
