package com.joja.api.DTOs;

import jakarta.validation.constraints.NotBlank;

public record DTOUser(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
