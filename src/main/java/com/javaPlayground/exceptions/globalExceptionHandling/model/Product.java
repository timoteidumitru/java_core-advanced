package com.javaPlayground.exceptions.globalExceptionHandling.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @NotNull(message = "Product ID is required")
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @Min(value = 1, message = "Price must be at least 1.0")
    private double price;

}
