package com.tienda.online.services;

import com.tienda.online.dto.order.CreateOrderRequest;
import com.tienda.online.dto.order.OrderResponse;
import com.tienda.online.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    List<OrderResponse> getUserOrders();

    List<OrderResponse> getUserOrdersByStatus(OrderStatus status);

    List<OrderResponse> getUserOrdersByDateRange(LocalDate startDate, LocalDate endDate);

    List<OrderResponse> getAllOrders();

    List<OrderResponse> getAllOrdersByStatus(OrderStatus status);

    List<OrderResponse> getAllOrdersByDateRange(LocalDate startDate, LocalDate endDate);

    List<OrderResponse> getAllOrdersByEmployee(Long id);

    List<OrderResponse> getAllOrdersByClient(Long id);

    void createOrder(CreateOrderRequest orderRequest);

    OrderResponse updateStatus(Long id, OrderStatus status);
}
