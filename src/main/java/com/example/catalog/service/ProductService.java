package com.example.catalog.service;
import com.example.catalog.dto.ProductRequestDTO;
import com.example.catalog.entity.Product;
import com.example.catalog.exception.ProductNotFoundException;
import com.example.catalog.repository.ProductRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo)
    {
        this.productRepo = productRepo;
    }

    public Product createProduct(ProductRequestDTO productRequestDTO)
    {
        Product product = new Product(
                productRequestDTO.name()
                ,productRequestDTO.price()
                ,productRequestDTO.status());
        return productRepo.save(product);
    }

    public Product getProductById(UUID id)
    {
        return productRepo.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
    }

    public Page<Product> getAllProducts(Pageable pageable)
    {
        return productRepo.findAll(pageable);
    }

    public Product updateProduct(UUID id, ProductRequestDTO productRequestDTO)
    {
        Product product = getProductById(id);
        System.out.println("***********" +product+ "*********");
        product.setName(productRequestDTO.name());
        product.setPrice(productRequestDTO.price());
        product.setStatus(productRequestDTO.status());
        return productRepo.save(product);
    }

    public void deleteProduct(UUID id) {
    Product product = productRepo.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
       productRepo.delete(product);
    }

    public void DeleteAllProducts() {
       productRepo.deleteAll();
    }
}
