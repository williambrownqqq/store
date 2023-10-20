package com.zanchenko.alex.store.service;

import com.zanchenko.alex.store.domain.Order;
import com.zanchenko.alex.store.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    Order getOrderById(Long orderId);
    void updateOrder(Order order);

    Order createOrder(OrderDTO orderDTO);

    List<Order> getAllOrders();
}
