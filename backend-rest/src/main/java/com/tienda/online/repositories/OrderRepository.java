package com.tienda.online.repositories;

import com.tienda.online.entities.Order;
import com.tienda.online.entities.security.User;
import com.tienda.online.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByClientOrderByCreatedAtDesc(User client);

    List<Order> findByEmployeeOrderByCreatedAtDesc(User employee);

    List<Order> findByClientAndStatusOrderByCreatedAtDesc(User client, OrderStatus status);

    List<Order> findByClientAndCreatedAtBetweenOrderByCreatedAtDesc(User client, LocalDateTime startDate, LocalDateTime endDate);

    List<Order> findAllByOrderByCreatedAtDesc();

    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);

    List<Order> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime startDate, LocalDateTime endDate);

}
