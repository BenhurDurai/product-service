package com.poc.ProductMicroservice.controller;

import com.poc.ProductMicroservice.dto.ProductDto;
import com.poc.ProductMicroservice.model.Product;
import com.poc.ProductMicroservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDto productDto){
        log.info("Received request to create product: {}", productDto.getProductName());
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(){
        log.info("Received request to retrieve all products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productName}")
    public ResponseEntity<Product> getProductByProductName(@PathVariable String productName){
        log.info("Received request to retrieve product: {}", productName);
        return ResponseEntity.ok(productService.getProductByProductName(productName));
    }

    @PutMapping("/{productName}")
    public ResponseEntity<Product> updateProductByProductName(@PathVariable String productName, @RequestBody ProductDto productDto ){
        log.info("Received request to update product: {}", productName);
        return ResponseEntity.ok(productService.updateProductByProductName(productName,productDto));
    }

    @DeleteMapping("/{productName}")
    public ResponseEntity<String> deleteProductByProductName(@PathVariable String productName){
        log.info("Received request to delete product: {}", productName);
        productService.deleteProductByProductName(productName);
        return ResponseEntity.ok("Product: " + productName + " deleted successfully");
    }

    @PutMapping("/updateQuantity/{productName}/{quantity}")
    public ResponseEntity<Product> updateProductQuantity(@PathVariable("productName") String productName, @PathVariable("quantity") int quantity){
        log.info("Received request to update product quantity: ", productName);
        productService.updateProductQuantityByProductName(productName, quantity);
        return ResponseEntity.ok(productService.updateProductQuantityByProductName(productName,quantity));
    }

}
