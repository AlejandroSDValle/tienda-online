package com.msvc.order.controllers;

import com.msvc.order.dto.order.CreateOrderRequest;
import com.msvc.order.dto.order.OrderResponse;
import com.msvc.order.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<OrderResponse>> getUserOrders(){
        return new ResponseEntity<>(orderService.getUserOrders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest orderRequest){
        orderService.createOrder(orderRequest);
        return new ResponseEntity<>("Order created", HttpStatus.CREATED);
    }


}

