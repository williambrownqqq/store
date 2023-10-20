package com.zanchenko.alex.store.repository;

import com.zanchenko.alex.store.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
