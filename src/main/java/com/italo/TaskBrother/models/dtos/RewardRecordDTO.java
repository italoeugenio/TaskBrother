package com.italo.TaskBrother.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RewardRecordDTO(
    @NotBlank String name, 
    String description, 
    @NotNull Integer cost, 
    UUID familyFK
) {
}