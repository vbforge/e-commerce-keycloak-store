package com.vbforge.client.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Column(nullable = false)
    @Builder.Default
    private Integer stock = 0;

    public enum ProductCategory {
        ELECTRONICS, CLOTHING, BOOKS, FOOD, OTHER
    }
}