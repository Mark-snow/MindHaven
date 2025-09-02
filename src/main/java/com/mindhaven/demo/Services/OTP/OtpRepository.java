package com.mindhaven.demo.Services.OTP;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OtpRepository extends MongoRepository<Otp, Long> {
    Otp findByEmailAndOtp(String email, String otp);
}
