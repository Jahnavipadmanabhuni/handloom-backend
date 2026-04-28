package com.handloom.repository;

import com.handloom.model.Product;
import com.handloom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByArtisan(User artisan);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByPriceBetween(Double min, Double max);
}
