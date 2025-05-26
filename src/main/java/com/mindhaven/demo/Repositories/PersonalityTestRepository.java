package com.mindhaven.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindhaven.demo.Entities.PersonalityTest;

public interface PersonalityTestRepository extends JpaRepository<PersonalityTest, String> {
    
}
