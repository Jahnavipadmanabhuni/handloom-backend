package com.handloom.service;

import com.handloom.config.JwtUtil;
import com.handloom.dto.*;
import com.handloom.model.User;
import com.handloom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .phone(req.getPhone())
                .address(req.getAddress())
                .build();
        user = userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        if (!user.getRole().equals(req.getRole())) {
            throw new RuntimeException("Role mismatch");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}