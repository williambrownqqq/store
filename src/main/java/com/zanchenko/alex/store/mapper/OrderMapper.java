package com.zanchenko.alex.store.mapper;

import com.zanchenko.alex.store.domain.Good;
import com.zanchenko.alex.store.domain.Order;
import com.zanchenko.alex.store.dto.GoodDTO;
import com.zanchenko.alex.store.dto.OrderDTO;

public class OrderMapper {

    public static OrderDTO mapToOrderDTO(Order order){
        return OrderDTO.builder()
                .id(order.getId())
                .orderItems(order.getOrderItems())
                .build();
    }

    public static Order mapToOrder(OrderDTO orderDTO){
        return Order.builder()
                .id(orderDTO.getId())
                .orderItems(orderDTO.getOrderItems())
                .build();
    }

}
