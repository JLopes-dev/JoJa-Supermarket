package com.joja.api.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOProduct(
        @NotBlank
        String name,
        @NotNull
        Double price,
        @NotNull
        Integer quantity
) {
}
