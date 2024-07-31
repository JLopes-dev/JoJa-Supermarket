package com.joja.api.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOProduct(
        Long id,
        @NotBlank
        String name,
        @NotNull
        Double price,
        @NotNull
        Integer quantity
) {
}
