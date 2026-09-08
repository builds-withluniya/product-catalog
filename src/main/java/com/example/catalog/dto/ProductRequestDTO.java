package com.example.catalog.dto;

import com.example.catalog.entity.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductRequestDTO (

    @NotBlank(message = "Name is required")
    String name,
    @DecimalMin(value = "0.01", message = "price should be greater then zero(0)")
    BigDecimal price,

    ProductStatus status)
{

}

