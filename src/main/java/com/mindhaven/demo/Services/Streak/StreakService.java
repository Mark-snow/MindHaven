package com.mindhaven.demo.Services.Streak;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindhaven.demo.Entities.MoodLog;
import com.mindhaven.demo.Entities.User;
import com.mindhaven.demo.Repositories.MoodLogRepository;
import com.mindhaven.demo.Repositories.UserRepository;

@Service
public class StreakService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MoodLogRepository moodLogRepository;

    public ResponseDto getStreak(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        MoodLog lastLog = moodLogRepository.findTopByUserIdOrderByDateDesc(userId);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setHasJournaledBefore(moodLogRepository.existsByUserId(userId));
        responseDto.setStreak(user.getStreak());
        responseDto.setResetStreakCount(user.getResetStreakCount());
        responseDto.setHoursSinceLastJournal(ChronoUnit.HOURS.between(lastLog.getDate().atStartOfDay(), LocalDateTime.now()));
        return responseDto;
    }

    public void updateStreak(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate now = LocalDate.now();
        LocalDate lastLogDate = (moodLogRepository.findLastLogDate(user.getUserId()).getDate());

        if (lastLogDate != null && lastLogDate.isEqual(now.minusDays(1))) {
            user.setStreak(user.getStreak() + 1);
        } 
        else if (lastLogDate != null && lastLogDate.isEqual(now)) {
            user.setStreak(user.getStreak());
        }

        userRepository.save(user);
    }

    public void resetStreak(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate now = LocalDate.now();
        LocalDate lastLogDate = moodLogRepository.findLastLogDate(user.getUserId()).getDate();

        if (lastLogDate != null && lastLogDate.isBefore(now.minusDays(1))) {
            user.setLostStreak(user.getStreak());
            user.setStreak(0L);
        }
        
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

    public Long canResetStreak(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getLostStreak() < 1) {
            return 0L;
        } else if (user.getResetStreakCount() < 1) {
            return 1L;
        } else {
            return 2L;
        }

    }

    // Uncomment to enable automatic streak updates

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
