package com.mindhaven.demo.Entities;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "forum")
public class Community {

    @Id
    private Long id;

    private Long userId;
    private String name;
    private String thoughts;
    private LocalDate date;

}
