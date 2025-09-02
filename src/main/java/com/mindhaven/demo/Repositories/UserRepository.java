package com.mindhaven.demo.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mindhaven.demo.Entities.User;

public interface UserRepository extends MongoRepository <User, Long>{
    User findByEmail(String email);    

}
