package com.ezinne.productservice.service;

import com.ezinne.productservice.model.Product;
import com.ezinne.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp() {
         productService = new ProductService(productRepository);
    }

    @Test
    void createProduct() {
        Product product = Product.builder()
                .name("Utaba")
                .description("speciale")
                .price(new BigDecimal("50.00"))
                .build();
        productRepository.save(product);

        when(productRepository.save(any())).thenReturn(product);
        Product createdProduct = productService.createProduct(product);

        verify(productRepository, times(2)).save(any());
        assertThat(createdProduct.getDescription().compareToIgnoreCase("speciale"));
    }

    @Test
    void getAllProducts() {
        Product product = Product.builder()
                .name("Utaba")
                .description("speciale")
                .price(new BigDecimal("50.00"))
                .build();
        productRepository.save(product);

        when(productRepository.findAll()).thenReturn(List.of(product));
        List<Product> products = productService.getAllProducts();

        verify(productRepository, times(1)).findAll();
        assertThat(new ArrayList<>(products).contains(product));
    }
}