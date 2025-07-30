package com.msvc.order.dto.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItem {
    private Long idProduct;
    private int quantity;

}
