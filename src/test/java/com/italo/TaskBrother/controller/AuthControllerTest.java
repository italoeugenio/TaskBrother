package com.italo.TaskBrother.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.italo.TaskBrother.models.dtos.*;
import com.italo.TaskBrother.models.entities.UserModel;
import com.italo.TaskBrother.models.entities.VerificationCodeModel;
import com.italo.TaskBrother.models.repository.UserRepository;
import com.italo.TaskBrother.models.repository.VerificationCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userRepository.deleteAll();
        verificationCodeRepository.deleteAll();
    }

    @Test
    void testRegisterSuccess() throws Exception {
        RegisterRequestDTO request = new RegisterRequestDTO("test@example.com", "password123");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Registration successful. Please check your email for verification code."))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        // Verify user was created
        assert userRepository.findByEmail("test@example.com").isPresent();
        
        // Verify verification code was created
        assert verificationCodeRepository.findByEmail("test@example.com").isPresent();
    }

    @Test
    void testRegisterDuplicateEmail() throws Exception {
        // Create existing user
        UserModel existingUser = new UserModel();
        existingUser.setEmail("test@example.com");
        existingUser.setPassword(passwordEncoder.encode("password123"));
        userRepository.save(existingUser);

        RegisterRequestDTO request = new RegisterRequestDTO("test@example.com", "password123");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Email already registered"));
    }

    @Test
    void testVerifyCodeSuccess() throws Exception {
        // Create user and verification code
        UserModel user = new UserModel();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password123"));
        userRepository.save(user);

        VerificationCodeModel verificationCode = new VerificationCodeModel();
        verificationCode.setEmail("test@example.com");
        verificationCode.setCode("123456");
        verificationCode.setCreatedAt(LocalDateTime.now());
        verificationCode.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        verificationCode.setIsVerified(false);
        verificationCodeRepository.save(verificationCode);

        VerifyCodeRequestDTO request = new VerifyCodeRequestDTO("test@example.com", "123456");

        mockMvc.perform(post("/auth/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Email verified successfully"))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void testVerifyCodeInvalid() throws Exception {
        VerifyCodeRequestDTO request = new VerifyCodeRequestDTO("test@example.com", "000000");

        mockMvc.perform(post("/auth/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid or expired verification code"));
    }

    @Test
    void testLoginSuccess() throws Exception {
        // Create verified user
        UserModel user = new UserModel();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password123"));
        userRepository.save(user);

        VerificationCodeModel verificationCode = new VerificationCodeModel();
        verificationCode.setEmail("test@example.com");
        verificationCode.setCode("123456");
        verificationCode.setIsVerified(true);
        verificationCodeRepository.save(verificationCode);

        LoginRequestDTO request = new LoginRequestDTO("test@example.com", "password123");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void testLoginInvalidCredentials() throws Exception {
        // Create user but with different password
        UserModel user = new UserModel();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password123"));
        userRepository.save(user);

        LoginRequestDTO request = new LoginRequestDTO("test@example.com", "wrongpassword");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid email or password"));
    }

    @Test
    void testLoginUnverifiedEmail() throws Exception {
        // Create user but not verified
        UserModel user = new UserModel();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password123"));
        userRepository.save(user);

        LoginRequestDTO request = new LoginRequestDTO("test@example.com", "password123");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Please verify your email before logging in"));
    }

    @Test
    void testResendCodeSuccess() throws Exception {
        // Create user
        UserModel user = new UserModel();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password123"));
        userRepository.save(user);

        ResendCodeRequestDTO request = new ResendCodeRequestDTO("test@example.com");

        mockMvc.perform(post("/auth/resend-code")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("New verification code sent to your email"));

        // Verify new verification code was created
        assert verificationCodeRepository.findByEmail("test@example.com").isPresent();
    }

    @Test
    void testResendCodeUserNotFound() throws Exception {
        ResendCodeRequestDTO request = new ResendCodeRequestDTO("notfound@example.com");

        mockMvc.perform(post("/auth/resend-code")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Email not found"));
    }
}