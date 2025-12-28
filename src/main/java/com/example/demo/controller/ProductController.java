package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController
// @RequestMapping("/api/products")
// public class ProductController {

//     private final ProductService service;

//     public ProductController(ProductService service) {
//         this.service = service;
//     }

//     @PostMapping
//     @ResponseStatus(HttpStatus.CREATED)
//     public Product create(@RequestBody Product product) {
//         return service.createProduct(product);
//     }

//     @GetMapping("/{id}")
//     public Product get(@PathVariable Long id) {
//         return service.getProductById(id);
//     }

//     @GetMapping
//     public List<Product> getAll() {
//         return service.getAllProducts();
//     }

//     @DeleteMapping("/{id}")
//     @ResponseStatus(HttpStatus.NO_CONTENT)
//     public void deactivate(@PathVariable Long id) {
//         service.deactivateProduct(id);
//     }
// }

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.createProduct(product); // returns 200 OK
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @GetMapping
    public List<Product> getAll() {
        return service.getAllProducts();
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Long id) {
        service.deactivateProduct(id);
    }
}
