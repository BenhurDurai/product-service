package com.poc.ProductMicroservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {

    @NotBlank
    private String categoryName;

    @NotBlank
    private String categoryDescription;

}
