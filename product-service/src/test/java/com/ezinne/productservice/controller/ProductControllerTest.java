package com.ezinne.productservice.controller;

import com.ezinne.productservice.model.Product;
import com.ezinne.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductControllerTest {
    @Mock
    private ProductService productService;

    @Autowired
    private ProductController productController;
    @Autowired
    private ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService)).build();
    }

    @Test
    void createProduct() throws Exception {
        Product product = Product.builder()
                .name("Utaba")
                .description("speciale")
                .price(new BigDecimal("50.00"))
                .build();
        productService.createProduct(product);

        this.mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void productList() throws Exception {
        Product product = Product.builder()
                .name("Utaba")
                .description("speciale")
                .price(new BigDecimal("50.00"))
                .build();
        productService.createProduct(product);

        this.mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andReturn();
    }
}