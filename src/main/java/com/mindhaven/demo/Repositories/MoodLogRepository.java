package com.mindhaven.demo.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mindhaven.demo.Entities.MoodLog;

public interface MoodLogRepository extends JpaRepository<MoodLog, Long>{

    MoodLog findByDate(LocalDate date);
    List<MoodLog> findByUserId(Long userId);
    
    @Query("SELECT MAX(m.date) FROM MoodLog m WHERE m.userId = :userId")
    LocalDate findLastLogDate(@Param("userId") Long userId);
}
