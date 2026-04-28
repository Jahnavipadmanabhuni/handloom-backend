package com.handloom.service;

import com.handloom.dto.OrderRequest;
import com.handloom.model.*;
import com.handloom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    public Order placeOrder(String email, OrderRequest req) {
        User buyer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = Order.builder()
                .buyer(buyer)
                .orderDate(LocalDate.now())
                .total(req.getTotal())
                .status("pending")
                .items(new ArrayList<>())
                .build();
        List<OrderItem> items = new ArrayList<>();
        for (OrderRequest.OrderItemRequest i : req.getItems()) {
            Product product = productRepository.findById(i.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            items.add(OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(i.getQuantity())
                    .price(i.getPrice())
                    .build());
        }
        order.setItems(items);
        Order saved = orderRepository.save(order);
        cartService.clearCart(email);
        return saved;
    }

    public List<Order> getBuyerOrders(String email) {
        User buyer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByBuyer(buyer);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}