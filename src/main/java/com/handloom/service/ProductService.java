package com.handloom.service;

import com.handloom.model.Product;
import com.handloom.model.User;
import com.handloom.repository.ProductRepository;
import com.handloom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Product createProduct(Product product, String artisanEmail) {
        User artisan = userRepository.findByEmail(artisanEmail)
                .orElseThrow(() -> new RuntimeException("Artisan not found"));
        product.setArtisan(artisan);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updated) {
        Product product = getProductById(id);
        product.setName(updated.getName());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());
        product.setStock(updated.getStock());
        product.setCategory(updated.getCategory());
        product.setImage(updated.getImage());
        product.setMaterials(updated.getMaterials());
        product.setDimensions(updated.getDimensions());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByArtisan(String email) {
        User artisan = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Artisan not found"));
        return productRepository.findByArtisan(artisan);
    }
}