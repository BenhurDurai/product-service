package com.poc.ProductMicroservice.service;

import com.poc.ProductMicroservice.dto.CategoryDto;
import com.poc.ProductMicroservice.exception.CategoryAlreadyExistsException;
import com.poc.ProductMicroservice.exception.CategoryNotFoundException;
import com.poc.ProductMicroservice.model.Category;
import com.poc.ProductMicroservice.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto categoryDto){
        if (categoryRepository.findByCategoryName(categoryDto.getCategoryName()).isPresent()){
            throw new CategoryAlreadyExistsException("Category already present");
        }

        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        log.info("Category created successfully: {}", category.getCategoryName());
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories(){
        log.info("Fetching all categories");
        return categoryRepository.findAll();
    }

    public Category getCategoryByCategoryName(String categoryName){
        log.info("Fetching category by name: {}",categoryName);
        return categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found: " + categoryName));
    }

    public void deleteCategoryByCategoryName(String categoryName){
        Category category = categoryRepository.findByCategoryName(categoryName)
                        .orElseThrow(()-> new CategoryNotFoundException("Category not found: " + categoryName));
        categoryRepository.delete(category);
        log.info("Category deleted successfully: {}", category.getCategoryName());
    }

}
