package com.italo.TaskBrother.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record TaskRecordDTO(@NotBlank String name, @NotBlank String description, @NotBlank String rejectionReason) {
}
