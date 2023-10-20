package com.zanchenko.alex.store.service;

public interface PaymentService {

    void markOrderAsPaid(Long orderId);
}
