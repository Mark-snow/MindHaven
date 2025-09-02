package com.mindhaven.demo.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.mindhaven.demo.Entities.MoodLog;

public interface MoodLogRepository extends MongoRepository<MoodLog, Long>{

    MoodLog findByDate(LocalDate date);
    List<MoodLog> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
    MoodLog findTopByUserIdOrderByDateDesc(Long userId);
    
    @Aggregation(pipeline = {
    "{ '$match': { 'userId': ?0 } }",
    "{ '$group': { '_id': null, 'lastLogDate': { '$max': '$date' } } }"
    })
    MoodLog findLastLogDate(Long userId);

}
