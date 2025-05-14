package com.poc.ProductMicroservice.service;

import com.poc.ProductMicroservice.dto.ProductDto;
import com.poc.ProductMicroservice.exception.CategoryNotFoundException;
import com.poc.ProductMicroservice.exception.ProductAlreadyExistsException;
import com.poc.ProductMicroservice.exception.ProductNotFoundException;
import com.poc.ProductMicroservice.model.Category;
import com.poc.ProductMicroservice.model.Product;
import com.poc.ProductMicroservice.repository.CategoryRepository;
import com.poc.ProductMicroservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ProductDto productDto;
    private Category category;
    private Product product;

    @BeforeEach
    void setup(){
        category = new Category(1L, "Electronics", "Electronics desc");

        productDto = new ProductDto("Laptop", "Gaming Laptop", 5000, 10, category.getCategoryName());

        product = new Product(1L, "Laptop","Gaming Laptop", 5000, 10,category);
    }

    @Test
    void createProduct_ShouldCreateProductAndReturnProduct(){
        when(productRepository.findByProductName("Laptop")).thenReturn(Optional.empty());
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product productCreated = productService.createProduct(productDto);

        assertEquals("Laptop", productCreated.getProductName());
        verify(productRepository,times(1)).save(any(Product.class));
    }

    @Test
    void createProduct_ShouldThrow_WhenProductAlreadyExists(){
        when(productRepository.findByProductName("Laptop")).thenReturn(Optional.of(product));

        assertThrows(ProductAlreadyExistsException.class, ()-> productService.createProduct(productDto));
    }

    @Test
    void createProduct_ShouldThrow_WhenCategoryNotFound(){
        when(productRepository.findByProductName("Laptop")).thenReturn(Optional.empty());
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, ()-> productService.createProduct(productDto));
    }

    @Test
    void getAllProducts_ShouldReturnProductList(){
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> products = productService.getAllProducts();

        assertEquals(1,products.size());
    }

    @Test
    void getProductByProductName_ShouldReturnProduct(){
        when(productRepository.findByProductName("Laptop")).thenReturn(Optional.of(product));

        Product productFound = productService.getProductByProductName("Laptop");

        assertEquals("Laptop", productFound.getProductName());
    }

    @Test
    void getProductByProductName_ShouldThrow_WhenProductNotFound(){
        when(productRepository.findByProductName("Laptop")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, ()-> productService.getProductByProductName("Laptop"));
    }

    @Test
    void updateProductByProductName_ShouldUpdateProductAndReturnUpdatedProduct(){
        ProductDto updatedDto = new ProductDto("Laptop","Updated Desc", 6000, 5,"Electronics");

        when(productRepository.findByProductName("Laptop")).thenReturn(Optional.of(product));
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.updateProductByProductName(updatedDto.getProductName(),updatedDto);

        assertEquals("Updated Desc",updatedProduct.getProductDescription());
        assertEquals(15, updatedProduct.getQuantity());
    }

    @Test
    void updateProductByProductName_ShouldThrow_WhenProductNotFound(){
        when(productRepository.findByProductName("Laptop")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, ()-> productService.updateProductByProductName("Laptop", productDto));
    }

    @Test
    void updateProductByProductName_ShouldThrow_WhenCategoryNotFound(){
        when(productRepository.findByProductName("Laptop")).thenReturn(Optional.of(product));
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, ()-> productService.updateProductByProductName("Laptop",productDto));
    }

    @Test
    void deleteProductByProductName_ShouldDeleteProduct(){
        when(productRepository.findByProductName("Laptop")).thenReturn(Optional.of(product));
        productService.deleteProductByProductName("Laptop");
        verify(productRepository,times(1)).delete(product);
    }

    @Test
    void deleteProductByProductName_ShouldThrow_WhenProductNotFound(){
        when(productRepository.findByProductName("Laptop")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, ()-> productService.deleteProductByProductName("Laptop"));
    }

    @Test
    void updateProductQuantityByProductName_ShouldUpdateQuantity(){
        when(productRepository.findByProductName("Laptop")).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation-> invocation.getArgument(0));

        Product updatedQuantityProduct = productService.updateProductQuantityByProductName("Laptop", 3);
        assertEquals(7,updatedQuantityProduct.getQuantity());
        verify(productRepository,times(1)).save(product);

    }

    @Test
    void updateProductQuantityByProductName_ShouldThrow_WhenProductNotFound(){
        when(productRepository.findByProductName("Laptop")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, ()-> productService.updateProductQuantityByProductName("Laptop",5));
    }

    @Test
    void updateProductQuantityByProductName_ShouldThrow_WhenInsufficientQuantity(){
        when(productRepository.findByProductName("Laptop")).thenReturn(Optional.of(product));

        assertThrows(RuntimeException.class, ()-> productService.updateProductQuantityByProductName("Laptop", 15));
    }
}
