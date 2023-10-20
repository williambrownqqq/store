package com.zanchenko.alex.store.dto;

import com.zanchenko.alex.store.domain.Good;
import com.zanchenko.alex.store.domain.Order;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    Long id;
    Good goods;
    int quantity;
    OrderDTO orderDTO;
}
