package com.poc.ProductMicroservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String categoryName;

    private String categoryDescription;

}
