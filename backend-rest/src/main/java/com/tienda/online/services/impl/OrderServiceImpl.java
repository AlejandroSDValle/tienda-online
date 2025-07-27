package com.tienda.online.services.impl;

import com.tienda.online.dto.order.CreateOrderRequest;
import com.tienda.online.dto.order.OrderItem;
import com.tienda.online.dto.order.OrderResponse;
import com.tienda.online.entities.Order;
import com.tienda.online.entities.OrderItems;
import com.tienda.online.entities.Product;
import com.tienda.online.entities.security.User;
import com.tienda.online.enums.OrderStatus;
import com.tienda.online.enums.RoleEnum;
import com.tienda.online.exceptions.ObjectNotFoundException;
import com.tienda.online.repositories.OrderRepository;
import com.tienda.online.repositories.ProductRepository;
import com.tienda.online.repositories.security.UserRepository;
import com.tienda.online.services.OrderService;
import com.tienda.online.services.auth.AuthenticationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final AuthenticationService authService;

    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
                            AuthenticationService authService, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @Override
    public List<OrderResponse> getUserOrders() {
        User userLogged = userRepository.findByUsername(authService.findLoggedInUser().getUsername()).get();
        List<Order> orders = orderRepository.findByClientOrderByCreatedAtDesc(userLogged);
        return orders.stream().map(OrderServiceImpl::getOrderResponse).toList();
    }

    @Override
    public List<OrderResponse> getUserOrdersByStatus(OrderStatus status) {
        User userLogged = userRepository.findByUsername(authService.findLoggedInUser().getUsername()).get();
        List<Order> orders = orderRepository.findByClientAndStatusOrderByCreatedAtDesc(userLogged, status);
        return orders.stream().map(OrderServiceImpl::getOrderResponse).toList();
    }

    @Override
    public List<OrderResponse> getUserOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        User userLogged = userRepository.findByUsername(authService.findLoggedInUser().getUsername()).get();

        LocalDateTime start = (startDate != null) ? startDate.atStartOfDay() : LocalDate.of(2000, 1, 1).atStartOfDay();
        LocalDateTime end = (endDate != null) ? endDate.atTime(LocalTime.MAX) : LocalDateTime.now();

        List<Order> orders = orderRepository.findByClientAndCreatedAtBetweenOrderByCreatedAtDesc(userLogged, start, end);

        return orders.stream().map(OrderServiceImpl::getOrderResponse).toList();
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAllByOrderByCreatedAtDesc();
        return orders.stream().map(OrderServiceImpl::getOrderResponse).toList();
    }

    @Override
    public List<OrderResponse> getAllOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderRepository.findByStatusOrderByCreatedAtDesc(status);
        return orders.stream().map(OrderServiceImpl::getOrderResponse).toList();
    }

    @Override
    public List<OrderResponse> getAllOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = (startDate != null) ? startDate.atStartOfDay() : LocalDate.of(2000, 1, 1).atStartOfDay();
        LocalDateTime end = (endDate != null) ? endDate.atTime(LocalTime.MAX) : LocalDateTime.now();
        List<Order> orders = orderRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(start, end);
        return orders.stream().map(OrderServiceImpl::getOrderResponse).toList();
    }

    @Override
    public List<OrderResponse> getAllOrdersByEmployee(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("User with ID " + id + " Does not exist"));
        List<Order> orders = orderRepository.findByEmployeeOrderByCreatedAtDesc(user);
        return orders.stream().map(OrderServiceImpl::getOrderResponse).toList();
    }

    @Override
    public List<OrderResponse> getAllOrdersByClient(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("User with ID " + id + " Does not exist"));
        List<Order> orders = orderRepository.findByClientOrderByCreatedAtDesc(user);
        return orders.stream().map(OrderServiceImpl::getOrderResponse).toList();
    }


    @Override
    @Transactional
    public void createOrder(CreateOrderRequest orderRequest) {
        User userLogged = userRepository.findByUsername(authService.findLoggedInUser().getUsername()).get();
        String role = userLogged.getRole().getName();

        if (orderRequest.getItems() == null || orderRequest.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product");
        }

        Order order = new Order();

        if(role.equals(RoleEnum.ADMINISTRATOR.name()) || role.equals(RoleEnum.ASSISTANT_ADMINISTRATOR.name())){
            // If it is a store sale
            // If the Client do not have a account, This sale will not have a registered user.
            if(orderRequest.getUserId() == null){
                order.setClient(null);
            }else{
                User user = userRepository.findById(orderRequest.getUserId())
                        .orElseThrow(() -> new ObjectNotFoundException("User with ID " + orderRequest.getUserId() + " does not exist"));
                order.setClient(user);
            }
            order.setEmployee(userLogged);
            order.setStatus(OrderStatus.PICKED_UP);
        } else if (role.equals(RoleEnum.CUSTOMER.name())) {
            // If it is a order
            order.setClient(userLogged);
            order.setEmployee(null);
        }

        List<OrderItems> items = new ArrayList<>();

        for (OrderItem item : orderRequest.getItems()) {
            Product product = productRepository.findById(item.getIdProduct())
                    .orElseThrow(()-> new ObjectNotFoundException("Product with id " + item.getIdProduct() + " does not exist"));

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("The product " + product.getName() + " does not have enough stock");
            }

            product.setStock(product.getStock() - item.getQuantity());
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            items.add(orderItem);
        }
        order.setItems(items);
        orderRepository.save(order);
    }

    @Override
    public OrderResponse updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Order with ID " + id + " not found"));

        User userLogged = userRepository.findByUsername(authService.findLoggedInUser().getUsername()).get();

        order.setStatus(status);
        if(order.getStatus().equals(OrderStatus.PREPARING)){
            order.setEmployee(userLogged);
        }
        orderRepository.save(order);
        return getOrderResponse(order);
    }

    private static OrderResponse getOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setEmailClient((order.getClient() != null) ? order.getClient().getEmail() : null);
        orderResponse.setNameClient((order.getClient() != null) ? order.getClient().getName() : null);
        orderResponse.setNameEmployee((order.getEmployee() != null) ? order.getEmployee().getName() : null);
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setStatus(order.getStatus());

        List<OrderResponse.OrderItemsResponse> itemResponses = order.getItems().stream().map(orderItem -> {
            Product product = orderItem.getProduct();

            OrderResponse.OrderItemsResponse itemDto = new OrderResponse.OrderItemsResponse();
            itemDto.setNameProduct(product.getName());
            itemDto.setDescription(product.getDescription());
            itemDto.setPrice(product.getPrice());
            itemDto.setDiscount(product.isDiscount());
            itemDto.setOfferPrice(product.getOfferPrice());
            itemDto.setBrand(product.getBrand().getName());
            itemDto.setQuantity(orderItem.getQuantity());

            return itemDto;
        }).collect(Collectors.toList());

        orderResponse.setItems(itemResponses);
        return orderResponse;
    }
}
