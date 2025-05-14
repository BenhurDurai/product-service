package com.poc.ProductMicroservice.service;

import com.poc.ProductMicroservice.dto.ProductDto;
import com.poc.ProductMicroservice.exception.CategoryNotFoundException;
import com.poc.ProductMicroservice.exception.ProductAlreadyExistsException;
import com.poc.ProductMicroservice.exception.ProductNotFoundException;
import com.poc.ProductMicroservice.model.Category;
import com.poc.ProductMicroservice.model.Product;
import com.poc.ProductMicroservice.repository.CategoryRepository;
import com.poc.ProductMicroservice.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(@Valid ProductDto productDto){
        if (productRepository.findByProductName(productDto.getProductName()).isPresent()){
            log.info("Product already exists, cannot create duplicate products");
            throw new ProductAlreadyExistsException("Product already present: " + productDto.getProductName());
        }

        Category category = categoryRepository.findByCategoryName(productDto.getCategoryName())
                .orElseThrow(()-> new CategoryNotFoundException("Category not found: " + productDto.getCategoryName()));

        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setProductDescription(productDto.getProductDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setCategory(category);

        log.info("Product created successfully: {}",product.getProductName());
        return productRepository.save(product);

    }

    public List<Product> getAllProducts(){
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    public Product getProductByProductName(String productName){
        log.info("Fetching product by name: {}",productName);
        return productRepository.findByProductName(productName)
                .orElseThrow(()-> new ProductNotFoundException("Product not found: " + productName));
    }

    public Product updateProductByProductName(String productName, ProductDto productDto){
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(()-> new ProductNotFoundException("Product not found: " + productName));

        Category category = categoryRepository.findByCategoryName(productDto.getCategoryName())
                .orElseThrow(()-> new CategoryNotFoundException("Category not found: " + productDto.getCategoryName()));

        product.setProductName(productDto.getProductName());
        product.setProductDescription(productDto.getProductDescription());
        product.setPrice(productDto.getPrice());
        int updateQuantity = product.getQuantity() + productDto.getQuantity();
        product.setQuantity(updateQuantity);
        product.setCategory(category);

        log.info("Product updated successfully: {}",productName);
        return productRepository.save(product);

    }

    public void deleteProductByProductName(String productName){
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(()-> new ProductNotFoundException("Product not found: " + productName));
        productRepository.delete(product);
        log.info("Product deleted successfully: {}",productName);
    }


    public Product updateProductQuantityByProductName(String productName, int quantity) {
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(()-> new ProductNotFoundException("Product not found: " + productName));

        int updatedQuantity = product.getQuantity()-quantity;
        if (updatedQuantity<0){
            throw new RuntimeException("Quantity not available");
        }
        product.setQuantity(updatedQuantity);
        log.info("Product quantity updated successfully: {}", productName);
        return productRepository.save(product);
    }
}
