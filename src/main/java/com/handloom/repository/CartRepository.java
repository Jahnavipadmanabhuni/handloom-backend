package com.handloom.repository;

import com.handloom.model.Cart;
import com.handloom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByBuyer(User buyer);
}
