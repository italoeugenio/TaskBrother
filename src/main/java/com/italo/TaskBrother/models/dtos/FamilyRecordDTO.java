package com.italo.TaskBrother.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record FamilyRecordDTO(@NotBlank String familyName) {
}
