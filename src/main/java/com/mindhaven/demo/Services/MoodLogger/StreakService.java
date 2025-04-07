package com.mindhaven.demo.Services.MoodLogger;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mindhaven.demo.Entities.User;
import com.mindhaven.demo.Repositories.MoodLogRepository;
import com.mindhaven.demo.Repositories.UserRepository;

@Service
public class StreakService {

    @Autowired
    private MoodLogRepository moodLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * *")
    private void updateUserStreak() {

        List<User> users = userRepository.findAll();

        LocalDate now = LocalDate.now();

        for (User user : users) {
            LocalDate lastLogDate = moodLogRepository.findLastLogDate(user.getUserId());

            if (lastLogDate != null && lastLogDate.isEqual(now.minusDays(1))) {
                user.setStreak(user.getStreak() + 1);
            } else {
                user.setStreak(0L);
            }

            userRepository.save(user);
        }
    }
}
