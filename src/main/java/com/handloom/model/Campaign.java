package com.handloom.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "campaigns")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String platform;
    private Double budget;
    private String startDate;
    private String endDate;
    private String audience;

    @Column(length = 2000)
    private String description;

    private String status; // active, paused, completed
    private Integer reach;
    private Double engagement;
    private Integer conversions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    private User createdBy;
}
