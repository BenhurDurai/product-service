package com.poc.ProductMicroservice.repository;

import com.poc.ProductMicroservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByProductName(String productName);
}
