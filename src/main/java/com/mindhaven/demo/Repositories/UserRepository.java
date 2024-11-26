package com.mindhaven.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindhaven.demo.Entities.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{
    User findByEmail(String email);    

}
