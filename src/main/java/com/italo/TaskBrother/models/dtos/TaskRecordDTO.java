package com.italo.TaskBrother.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskRecordDTO(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull Integer scoreValue,
        LocalDateTime deadline) {
}
