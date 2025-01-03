package com.mindhaven.demo.Services.MoodLogger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindhaven.demo.Entities.MoodLog;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/mood-logger")
@CrossOrigin("*")

public class MoodLoggerController {
    
    private final MoodLoggerService moodLoggerService;

    public MoodLoggerController(MoodLoggerService moodLoggerService) {
        this.moodLoggerService = moodLoggerService;
    }

    @PostMapping("/new-log/{userId}")
    public void addMood(@RequestBody MoodLogDto moodDto, @PathVariable Long userId) {
        moodLoggerService.logMood(moodDto, userId);
    }

    @GetMapping("/logs/{userId}")
    public MoodLog getMood(@PathVariable Long userId) {
        return moodLoggerService.getLogs(userId);
    }

    @DeleteMapping("/logs/{logId}")
    public void deleteMood(@PathVariable Long logId) {
        moodLoggerService.deleteLog(logId);
    }

}
