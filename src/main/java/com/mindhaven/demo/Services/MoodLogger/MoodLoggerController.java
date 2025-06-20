package com.mindhaven.demo.Services.MoodLogger;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindhaven.demo.Entities.MoodLog;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mood-logger")
@CrossOrigin("*")

public class MoodLoggerController {
    
    private final MoodLoggerService moodLoggerService;

    private static final Logger log = LoggerFactory.getLogger(MoodLoggerController.class);

    public MoodLoggerController(MoodLoggerService moodLoggerService) {
        this.moodLoggerService = moodLoggerService;
    }

    @PostMapping("/new-log/{userId}")
    public MoodLog addMood(@Valid @PathVariable Long userId, @RequestBody MoodLogDto moodDto) {
        log.info("Received MoodLogDto: mood={}, description={}, tag={}", moodDto.getMood(), moodDto.getDescription(), moodDto.getTag());
        return moodLoggerService.logMood(userId, moodDto.getMood(), moodDto.getDescription(), moodDto.getTag());
    }

    @GetMapping("/logs/{userId}")
    public List<MoodLog> getMood(@PathVariable Long userId) {
        return moodLoggerService.getLogs(userId);
    }

    @DeleteMapping("/logs/{logId}")
    public void deleteMood(@PathVariable Long logId) {
        moodLoggerService.deleteLog(logId);
    }

}
