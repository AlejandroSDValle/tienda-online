package com.msv.product.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    private boolean discount;

    @Column(name = "offer_price")
    private BigDecimal offerPrice;

    private int stock;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}
