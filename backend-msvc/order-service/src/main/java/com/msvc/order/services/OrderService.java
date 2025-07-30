package com.msvc.order.services;

import com.msvc.order.dto.order.CreateOrderRequest;
import com.msvc.order.dto.order.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getUserOrders();

//    List<OrderResponse> getUserOrdersByStatus(OrderStatus status);
//
//    List<OrderResponse> getUserOrdersByDateRange(LocalDate startDate, LocalDate endDate);
//
//    List<OrderResponse> getAllOrders();
//
//    List<OrderResponse> getAllOrdersByStatus(OrderStatus status);
//
//    List<OrderResponse> getAllOrdersByDateRange(LocalDate startDate, LocalDate endDate);
//
//    List<OrderResponse> getAllOrdersByEmployee(Long id);
//
//    List<OrderResponse> getAllOrdersByClient(Long id);

    void createOrder(CreateOrderRequest orderRequest);

//    OrderResponse updateStatus(Long id, OrderStatus status);
}
