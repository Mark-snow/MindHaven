package com.mindhaven.demo.Repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mindhaven.demo.Entities.MoodLog;

public interface MoodLogRepository extends JpaRepository<MoodLog, Long>{

    MoodLog findByDate(LocalDate date);
    MoodLog findByUserId(Long userId);
    
    @Query(value = "select u.date from mood_log u where u.user_Id = ?1", nativeQuery = true)
    public LocalDate getLogDate(Long userId);
}
