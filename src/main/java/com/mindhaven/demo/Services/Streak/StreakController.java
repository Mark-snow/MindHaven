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
    public ApiResponse<ResponseDto> getStreak(@PathVariable Long userId) {
        ResponseDto streak = streakService.getStreak(userId);
        return new ApiResponse<>(true, "Streak retrieved successfully", streak);
    }

    @GetMapping("/restore/{userId}")
    public ApiResponse<Long> restoreStreak(@PathVariable Long userId) {

        Long result = streakService.canResetStreak(userId);

        // Check if the user has a lost streak
        if (result == 0L) {
            return new ApiResponse<>(false, "No lost streak to restore", null);
        }

        // Check if the user has any reset streaks left
        else if (result == 1L) {
            return new ApiResponse<>(false, "No reset streaks left", null);
        }

        else {
            Long streak = streakService.restoreStreak(userId);
            return new ApiResponse<>(true, "Streak restored successfully", streak);
        }

    }

}
