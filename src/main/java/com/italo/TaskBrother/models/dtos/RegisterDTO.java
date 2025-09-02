package com.italo.TaskBrother.models.dtos;

import com.italo.TaskBrother.models.enums.UserRole;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(@NotBlank String email, @NotBlank String password, UserRole role) {
}
