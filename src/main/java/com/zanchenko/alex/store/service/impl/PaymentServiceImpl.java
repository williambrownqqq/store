package com.zanchenko.alex.store.service.impl;

import com.zanchenko.alex.store.domain.Order;
import com.zanchenko.alex.store.exception.OrderAlreadyPaidException;
import com.zanchenko.alex.store.exception.OrderNotFoundException;
import com.zanchenko.alex.store.service.OrderService;
import com.zanchenko.alex.store.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderService orderService;

    public void markOrderAsPaid(Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }
        if (order.isPaid()) {
            throw new OrderAlreadyPaidException("Order is already marked as paid.");
        }
        order.setPaid(true); // Update the order status to "paid"
        orderService.updateOrder(order);
    }
}
