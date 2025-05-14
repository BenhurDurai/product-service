package com.poc.ProductMicroservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.aspectj.bridge.IMessage;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Product name should not be blank")
    private String productName;

    private String productDescription;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private double price;

    @Min(value = 1, message = "Quantity must be greater than or equal to 1")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
