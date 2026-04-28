package com.handloom.controller;

import com.handloom.config.JwtUtil;
import com.handloom.model.Product;
import com.handloom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final JwtUtil jwtUtil;

    private String extractEmail(String header) {
        return jwtUtil.extractEmail(header.replace("Bearer ", ""));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        if (category != null && !category.isEmpty()) {
            return ResponseEntity.ok(productService.getProductsByCategory(category));
        }
        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(productService.searchProducts(search));
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/my-products")
    public ResponseEntity<List<Product>> getMyProducts(
            @RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(productService.getProductsByArtisan(extractEmail(auth)));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(
            @RequestBody Product product,
            @RequestHeader("Authorization") String auth) {
        try {
            return ResponseEntity.ok(productService.createProduct(product, extractEmail(auth)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, product));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
