package com.mindhaven.demo.Configurations.DatabaseConfig;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Document(collection = "counters")
public class DatabaseSequence {

    @Id
    private String id;
    private long seq;
}