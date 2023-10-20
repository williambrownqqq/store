package com.zanchenko.alex.store.dto;

import com.zanchenko.alex.store.domain.OrderItem;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    Long id;
    boolean isPaid;
    List<OrderItem> orderItems;

    public double calculateTotalPrice(){
        return orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getGoods().getPrice() * orderItem.getQuantity())
                .sum();
    }
}
