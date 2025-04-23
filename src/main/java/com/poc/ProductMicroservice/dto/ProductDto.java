package com.poc.ProductMicroservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Value;

@Data
public class ProductDto {

    @NotBlank
    private String productName;

    @NotBlank
    private String productDescription;

    @Min(value=0, message ="Price must be greater than or equal to 0" )
    private double price;

    @Min(value = 1, message = "Quantity must be greater than or equal to 1")
    private int quantity;

    private String categoryName;

}
