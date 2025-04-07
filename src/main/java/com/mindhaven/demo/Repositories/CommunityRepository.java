package com.mindhaven.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindhaven.demo.Entities.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    // Custom query methods can be defined here if needed

}
