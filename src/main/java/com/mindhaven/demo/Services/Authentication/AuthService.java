package com.mindhaven.demo.Services.Authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mindhaven.demo.Configurations.EmailConfig.EmailService;
import com.mindhaven.demo.Configurations.SecurityConfig.JwtUtil;
import com.mindhaven.demo.Entities.User;
import com.mindhaven.demo.Repositories.UserRepository;
import com.mindhaven.demo.Services.Streak.StreakService;


@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StreakService streakService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    public User registerUser(User user) {
        User existing = userRepository.findByEmail(user.getEmail());
        if (existing != null) {
            logger.info("User already exists with email: {}", user.getEmail());
            return null;
        }
        user.setStreak(0L);
        user.setResetStreakCount(3L);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        emailService.sendRegistrationEmail(user.getEmail(), user.getFullName());
        logger.info("Sending registration email to: {}", user.getEmail());
        logger.info("User registered successfully: {}", user.getFullName());
        return userRepository.save(user);
    }

    public String authenticateUser(String email, String password) {
        logger.info("Attempting to authenticate User with email: {}", email);
        User User = userRepository.findByEmail(email);
        if (User != null) {
            logger.info("User found with email: {}", email);
            if (passwordEncoder.matches(password, User.getPassword())) {
                String token = jwtUtil.generateToken(email);
                logger.info("Generated token for email: {}", email);
                logger.info("User authenticated successfully: {}", User.getFullName());
                
                streakService.resetStreak(User.getUserId());
                
                return token;
            } else {
                logger.warn("Invalid password for email: {}", email);
            }
        } else {
            logger.warn("User not found for email: {}", email);
        }
        return null;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean updatePassword(String email, String newPassword) {

        User existing = userRepository.findByEmail(email);
        existing.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(existing);

        return true; // Return true if password update is successful
    }

}
