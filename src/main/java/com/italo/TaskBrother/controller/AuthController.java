package com.italo.TaskBrother.controller;

import com.italo.TaskBrother.models.dtos.*;
import com.italo.TaskBrother.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/verify")
    public ResponseEntity<AuthResponseDTO> verifyCode(@RequestBody @Valid VerifyCodeRequestDTO request) {
        return authService.verifyCode(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        return authService.login(request);
    }

    @PostMapping("/resend-code")
    public ResponseEntity<AuthResponseDTO> resendVerificationCode(@RequestBody @Valid ResendCodeRequestDTO request) {
        return authService.resendVerificationCode(request);
    }
}