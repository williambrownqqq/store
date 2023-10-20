package com.zanchenko.alex.store.repository;

import com.zanchenko.alex.store.domain.Good;
import com.zanchenko.alex.store.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByIsPaidFalseAndOrderTimeBefore(LocalDateTime time);

}
