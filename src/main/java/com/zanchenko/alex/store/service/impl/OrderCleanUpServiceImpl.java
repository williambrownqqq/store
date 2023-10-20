package com.zanchenko.alex.store.service.impl;

import com.zanchenko.alex.store.domain.Order;
import com.zanchenko.alex.store.repository.OrderRepository;
import com.zanchenko.alex.store.service.OrderCleanUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCleanUpServiceImpl implements OrderCleanUpService {

    private OrderRepository orderRepository;
    @Override
    @Scheduled(fixedRate = 600000) // 10 minutes
    public void cleanupUnpaidOrders() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        List<Order> unpaidOrders = orderRepository.findByIsPaidFalseAndOrderTimeBefore(tenMinutesAgo);
        unpaidOrders.forEach(unpaidOrder -> orderRepository.delete(unpaidOrder));
    }
}
