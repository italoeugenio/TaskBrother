package com.italo.TaskBrother.models.dtos;

public record AuthResponseDTO(
    String message,
    String token,
    String email,
    Boolean success
) {
}