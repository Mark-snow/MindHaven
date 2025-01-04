package com.mindhaven.demo.Services.MoodLogger;

import java.time.LocalDate;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindhaven.demo.Entities.MoodLog;
import com.mindhaven.demo.Entities.User;
import com.mindhaven.demo.Exceptions.UserNotFoundException;
import com.mindhaven.demo.Repositories.MoodLogRepository;
import com.mindhaven.demo.Repositories.UserRepository;

@Service
public class MoodLoggerService {

    @Autowired
    private MoodLogRepository moodLogRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(MoodLoggerService.class);

    public MoodLog logMood(Long userId, String mood, String description) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));;
        log.info("User found: {}", user.getFullName());

        MoodLog newLog = new MoodLog();

        newLog.setUserId(userId);
        newLog.setMood(mood);
        newLog.setDescription(description);
        newLog.setDate(LocalDate.now());
        newLog.setTimeOfDay(timeOfDay());

        moodLogRepository.save(newLog);

        return newLog;
    }

    public MoodLog getLogs(Long userId) {
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
