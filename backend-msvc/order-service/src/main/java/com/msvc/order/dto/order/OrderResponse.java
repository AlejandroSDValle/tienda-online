package com.msvc.order.dto.order;

import com.msvc.order.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class OrderResponse {

    private Long id;

    private String emailClient;

    private String nameClient;

    private String nameEmployee;

    private LocalDateTime createdAt;

    private OrderStatus status;

    private List<OrderItemsResponse> items;

    @Setter
    @Getter
    public static class OrderItemsResponse{
        private String nameProduct;

        private String description;

        private BigDecimal price;

        private boolean discount;

        private BigDecimal offerPrice;

        private String brand;

        private int quantity;

    }

}

