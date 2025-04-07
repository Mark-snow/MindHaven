package com.mindhaven.demo.Services.Authentication;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindhaven.demo.Configurations.EmailConfig.EmailService;
import com.mindhaven.demo.Configurations.ResponseConfig.ApiResponse;
import com.mindhaven.demo.Entities.User;
import com.mindhaven.demo.Repositories.UserRepository;
import com.mindhaven.demo.Services.Authentication.DTO.LoginRequest;
import com.mindhaven.demo.Services.Authentication.DTO.NewPassword;
import com.mindhaven.demo.Services.Authentication.DTO.ResetPasswordRequest;
import com.mindhaven.demo.Services.OTP.Otp;
import com.mindhaven.demo.Services.OTP.OtpRepository;
import com.mindhaven.demo.Services.OTP.OtpService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/open/auth")
@CrossOrigin("*")

public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private OtpRepository otpRepository;
    
    @Autowired
    private EmailService emailService;

    //user sign up API
    @PostMapping("/register")
    public ApiResponse<User>  registerAdmin(@RequestBody User user) {
        logger.info("User Registration request received for email: {}", user.getEmail());
        User registeredUser = authService.registerUser(user);

        if (registeredUser != null) {
            return new ApiResponse<>(true, "User registered successfully", registeredUser);
        } else {
            return new ApiResponse<>(false, "An user already exists with the email: " + user.getEmail(), null);
        }
    }

    //user login API
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest) {
        logger.info("Admin Login request received for email: {}", loginRequest.getEmail());
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null){
            Map<String,Object> res = new HashMap<>();
            res.put("message", "No user exists with email: " + loginRequest.getEmail());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }

        String token = authService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        Long userId = user.getUserId();
        Long streak = user.getStreak();
         
        java.util.Map<String,Object> res = new HashMap<>();
        if(token == null){
            res.put("status", 400);
            res.put("message", "Invalid details");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
        else{
            res.put("status", 200);
            res.put("token", token);
            res.put("user id", userId);
            res.put("user email", loginRequest.getEmail());
            res.put("user streak", streak);
 
            return ResponseEntity.status(HttpStatus.OK.value()).body(res);
        }
        
        //return new ResponseEntity<Object>(res, HttpStatusCode.valueOf(200));
    }

    //user reset password
    @PostMapping("/reset-password/request")
    public ResponseEntity<Object> requestResetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String email = resetPasswordRequest.getEmail();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("message", "No user exists with email " + email);
            return ResponseEntity.ok(response);
        }

        // Generate OTP
        String otp = otpService.generateOtp();

        // Save OTP to database (with an expiration time if needed)
        otpRepository.save(new Otp(email, otp));

        // Send OTP to admin's email
        emailService.sendPasswordResetEmail(email, user.getFullName(), otp);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "OTP sent to email " + email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<Object> resetPassword(@RequestBody NewPassword newPassword) {
        String email = newPassword.getEmail();
        String otp = newPassword.getOtp();
        String password = newPassword.getNewPassword();

        // Check if OTP is valid and not expired
        Otp otpEntity = otpRepository.findByEmailAndOtp(email, otp);
        if (otpEntity != null) {
            otpRepository.delete(otpEntity); // Optionally remove OTP after validation
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("message", "OTP verified successfully.");

        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Invalid or expired OTP.");
            return ResponseEntity.status(400).body(response);
        }

        // Update user's password
        boolean isPasswordUpdated = authService.updatePassword(email, password);

        if (isPasswordUpdated) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Password reset successfully.");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Failed to reset password.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
