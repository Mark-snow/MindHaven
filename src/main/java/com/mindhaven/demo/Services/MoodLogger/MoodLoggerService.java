package com.mindhaven.demo.Services.MoodLogger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindhaven.demo.Entities.MoodLog;
import com.mindhaven.demo.Entities.User;
import com.mindhaven.demo.Exceptions.UserNotFoundException;
import com.mindhaven.demo.Repositories.MoodLogRepository;
import com.mindhaven.demo.Repositories.UserRepository;
import com.mindhaven.demo.Services.Streak.StreakService;

@Service
public class MoodLoggerService {

    @Autowired
    private MoodLogRepository moodLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StreakService streakService;

    private static final Logger log = LoggerFactory.getLogger(MoodLoggerService.class);

    public MoodLog logMood(Long userId, String mood, String description, String tag) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));;
        log.info("User found: {}", user.getFullName());

        streakService.updateStreak(userId);
        log.info("Streak updated for user: {}", user.getFullName());

        MoodLog newLog = new MoodLog();

        newLog.setUserId(userId);
        newLog.setMood(mood);
        newLog.setDescription(description);
        newLog.setTag(tag);
        newLog.setDate(LocalDate.now());
        newLog.setTimeOfDay(timeOfDay());

        moodLogRepository.save(newLog);
        
        return newLog;
    }

    public List<MoodLog> getLogs(Long userId) {
        return moodLogRepository.findByUserId(userId);
    }

    public void deleteLog(Long logId) {
        moodLogRepository.deleteById(logId);
    }

    public String timeOfDay() {
        LocalTime now = LocalTime.now();

        if (now.isBefore(LocalTime.NOON) && now.isAfter(LocalTime.MIDNIGHT)) {
            return "Morning";
        }

        else if (now.isBefore(LocalTime.of(18,00)) && now.isAfter(LocalTime.NOON)) {
            return "Afternoon";
        }

        else {
            return "Evening";
        }
    }
}
