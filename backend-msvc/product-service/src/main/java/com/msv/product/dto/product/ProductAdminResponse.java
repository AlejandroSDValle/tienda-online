package com.msv.product.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductAdminResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private BigDecimal purchasePrice;

    private boolean discount;

    private BigDecimal offerPrice;

    private int stock;

    private LocalDateTime createdAt;

}
