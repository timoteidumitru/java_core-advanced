package com.javaPlayground.exceptions.globalExceptionHandling.service;

import com.javaPlayground.exceptions.globalExceptionHandling.exception.InvalidProductException;
import com.javaPlayground.exceptions.globalExceptionHandling.exception.ProductNotFoundException;
import com.javaPlayground.exceptions.globalExceptionHandling.model.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    private final Map<Long, Product> products = new HashMap<>();

    public ProductService() {
        products.put(1L, new Product(1L, "Laptop Gaming HP", 1200.0));
        products.put(2L, new Product(2L, "iPhone 17 Pro", 800.0));
        products.put(3L, new Product(3L, "Video Card Nvidia 4070", 1800.0));
        products.put(4L, new Product(4L, "Led Screen LG 26", 300.0));
        products.put(5L, new Product(5L, "Mouse Gaming Razor", 200.0));
        products.put(6L, new Product(6L, "Keyboard Slim FX300", 250.0));
        products.put(7L, new Product(7L, "Samsung Galaxy S25 Edge", 1270.0));
    }

    public Product getProductById(Long id) {
        return Optional.ofNullable(products.get(id))
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product addProduct(Product product) {
        if (product.getPrice() <= 0) {
            throw new InvalidProductException("Price must be greater than zero");
        }
        products.put(product.getId(), product);
        return product;
    }
}
