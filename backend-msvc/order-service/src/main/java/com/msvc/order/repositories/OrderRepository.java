package com.msvc.order.repositories;

import com.msvc.order.entities.Order;
import com.msvc.order.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

//    List<Order> findByClientOrderByCreatedAtDesc(User client);
//
//    List<Order> findByEmployeeOrderByCreatedAtDesc(User employee);

//    List<Order> findByClientAndStatusOrderByCreatedAtDesc(User client, OrderStatus status);
//
//    List<Order> findByClientAndCreatedAtBetweenOrderByCreatedAtDesc(User client, LocalDateTime startDate, LocalDateTime endDate);

    List<Order> findAllByOrderByCreatedAtDesc();

    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);

    List<Order> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime startDate, LocalDateTime endDate);

}