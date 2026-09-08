package com.example.catalog.controller;

import com.example.catalog.dto.ProductRequestDTO;
import com.example.catalog.entity.Product;
import com.example.catalog.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO)
    {
       Product product = productService.createProduct(productRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id)
    {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable)
    {
       return ResponseEntity.ok(productService.getAllProducts(pageable));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductRequestDTO productRequestDTO)
    {
        System.out.println("=======ProductController======= "+id);
        return ResponseEntity.ok(productService.updateProduct(id,productRequestDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable UUID id)
    {
        productService.deleteProduct(id);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("Message","Product Deleted" +id);
        return ResponseEntity.ok(response);
    }




}
