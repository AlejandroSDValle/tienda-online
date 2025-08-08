package com.msvc.order.controllers;

import com.msvc.order.dto.order.CreateOrderRequest;
import com.msvc.order.dto.order.OrderResponse;
import com.msvc.order.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final CircuitBreakerFactory cBreakerFactory;

    public OrderController(OrderService orderService, CircuitBreakerFactory cBreakerFactory) {
        this.orderService = orderService;
        this.cBreakerFactory = cBreakerFactory;
    }

    @GetMapping("/me/{opt}")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable Long opt){
        List<OrderResponse> orders = cBreakerFactory.create("orders").run(() -> orderService.getUserOrders(opt), e -> {
            logger.error(e.getMessage());
            OrderResponse order = new OrderResponse();
            return List.of(order);
        });
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest orderRequest){
        orderService.createOrder(orderRequest);
        return new ResponseEntity<>("Order created", HttpStatus.CREATED);
    }


}

