package com.handloom.controller;

import com.handloom.config.JwtUtil;
import com.handloom.dto.OrderRequest;
import com.handloom.model.Order;
import com.handloom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    private String extractEmail(String header) {
        return jwtUtil.extractEmail(header.replace("Bearer ", ""));
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(
            @RequestHeader("Authorization") String auth,
            @RequestBody OrderRequest req) {
        try {
            return ResponseEntity.ok(orderService.placeOrder(extractEmail(auth), req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getMyOrders(
            @RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(orderService.getBuyerOrders(extractEmail(auth)));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(orderService.updateOrderStatus(id, body.get("status")));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
