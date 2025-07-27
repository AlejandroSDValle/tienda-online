package com.tienda.online.controllers;

import com.tienda.online.dto.order.CreateOrderRequest;
import com.tienda.online.dto.order.OrderResponse;
import com.tienda.online.enums.OrderStatus;
import com.tienda.online.services.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/me/status")
    public ResponseEntity<List<OrderResponse>> getUserOrdersByStatus(@RequestParam OrderStatus status){
        return new ResponseEntity<>(orderService.getUserOrdersByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/me/date")
    public ResponseEntity<List<OrderResponse>> getUserOrdersByDateRange(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        System.out.println(startDate);
        System.out.println(endDate);
        List<OrderResponse> orders = orderService.getUserOrdersByDateRange(startDate, endDate);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/admin/status")
    public ResponseEntity<List<OrderResponse>> getAllOrdersByStatus(@RequestParam OrderStatus status){
        return new ResponseEntity<>(orderService.getAllOrdersByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/admin/date")
    public ResponseEntity<List<OrderResponse>> getAllOrdersByDateRange(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<OrderResponse> orders = orderService.getAllOrdersByDateRange(startDate, endDate);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<List<OrderResponse>> getAllOrdersByEmployee(@PathVariable Long id) {
        List<OrderResponse> orders = orderService.getAllOrdersByEmployee(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<OrderResponse>> getAllOrdersByClient(@PathVariable Long id) {
        List<OrderResponse> orders = orderService.getAllOrdersByClient(id);
        return ResponseEntity.ok(orders);
    }


    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest orderRequest){
        orderService.createOrder(orderRequest);
        return new ResponseEntity<>("Order created", HttpStatus.CREATED);
    }

    @PatchMapping("/order/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long id,
                                                      @RequestParam OrderStatus status){
        return new ResponseEntity<>(orderService.updateStatus(id, status), HttpStatus.OK);
    }

}
