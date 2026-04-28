package com.handloom.service;

import com.handloom.model.*;
import com.handloom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    private Cart getOrCreateCart(String email) {
        User buyer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByBuyer(buyer).orElseGet(() -> {
            Cart cart = Cart.builder()
                    .buyer(buyer)
                    .items(new ArrayList<>())
                    .build();
            return cartRepository.save(cart);
        });
    }

    public Cart getCart(String email) {
        return getOrCreateCart(email);
    }

    public Cart addToCart(String email, Long productId, int quantity) {
        Cart cart = getOrCreateCart(email);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem existing = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst().orElse(null);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
        } else {
            CartItem item = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .build();
            cart.getItems().add(item);
        }
        return cartRepository.save(cart);
    }

    public Cart updateCartItem(String email, Long productId, int quantity) {
        Cart cart = getOrCreateCart(email);
        cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
        return cartRepository.save(cart);
    }

    public Cart removeFromCart(String email, Long productId) {
        Cart cart = getOrCreateCart(email);
        cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));
        return cartRepository.save(cart);
    }

    public void clearCart(String email) {
        Cart cart = getOrCreateCart(email);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}