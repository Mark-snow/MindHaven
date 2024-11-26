package com.mindhaven.demo.Configurations.EmailConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender emailSender;

    private void sendEmail(String recipientEmail, String subject, String htmlContent) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendRegistrationEmail(String recipientEmail, String fullName) {
        String htmlContent = generateRegistrationEmailContent(fullName);
        sendEmail(recipientEmail, "Registration Confirmation", htmlContent);
    }

    public void sendPasswordResetEmail(String recipientEmail, String fullName, String otp) {
        String htmlContent = generatePasswordResetContent(otp, fullName);
        sendEmail(recipientEmail, "Password Reset Request", htmlContent);
    }

    public void sendOtpEmail(String recipientEmail, String otp) {
        String htmlContent = generateOtpEmailContent(otp);
        sendEmail(recipientEmail, "Password Reset OTP", htmlContent);
    }

    private String generateRegistrationEmailContent(String fullName) {
        return "<!DOCTYPE html>" +
               "<html lang='en'>" +
               "<head>" +
               "<meta charset='UTF-8'>" +
               "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "<style>" +
               "body { font-family: 'Arial', sans-serif; margin: 0; padding: 0; background-color: #f0f0f0; }" +
               ".container { width: 100%; max-width: 600px; margin: 0 auto; background-color: #ffffff; border: 1px solid #e0e0e0; border-radius: 8px; overflow: hidden; }" +
               ".header { background-color: #0044cc; color: #ffffff; padding: 20px; text-align: center; }" +
               ".header h1 { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 24px; margin: 0; }" +
               ".content { padding: 20px; color: #333333; font-size: 16px; line-height: 1.5; }" +
               ".content p { margin: 0 0 20px; }" +
               ".footer { background-color: #f4f4f4; text-align: center; padding: 10px; font-size: 12px; color: #888888; }" +
               "@media only screen and (max-width: 600px) { .container { width: 100% !important; } }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class='container'>" +
               "<div class='header'>" +
               "<h1>Registration Confirmation</h1>" +
               "</div>" +
               "<div class='content'>" +
               "<p>Dear Parent,</p>" +
               "<p>Thank you " + fullName + " for registering your child with us. We are excited to welcome them to our institution!</p>" +
               "<p>If you have any questions or need further assistance, feel free to contact us.</p>" +
               "<p>Best regards,</p>" +
               "<p>MindHaven</p>" +
               "</div>" +
               "<div class='footer'>" +
               "<p>&copy; 2024 MindHaven. All rights reserved.</p>" +
               "</div>" +
               "</div>" +
               "</body>" +
               "</html>";
    }

    private String generatePasswordResetContent(String otp, String Name) {
        return "<!DOCTYPE html>" +
               "<html lang='en'>" +
               "<head>" +
               "<meta charset='UTF-8'>" +
               "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "<style>" +
               "body { font-family: 'Arial', sans-serif; margin: 0; padding: 0; background-color: #f0f0f0; }" +
               ".container { width: 100%; max-width: 600px; margin: 0 auto; background-color: #ffffff; border: 1px solid #e0e0e0; border-radius: 8px; overflow: hidden; }" +
               ".header { background-color: #0044cc; color: #ffffff; padding: 20px; text-align: center; }" +
               ".header h1 { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 24px; margin: 0; }" +
               ".content { padding: 20px; color: #333333; font-size: 16px; line-height: 1.5; }" +
               ".content p { margin: 0 0 20px; }" +
               ".footer { background-color: #f4f4f4; text-align: center; padding: 10px; font-size: 12px; color: #888888; }" +
               "@media only screen and (max-width: 600px) { .container { width: 100% !important; } }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class='container'>" +
               "<div class='header'>" +
               "<h1>Password Reset Request</h1>" +
               "</div>" +
               "<div class='content'>" +
               "<p>Welcome " + Name + ",</p>" +
               "<p>We have received a password reset request from your account.</p>" +
               "<p>Here is the One Time Password(OTP) you will use to reset your password:</p>" +
               "<p><strong>OTP: " + otp + "</strong></p>" +
               "<p>If this request was not made by you, please ignore this email." +
               "<p>If you have any concerns or questions, please do not hesitate to contact us.</p>" +
               "<p>Best regards,</p>" +
               "<p>MindHaven</p>" +
               "</div>" +
               "<div class='footer'>" +
               "<p>&copy; 2024 MindHaven. All rights reserved.</p>" +
               "</div>" +
               "</div>" +
               "</body>" +
               "</html>";
    }

    private String generateOtpEmailContent(String otp) {
        return "<!DOCTYPE html>" +
               "<html lang='en'>" +
               "<head>" +
               "<meta charset='UTF-8'>" +
               "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "<style>" +
               "body { font-family: 'Arial', sans-serif; margin: 0; padding: 0; background-color: #f0f0f0; }" +
               ".container { width: 100%; max-width: 600px; margin: 0 auto; background-color: #ffffff; border: 1px solid #e0e0e0; border-radius: 8px; overflow: hidden; }" +
               ".header { background-color: #007bff; color: #ffffff; padding: 20px; text-align: center; }" +
               ".header h1 { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 24px; margin: 0; }" +
               ".content { padding: 20px; color: #333333; font-size: 16px; line-height: 1.5; }" +
               ".content p { margin: 0 0 20px; }" +
               ".footer { background-color: #f4f4f4; text-align: center; padding: 10px; font-size: 12px; color: #888888; }" +
               "@media only screen and (max-width: 600px) { .container { width: 100% !important; } }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class='container'>" +
               "<div class='header'>" +
               "<h1>Password Reset OTP</h1>" +
               "</div>" +
               "<div class='content'>" +
               "<p>Dear Parent,</p>" +
               "<p>You have requested to reset your password. Here is your OTP:</p>" +
               "<p><strong>" + otp + "</strong></p>" +
               "<p>The OTP is valid for 2 minutes. Please use it to reset your password.</p>" +
               "<p>If you did not request this, please ignore this email.</p>" +
               "<p>Best regards,</p>" +
               "<p>MindHaven</p>" +
               "</div>" +
               "<div class='footer'>" +
               "<p>&copy; MindHaven. All rights reserved.</p>" +
               "</div>" +
               "</div>" +
               "</div>" +
               "</body>" +
               "</html>";
    }
}
