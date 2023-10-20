package com.zanchenko.alex.store.service.impl;

import com.zanchenko.alex.store.domain.Order;
import com.zanchenko.alex.store.dto.OrderDTO;
import com.zanchenko.alex.store.repository.OrderRepository;
import com.zanchenko.alex.store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.zanchenko.alex.store.mapper.OrderMapper.mapToOrder;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElse(null);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        Order order = mapToOrder(orderDTO);
        order.setPaid(false);
        order.setTotalPrice(orderDTO.calculateTotalPrice());
        order.setOrderTime(LocalDateTime.now());
        order = orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
