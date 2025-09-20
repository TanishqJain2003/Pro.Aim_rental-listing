package com.proaim.controller;

import com.proaim.entity.Payment;
import com.proaim.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository paymentRepository;

    // ✅ Get all payments
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // ✅ Get a payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Create a new payment
    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());
        payment.setVersion(0L); // 👈 ensure version starts at 0
        return paymentRepository.save(payment);
    }

    // ✅ Update payment
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment updatedPayment) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    // update fields
                    payment.setStatus(updatedPayment.getStatus());
                    payment.setAmount(updatedPayment.getAmount());
                    payment.setPaymentDate(updatedPayment.getPaymentDate());
                    payment.setDueDate(updatedPayment.getDueDate());
                    payment.setPaymentDescription(updatedPayment.getPaymentDescription());
                    payment.setMethod(updatedPayment.getMethod());
                    payment.setTransactionId(updatedPayment.getTransactionId());
                    payment.setLateFee(updatedPayment.getLateFee());
                    payment.setProcessingFee(updatedPayment.getProcessingFee());
                    payment.setTotalAmount(updatedPayment.getTotalAmount());
                    payment.setUpdatedAt(LocalDateTime.now());

                    // 👇 version is automatically handled by JPA
                    return ResponseEntity.ok(paymentRepository.save(payment));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete payment
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable Long id) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    paymentRepository.delete(payment);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
