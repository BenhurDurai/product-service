package com.poc.ProductMicroservice.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message){
        super(message);
    }

}
