package com.example.store;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepository {

    private final List<Product> products;

    public ProductRepository() {
        products = new ArrayList<>();
        // Инициализация продуктов
        products.add(new Product(1L, "Laptop", 1500.0));
        products.add(new Product(2L, "Smartphone", 800.0));
        products.add(new Product(3L, "Headphones", 200.0));
        products.add(new Product(4L, "Monitor", 300.0));
        products.add(new Product(5L, "Keyboard", 100.0));
        System.out.println("ProductRepository initialized with products: " + products);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    public Product findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
