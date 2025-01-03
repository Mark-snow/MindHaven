package com.mindhaven.demo.Services.MoodLogger;

import java.time.LocalDate;
import java.time.LocalTime;

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

    public MoodLog logMood(MoodLogDto moodLogDto, Long userId) {

        MoodLog newLog = new MoodLog();

        newLog.setUserId(userId);
        newLog.setDate(LocalDate.now());
        newLog.setTimeOfDay(timeOfDay());
        newLog.setMood(moodLogDto.getMood());
        newLog.setDescription(moodLogDto.getDescription());

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));;
        updateUserStreak(user);

        userRepository.save(user);
        moodLogRepository.save(newLog);

        return newLog;
    }

    public MoodLog getLogs(Long userId) {
        return moodLogRepository.findByUserId(userId);
    }

    public void deleteLog(Long logId) {
        moodLogRepository.deleteById(logId);
    }

    private void updateUserStreak(User user) {
        LocalDate lastLogDate = moodLogRepository.getLogDate(user.getId());
        if (lastLogDate == (null) || lastLogDate.plusDays(1).isBefore(LocalDate.now())) {
            user.setStreak(1L);
        } else {
            user.setStreak(user.getStreak() + 1);
        }
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
