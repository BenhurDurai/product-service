package com.poc.ProductMicroservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoryDto {

    @NotBlank
    private String categoryName;

    @NotBlank
    private String categoryDescription;

}
