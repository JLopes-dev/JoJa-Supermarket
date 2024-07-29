package com.joja.api.DTOs;

public record DTOProductUpdated(
        String name,
        Double price,
        Integer quantity
) {
}
