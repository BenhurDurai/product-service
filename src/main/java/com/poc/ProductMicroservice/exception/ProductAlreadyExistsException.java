package com.poc.ProductMicroservice.exception;

public class ProductAlreadyExistsException extends RuntimeException{

    public ProductAlreadyExistsException(String message){
        super(message);
    }

}
