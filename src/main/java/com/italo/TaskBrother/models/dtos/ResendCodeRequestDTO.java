package com.italo.TaskBrother.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResendCodeRequestDTO(
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email
) {
}