package com.italo.TaskBrother.services;

import com.italo.TaskBrother.models.dtos.*;
import com.italo.TaskBrother.models.entities.UserModel;
import com.italo.TaskBrother.models.entities.VerificationCodeModel;
import com.italo.TaskBrother.models.repository.UserRepository;
import com.italo.TaskBrother.models.repository.VerificationCodeRepository;
import com.italo.TaskBrother.utils.EmailValidator;
import com.italo.TaskBrother.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private final SecureRandom random = new SecureRandom();

    @Transactional
    public ResponseEntity<AuthResponseDTO> register(RegisterRequestDTO request) {
        // Validate email format
        if (!EmailValidator.checkIfEmailIsValid(request.email())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponseDTO("Invalid email format", null, request.email(), false));
        }

        // Check if user already exists
        Optional<UserModel> existingUser = userRepository.findByEmail(request.email());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthResponseDTO("Email already registered", null, request.email(), false));
        }

        // Create user (but not verified yet)
        UserModel user = new UserModel();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        // Generate and send verification code
        String code = generateVerificationCode();
        VerificationCodeModel verificationCode = new VerificationCodeModel();
        verificationCode.setEmail(request.email());
        verificationCode.setCode(code);
        verificationCodeRepository.save(verificationCode);

        // Send email
        emailService.sendVerificationCode(request.email(), code);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new AuthResponseDTO("Registration successful. Please check your email for verification code.", 
                      null, request.email(), true));
    }

    @Transactional
    public ResponseEntity<AuthResponseDTO> verifyCode(VerifyCodeRequestDTO request) {
        // Clean up expired codes first
        verificationCodeRepository.deleteExpiredCodes(LocalDateTime.now());

        // Find verification code
        Optional<VerificationCodeModel> verificationCodeOpt = 
                verificationCodeRepository.findByEmailAndCode(request.email(), request.code());

        if (verificationCodeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponseDTO("Invalid or expired verification code", null, request.email(), false));
        }

        VerificationCodeModel verificationCode = verificationCodeOpt.get();

        // Check if code is expired
        if (verificationCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponseDTO("Verification code has expired", null, request.email(), false));
        }

        // Check if already verified
        if (verificationCode.getIsVerified()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponseDTO("Verification code already used", null, request.email(), false));
        }

        // Mark as verified
        verificationCode.setIsVerified(true);
        verificationCodeRepository.save(verificationCode);

        // Generate JWT token
        String token = jwtUtil.generateToken(request.email());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new AuthResponseDTO("Email verified successfully", token, request.email(), true));
    }

    public ResponseEntity<AuthResponseDTO> login(LoginRequestDTO request) {
        // Find user
        Optional<UserModel> userOpt = userRepository.findByEmail(request.email());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO("Invalid email or password", null, request.email(), false));
        }

        UserModel user = userOpt.get();

        // Check password
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO("Invalid email or password", null, request.email(), false));
        }

        // Check if email is verified
        Optional<VerificationCodeModel> verificationOpt = verificationCodeRepository.findByEmail(request.email());
        if (verificationOpt.isEmpty() || !verificationOpt.get().getIsVerified()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new AuthResponseDTO("Please verify your email before logging in", null, request.email(), false));
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(request.email());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new AuthResponseDTO("Login successful", token, request.email(), true));
    }

    @Transactional
    public ResponseEntity<AuthResponseDTO> resendVerificationCode(ResendCodeRequestDTO request) {
        // Check if user exists
        Optional<UserModel> userOpt = userRepository.findByEmail(request.email());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AuthResponseDTO("Email not found", null, request.email(), false));
        }

        // Check if already verified
        Optional<VerificationCodeModel> existingVerificationOpt = verificationCodeRepository.findByEmail(request.email());
        if (existingVerificationOpt.isPresent() && existingVerificationOpt.get().getIsVerified()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponseDTO("Email already verified", null, request.email(), false));
        }

        // Delete existing unverified codes
        if (existingVerificationOpt.isPresent()) {
            verificationCodeRepository.delete(existingVerificationOpt.get());
        }

        // Generate new verification code
        String code = generateVerificationCode();
        VerificationCodeModel verificationCode = new VerificationCodeModel();
        verificationCode.setEmail(request.email());
        verificationCode.setCode(code);
        verificationCodeRepository.save(verificationCode);

        // Send email
        emailService.sendVerificationCode(request.email(), code);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new AuthResponseDTO("New verification code sent to your email", null, request.email(), true));
    }

    private String generateVerificationCode() {
        return String.format("%06d", random.nextInt(1000000));
    }
}