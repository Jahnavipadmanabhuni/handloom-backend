package com.handloom.config;

import com.handloom.model.*;
import com.handloom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        try {
            if (userRepository.count() > 0) {
                System.out.println("✅ Data already exists, skipping seed.");
                return;
            }

            User artisan = new User();
            artisan.setName("jahnavi padmanabhuni");
            artisan.setEmail("artisan@handloom.com");
            artisan.setPassword(passwordEncoder.encode("artisan123"));
            artisan.setRole("artisan");
            artisan.setPhone("+1234567891");
            artisan.setAddress("Artisan Village, India");
            artisan = userRepository.save(artisan);

            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@handloom.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("admin");
            admin.setPhone("+1234567890");
            admin.setAddress("Admin Office");
            userRepository.save(admin);

            User buyer = new User();
            buyer.setName("Global Buyer");
            buyer.setEmail("buyer@handloom.com");
            buyer.setPassword(passwordEncoder.encode("buyer123"));
            buyer.setRole("buyer");
            buyer.setPhone("+1234567892");
            buyer.setAddress("New York, USA");
            userRepository.save(buyer);

            User marketing = new User();
            marketing.setName("Jahnavi");
            marketing.setEmail("marketing@handloom.com");
            marketing.setPassword(passwordEncoder.encode("marketing123"));
            marketing.setRole("marketing");
            marketing.setPhone("+1234567893");
            marketing.setAddress("Marketing Office");
            userRepository.save(marketing);

            Product p1 = new Product();
            p1.setName("Handwoven Silk Saree");
            p1.setDescription("Beautiful handwoven silk saree with traditional patterns.");
            p1.setPrice(150.0);
            p1.setStock(10);
            p1.setCategory("clothing");
            p1.setImage("https://t4.ftcdn.net/jpg/00/17/49/37/360_F_17493746_NxmDWBzvxFZjwi2lmDXVddPTI4nlb44p.jpg");
            p1.setMaterials("Pure Silk, Zari Thread");
            p1.setDimensions("6 yards");
            p1.setArtisan(artisan);
            productRepository.save(p1);

            Product p2 = new Product();
            p2.setName("Handcrafted Cotton Scarf");
            p2.setDescription("Eco-friendly cotton scarf with natural dyes.");
            p2.setPrice(25.0);
            p2.setStock(50);
            p2.setCategory("accessories");
            p2.setImage("https://itokri.com/cdn/shop/files/0Q8A5547_1ab5622e-f7c6-4ed4-9b70-4f09061a12ca.jpg");
            p2.setMaterials("Organic Cotton, Natural Dyes");
            p2.setDimensions("30x30 inches");
            p2.setArtisan(artisan);
            productRepository.save(p2);

            Product p3 = new Product();
            p3.setName("Traditional Wool Shawl");
            p3.setDescription("Warm and comfortable wool shawl with traditional embroidery.");
            p3.setPrice(75.0);
            p3.setStock(15);
            p3.setCategory("clothing");
            p3.setImage("https://via.placeholder.com/400x500/45B7D1/FFFFFF?text=Wool+Shawl");
            p3.setMaterials("Pure Wool, Cotton Thread");
            p3.setDimensions("70x40 inches");
            p3.setArtisan(artisan);
            productRepository.save(p3);

            System.out.println("✅ Sample data seeded successfully!");
            System.out.println("   admin@handloom.com     / admin123");
            System.out.println("   artisan@handloom.com   / artisan123");
            System.out.println("   buyer@handloom.com     / buyer123");
            System.out.println("   marketing@handloom.com / marketing123");

        } catch (Exception e) {
            System.out.println("⚠️ Seeding skipped: " + e.getMessage());
        }
    }
}