package com.mindhaven.demo.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mindhaven.demo.Entities.PersonalityTest;

public interface PersonalityTestRepository extends MongoRepository<PersonalityTest, String> {
    
}
