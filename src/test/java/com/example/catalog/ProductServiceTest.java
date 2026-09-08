package com.example.catalog;

import com.example.catalog.dto.ProductRequestDTO;
import com.example.catalog.entity.Product;
import com.example.catalog.entity.ProductStatus;
import com.example.catalog.exception.ProductNotFoundException;
import com.example.catalog.repository.ProductRepo;
import com.example.catalog.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProduct()
    {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO("Apply-17", new BigDecimal("165000"), ProductStatus.ACTIVE);
        Product product = new Product(productRequestDTO.name(),productRequestDTO.price(), productRequestDTO.status());
        when(productRepo.save(any(Product.class))).thenReturn(product);
        Product pro = productService.createProduct(productRequestDTO);
        assertEquals("Apply-17",pro.getName());
        assertEquals(new BigDecimal("165000"),pro.getPrice());
        assertEquals(ProductStatus.ACTIVE,pro.getStatus());
        verify(productRepo).save(any(Product.class));


    }


    @Test
    void shouldDeleteProduct()
    {
        UUID id = UUID.randomUUID();
        Product product = new Product("Apple-17",new BigDecimal("165000"),ProductStatus.ACTIVE);
when(productRepo.findById(id)).thenReturn(Optional.of(product));
productService.deleteProduct(id);
verify(productRepo).findById(id);
verify(productRepo).delete(product);



    }
@Test
void shoudFindAllProducts()
{
    Pageable pageable = PageRequest.of(0,3);
    Product product1 = new Product("Apple-17",new BigDecimal("165000"),ProductStatus.ACTIVE);
    Product product2 = new Product("Vivo",new BigDecimal("90000"),ProductStatus.ACTIVE);
    Page<Product> page = new PageImpl<>(List.of(product1,product2));
when(productRepo.findAll(pageable)).thenReturn(page);
Page<Product> result = productService.getAllProducts(pageable);


    assertEquals(2,result.getTotalElements());
    assertEquals("Apple-17",result.getContent().get(0).getName());
    assertEquals("Vivo",result.getContent().get(1).getName());
    verify(productRepo).findAll(pageable);
}

    @Test
    void shouldThrowExceptionIfProductIsNotExists()
    {
        UUID id = UUID.randomUUID();
        when(productRepo.findById(id)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,()-> productService.getProductById(id));

    }



}
