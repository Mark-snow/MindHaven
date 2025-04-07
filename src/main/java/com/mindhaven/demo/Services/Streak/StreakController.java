package com.mindhaven.demo.Services.Streak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindhaven.demo.Configurations.ResponseConfig.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/streak")
@CrossOrigin("*")

public class StreakController {

    @Autowired
    private StreakService streakService;

    @GetMapping("/{userId}")
    public ApiResponse<Long> getStreak(@PathVariable Long userId) {
        Long streak = streakService.getStreak(userId);
        return new ApiResponse<>(true, "Streak retrieved successfully", streak);
    }

    @GetMapping("/restore/{userId}")
    public ApiResponse<Long> restoreStreak(@PathVariable Long userId) {
        // Check if the user has any reset streaks left
        if (streakService.canResetStreak(userId) == false) {
            return new ApiResponse<>(false, "No reset streaks left", null);
        }
        
        Long streak = streakService.restoreStreak(userId);
        return new ApiResponse<>(true, "Streak restored successfully", streak);
    }

}
