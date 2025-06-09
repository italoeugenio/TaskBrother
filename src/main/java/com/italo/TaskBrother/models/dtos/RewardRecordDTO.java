package com.italo.TaskBrother.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RewardRecordDTO(@NotBlank String title, @NotBlank String description, @NotNull Integer price) {
}
