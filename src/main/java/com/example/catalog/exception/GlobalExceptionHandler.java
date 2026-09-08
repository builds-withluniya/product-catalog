package com.example.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFound(ProductNotFoundException ex)
    {
        Map<String, Object> error = new LinkedHashMap();
        error.put("status",404);
        error.put("error","Not Found");
        error.put("message",ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex)
    {
        Map<String,Object> fields = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> fields.put(error.getField(),error.getDefaultMessage()));
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("status",400);
        response.put("error","Bad Request");
        response.put("fields",fields);
        return ResponseEntity.badRequest().body(response);
    }

}
