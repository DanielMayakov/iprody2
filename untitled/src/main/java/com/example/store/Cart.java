package com.example.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class Cart {

    private final ProductRepository productRepository;
    private final List<Product> cartItems = new ArrayList<>();

    @Autowired
    public Cart(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProductToCart(Long productId) {
        Product product = productRepository.findById(productId);
        if (product != null) {
            cartItems.add(product);
            System.out.println("Added to cart: " + product);
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    public List<Product> getCartItems() {
        return cartItems;
    }
}
