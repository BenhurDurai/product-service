package com.poc.ProductMicroservice.controller;

import com.poc.ProductMicroservice.dto.CategoryDto;
import com.poc.ProductMicroservice.model.Category;
import com.poc.ProductMicroservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

     @Autowired
     private CategoryService categoryService;

     @PostMapping("/create")
     public Category createCategory(@Valid @RequestBody CategoryDto categoryDto){
          log.info("Received request to create category: {}", categoryDto.getCategoryName());
          return categoryService.createCategory(categoryDto);
     }

     @GetMapping
     public List<Category> getAllCategories(){
          log.info("Received request to fetch all categories");
          return categoryService.getAllCategories();
     }

     @GetMapping("/{categoryName}")
     public Category getCategoryByCategoryName(@PathVariable String categoryName){
          log.info("Received request to fetch category: {}", categoryName);
          return categoryService.getCategoryByCategoryName(categoryName);
     }

     @DeleteMapping("/{categoryName}")
     public String deleteCategory(@PathVariable String categoryName){
          log.info("Received request to delete category: {}", categoryName);
          categoryService.deleteCategoryByCategoryName(categoryName);
          return "Category deleted successfully " + categoryName;
     }


}
