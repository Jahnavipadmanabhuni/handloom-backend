package com.handloom.controller;

import com.handloom.config.JwtUtil;
import com.handloom.model.Cart;
import com.handloom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final JwtUtil jwtUtil;

    private String extractEmail(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("No token provided");
        }
        return jwtUtil.extractEmail(header.replace("Bearer ", ""));
    }

    @GetMapping
    public ResponseEntity<?> getCart(
            @RequestHeader(value = "Authorization", required = false) String auth) {
        try {
            return ResponseEntity.ok(cartService.getCart(extractEmail(auth)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestHeader(value = "Authorization", required = false) String auth,
            @RequestBody Map<String, Object> body) {
        try {
            Long productId = Long.parseLong(body.get("productId").toString());
            int quantity = Integer.parseInt(body.get("quantity").toString());
            return ResponseEntity.ok(cartService.addToCart(extractEmail(auth), productId, quantity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateItem(
            @RequestHeader(value = "Authorization", required = false) String auth,
            @RequestBody Map<String, Object> body) {
        try {
            Long productId = Long.parseLong(body.get("productId").toString());
            int quantity = Integer.parseInt(body.get("quantity").toString());
            return ResponseEntity.ok(cartService.updateCartItem(extractEmail(auth), productId, quantity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeItem(
            @RequestHeader(value = "Authorization", required = false) String auth,
            @PathVariable Long productId) {
        try {
            return ResponseEntity.ok(cartService.removeFromCart(extractEmail(auth), productId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(
            @RequestHeader(value = "Authorization", required = false) String auth) {
        try {
            cartService.clearCart(extractEmail(auth));
            return ResponseEntity.ok("Cart cleared");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}