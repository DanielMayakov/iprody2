package com.example.store;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Проверка создания бина ProductRepository
        ProductRepository productRepository = context.getBean(ProductRepository.class);
        System.out.println("ProductRepository bean: " + productRepository);
        // Вывод всех продуктов из репозитория
        System.out.println("Available products:");
        for (Product product : productRepository.findAll()) {
            System.out.println(product);
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Start a new shopping cart");
            System.out.println("2. Add product to cart");
            System.out.println("3. View cart items");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                // Создание новой корзины (новый бин Cart)
                Cart cart = context.getBean(Cart.class);
                System.out.println("New cart created! HashCode: " + cart.hashCode());
                while (true) {
                    System.out.println("\nAvailable products:");
                    for (Product product : productRepository.findAll()) {
                        System.out.println(product);
                    }
                    System.out.println("\nEnter product ID to add to cart (or 0 to finish): ");
                    Long productId = scanner.nextLong();
                    if (productId == 0) break;
                    cart.addProductToCart(productId);
                }
                System.out.println("Your cart items: " + cart.getCartItems());
            } else if (choice == 4) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        context.close();
    }
}
