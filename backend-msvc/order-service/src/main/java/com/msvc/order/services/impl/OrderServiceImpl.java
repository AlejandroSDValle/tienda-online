package com.msvc.order.services.impl;

import com.libs.msvc.commons.dto.product.ProductResponse;
import com.msvc.order.client.ProductFeignClient;
import com.msvc.order.dto.order.CreateOrderRequest;
import com.msvc.order.dto.order.OrderItem;
import com.msvc.order.dto.order.OrderResponse;
import com.msvc.order.entities.Order;
import com.msvc.order.entities.OrderItems;
import com.msvc.order.enums.OrderStatus;
import com.msvc.order.exception.ObjectNotFoundException;
import com.msvc.order.repositories.OrderRepository;
import com.msvc.order.services.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private ProductFeignClient client;

    public OrderServiceImpl(OrderRepository orderRepository, ProductFeignClient client) {
        this.orderRepository = orderRepository;
        this.client = client;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getUserOrders(Long opt) {
        List<Order> orders = orderRepository.findAll();
        Map<Long, ProductResponse> products = client.getAllProductsWithError(opt).stream()
                .collect(Collectors.toMap(ProductResponse::getId, p->p));
        return orders.stream().map(order -> getOrderResponse(order, products)).toList();
    }

    @Override
    @Transactional
    public void createOrder(CreateOrderRequest orderRequest) {
        Map<Long, ProductResponse> products = client.getAllProducts().stream()
                .collect(Collectors.toMap(ProductResponse::getId, p->p));

        if (orderRequest.getItems() == null || orderRequest.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product");
        }

        Order order = new Order();

        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(LocalDateTime.now());
        List<OrderItems> items = new ArrayList<>();

        for (OrderItem item : orderRequest.getItems()) {
            ProductResponse product = Optional.ofNullable(products.get(item.getIdProduct()))
                    .orElseThrow(()-> new ObjectNotFoundException("Product with id " + item.getIdProduct() + " does not exist"));

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("The product " + product.getName() + " does not have enough stock");
            }

            product.setStock(product.getStock() - item.getQuantity());
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(order);
            orderItem.setProductId(item.getIdProduct());
            orderItem.setQuantity(item.getQuantity());
            items.add(orderItem);
        }
        order.setItems(items);
        orderRepository.save(order);
    }


    private static OrderResponse getOrderResponse(Order order, Map<Long, ProductResponse> products) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setStatus(order.getStatus());

        List<OrderResponse.OrderItemsResponse> itemResponses = order.getItems().stream().map(orderItem -> {
            ProductResponse product =  products.get(orderItem.getProductId());

            OrderResponse.OrderItemsResponse itemDto = new OrderResponse.OrderItemsResponse();
            itemDto.setNameProduct(product.getName());
            itemDto.setDescription(product.getDescription());
            itemDto.setPrice(product.getPrice());
            itemDto.setDiscount(product.isDiscount());
            itemDto.setOfferPrice(product.getOfferPrice());
//            itemDto.setBrand(product.getBrand().getName());
            itemDto.setQuantity(orderItem.getQuantity());
            return itemDto;
        }).collect(Collectors.toList());

        orderResponse.setItems(itemResponses);
        return orderResponse;
    }
}
