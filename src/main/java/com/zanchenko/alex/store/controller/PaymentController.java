package com.zanchenko.alex.store.controller;

import com.zanchenko.alex.store.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@CrossOrigin
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay/{orderId}") // "pay" for order
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<String> markOrderAsPaid(@PathVariable Long orderId) {
        paymentService.markOrderAsPaid(orderId);
        return new ResponseEntity<>("Order marked as paid.", HttpStatus.OK);
    }
}
