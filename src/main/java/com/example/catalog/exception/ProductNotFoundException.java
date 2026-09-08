package com.example.catalog.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(UUID id) {
        super("Product not exists : "+id);
    }
}
