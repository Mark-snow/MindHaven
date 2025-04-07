package com.mindhaven.demo.Services.Streak;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindhaven.demo.Entities.User;
import com.mindhaven.demo.Repositories.MoodLogRepository;
import com.mindhaven.demo.Repositories.UserRepository;

@Service
public class StreakService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MoodLogRepository moodLogRepository;

    public Long getStreak(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getStreak();
    }

    public void updateStreak(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate now = LocalDate.now();
        LocalDate lastLogDate = moodLogRepository.findLastLogDate(user.getUserId());

        if (lastLogDate != null && lastLogDate.isEqual(now.minusDays(1))) {
            user.setStreak(user.getStreak() + 1);
        } 
        else if (lastLogDate != null && lastLogDate.isEqual(now)) {
            user.setStreak(user.getStreak());
        }
        else if (lastLogDate != null && lastLogDate.isBefore(now.minusDays(1))) {
            user.setLostStreak(user.getStreak());
            user.setStreak(0L);
        }

        userRepository.save(user);
    }

    public Long restoreStreak(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getResetStreakCount() <= 0) {
            throw new RuntimeException("No reset streaks left");
        }

        user.setStreak(user.getLostStreak());
        user.setLostStreak(0L);
        user.setResetStreakCount(user.getResetStreakCount() - 1);
        if (user.getResetStreakCount() < 0) {
            user.setResetStreakCount(0L);
        }
        userRepository.save(user);
        return user.getStreak();
    }

    public boolean canResetStreak(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return (user.getResetStreakCount() > 0 && user.getLostStreak() > 0);
    }

    // Uncomment the following method to enable automatic streak updates

    // @Scheduled(cron = "0 0 0 * * *")
    // private void updateUserStreak() {

    //     List<User> users = userRepository.findAll();

    //     LocalDate now = LocalDate.now();

    //     for (User user : users) {
    //         LocalDate lastLogDate = moodLogRepository.findLastLogDate(user.getUserId());

    //         if (lastLogDate != null && lastLogDate.isEqual(now.minusDays(1))) {
    //             user.setStreak(user.getStreak() + 1);
    //         } else {
    //             user.setStreak(0L);
    //         }

    //         userRepository.save(user);
    //     }
    // }
}
