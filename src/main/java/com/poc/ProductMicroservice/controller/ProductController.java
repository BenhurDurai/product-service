package com.poc.ProductMicroservice.controller;

import com.poc.ProductMicroservice.dto.ProductDto;
import com.poc.ProductMicroservice.model.Product;
import com.poc.ProductMicroservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public Product createProduct(@RequestBody @Valid ProductDto productDto){
        log.info("Received request to create product: {}", productDto.getProductName());
        return productService.createProduct(productDto);
    }

    @GetMapping()
    public List<Product> getAllProducts(){
        log.info("Received request to retrieve all products");
        return productService.getAllProducts();
    }

    @GetMapping("/{productName}")
    public Product getProductByProductName(@PathVariable String productName){
        log.info("Received request to retrieve product: {}", productName);
        return productService.getProductByProductName(productName);
    }

    @PutMapping("/{productName}")
    public Product updateProductByProductName(@PathVariable String productName, @RequestBody ProductDto productDto ){
        log.info("Received request to update product: {}", productName);
        return productService.updateProductByProductName(productName,productDto);
    }

    @DeleteMapping("/{productName}")
    public String deleteProductByProductName(@PathVariable String productName){
        log.info("Received request to delete product: {}", productName);
        productService.deleteProductByProductName(productName);
        return "Product: " + productName + " deleted successfully";
    }

    @PutMapping("/updateQuantity/{productName}/{quantity}")
    public String updateProductQuantity(@PathVariable("productName") String productName, @PathVariable("quantity") int quantity){
        log.info("Received request to update product quantity: ", productName);
        productService.updateProductQuantityByProductName(productName, quantity);
        return "Quantity of product: " + productName + " updated successfully";
    }

}
