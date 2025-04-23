package com.poc.ProductMicroservice.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message){
        super(message);
    }

}
