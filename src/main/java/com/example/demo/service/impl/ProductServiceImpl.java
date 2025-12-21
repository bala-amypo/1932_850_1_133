package com.example.demo.service.impl;

import com.example.demo.entity.Product;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        if (productRepository.existsBySku(product.getSku())) {
            throw new BadRequestException("Duplicate SKU: SKU already exists");
        }
        return productRepository.save(product);
    }

    @Override
    public java.util.List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
