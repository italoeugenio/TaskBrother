package com.italo.TaskBrother.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationCode(String email, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("TaskBrother - Email Verification Code");
            message.setText("Your verification code is: " + code + "\n\nThis code will expire in 15 minutes.");
            message.setFrom("noreply@taskbrother.com");
            
            mailSender.send(message);
            logger.info("Verification code sent to email: {}", email);
        } catch (Exception e) {
            logger.error("Failed to send verification code to email: {}", email, e);
            // In development, we might want to just log the code instead of failing
            logger.info("Development mode - Verification code for {}: {}", email, code);
        }
    }
}