package com.mindhaven.demo.Entities;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "moodLog")
public class MoodLog {

    @Id
    private Long id;

    private Long userId;
    private LocalDate date;
    private String timeOfDay;
    private String mood;
    private String description;
    private String tag;

}
