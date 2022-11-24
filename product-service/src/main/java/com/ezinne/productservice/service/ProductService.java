package com.ezinne.productservice.service;


import com.ezinne.productservice.model.Product;
import com.ezinne.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public Product createProduct(Product product) {
      return   productRepository.save(product);
    }


    public List<Product> getAllProducts() {
     return productRepository.findAll();
    }

}