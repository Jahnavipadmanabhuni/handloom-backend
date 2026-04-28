package com.handloom.repository;

import com.handloom.model.Order;
import com.handloom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyer(User buyer);
    List<Order> findByStatus(String status);
}
