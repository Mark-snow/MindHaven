package com.mindhaven.demo.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Document(collection = "users")
public class User {

    @Id
    private Long userId;

    private String fullName;
    private String email;
    private String password;
    private Long streak;
    private Long lostStreak;
    private Long resetStreakCount;
    private String mbtiType; // Optional, can be null if not set
    
}
