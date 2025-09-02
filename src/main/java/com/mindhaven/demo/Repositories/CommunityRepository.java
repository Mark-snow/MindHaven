package com.mindhaven.demo.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mindhaven.demo.Entities.Community;

public interface CommunityRepository extends MongoRepository<Community, Long> {
    // Custom query methods can be defined here if needed

}
