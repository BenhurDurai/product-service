package com.poc.ProductMicroservice.repository;

import com.poc.ProductMicroservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,String> {
    Optional<Category> findByCategoryName(String categoryName);
}
