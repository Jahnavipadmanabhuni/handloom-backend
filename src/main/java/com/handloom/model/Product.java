package com.handloom.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    private Double price;
    private Integer stock;
    private String category;
    private String image;
    private String materials;
    private String dimensions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artisan_id")
    private User artisan;
}
