package com.poc.ProductMicroservice.service;

import com.poc.ProductMicroservice.dto.CategoryDto;
import com.poc.ProductMicroservice.exception.CategoryAlreadyExistsException;
import com.poc.ProductMicroservice.exception.CategoryNotFoundException;
import com.poc.ProductMicroservice.model.Category;
import com.poc.ProductMicroservice.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private CategoryDto categoryDto;
    private Category category;

    @BeforeEach
    void setup(){
        categoryDto = new CategoryDto("Electronics", "Electric Items");
        category = new Category(1L, "Electronics", "Electric Items");
    }

    @Test
    void createCategory_ShouldCreateNewCategory(){
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category createdCategory = categoryService.createCategory(categoryDto);

        assertEquals("Electronics", createdCategory.getCategoryName());
        verify(categoryRepository,times(1)).save(any(Category.class));
    }

    @Test
    void createCategory_ShouldThrow_CategoryAlreadyExists(){
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.of(category));

        assertThrows(CategoryAlreadyExistsException.class, ()-> categoryService.createCategory(categoryDto));
    }

    @Test
    void getAllCategories_ShouldReturnListOfCategories(){
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<Category> categories = categoryService.getAllCategories();

        assertEquals(1,categories.size());
    }

    @Test
    void getCategoryByCategoryName_ShouldReturnCategory(){
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryByCategoryName("Electronics");

        assertNotNull(result);
        assertEquals("Electronics", result.getCategoryName());
    }

    @Test
    void getCategoryByCategoryName_ShouldThrow_WhenCategoryNotFound(){
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, ()-> categoryService.getCategoryByCategoryName("Electronics"));
    }

    @Test
    void deleteCategoryByCategoryName_ShouldDeleteCategory(){
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.of(category));

        categoryService.deleteCategoryByCategoryName("Electronics");

        verify(categoryRepository).delete(category);
    }

    @Test
    void deleteCategoryByCategoryName_ShouldThrow_WhenCategoryNotFound(){
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, ()-> categoryService.deleteCategoryByCategoryName("Electronics"));
    }

}
