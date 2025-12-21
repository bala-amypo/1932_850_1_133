package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

import java.util.List;

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public Product createProduct(Product product) {
        return productService.createProduct(product);
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
