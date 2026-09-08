package com.example.catalog;

import com.example.catalog.entity.Product;
import com.example.catalog.entity.ProductStatus;
import com.example.catalog.repository.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepo productRepo;

    @BeforeEach
    void setUp()
    {
        productRepo.deleteAll();
    }

    @Test
   void shouldCreateProduct() throws Exception
    {
        String request = """ 
        {
        "name": "Samsung",
        "price": "70000.0",
        "status": "ACTIVE"
        }""";
  mockMvc.perform(post("/api/product/create")
          .contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().isCreated())
          .andExpect(jsonPath("$.name").value("Samsung"))
          .andExpect(jsonPath("$.price").value("70000.0"))
          .andExpect(jsonPath("$.status").value("ACTIVE"));
  assert productRepo.count() == 1;

    }
    @Test
    void shouldGetProductById() throws Exception
    {
        Product product = new Product();
        product.setName("Samsung");
        product.setPrice(new BigDecimal("70000.0"));
        product.setStatus(ProductStatus.ACTIVE);

        Product saveProduct = productRepo.save(product);

        mockMvc.perform(get("/api/product/"+saveProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Samsung"))
                .andExpect(jsonPath("$.price").value("70000.0"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

    }

    @Test
    void shouldUpdateProduct() throws Exception
    {
        Product product = new Product();
        product.setName("Samsung");
        product.setPrice(new BigDecimal("70000.0"));
        product.setStatus(ProductStatus.ACTIVE);

        Product saveProduct = productRepo.save(product);


            String request = """ 
        {
        "name": "Samsung Updated",
        "price": "70000.0",
        "status": "ACTIVE"
        }""";

        mockMvc.perform(put("/api/product/"+saveProduct.getId()).contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Samsung Updated"))
                .andExpect(jsonPath("$.price").value("70000.0"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

    }


    @Test
    void shouldDeleteProduct() throws Exception
    {
        Product product = new Product();
        product.setName("Samsung");
        product.setPrice(new BigDecimal("70000.0"));
        product.setStatus(ProductStatus.ACTIVE);

        Product saveProduct = productRepo.save(product);

        mockMvc.perform(delete("/api/product/"+saveProduct.getId()))
                .andExpect(status().isOk());
        assert productRepo.findById(saveProduct.getId()).isEmpty();
    }

}
