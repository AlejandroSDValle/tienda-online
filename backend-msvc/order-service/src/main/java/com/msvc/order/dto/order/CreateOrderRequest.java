package com.msvc.order.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CreateOrderRequest {

    private Long userId;

    private List<OrderItem> items;

}
