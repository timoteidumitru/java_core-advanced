package com.javaPlayground.exceptions.globalExceptionHandling.controller;

import com.javaPlayground.exceptions.globalExceptionHandling.model.Product;
import com.javaPlayground.exceptions.globalExceptionHandling.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private final ProductService service = new ProductService();

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody Product product) {
        return service.addProduct(product);
    }
}
