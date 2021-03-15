package com.payments.application;

import com.payments.core.dto.PaymentDto;
import com.payments.core.model.Payment;
import com.payments.core.ports.in.PaymentUseCase;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @NonNull
    private final PaymentUseCase paymentUseCase;

    @GetMapping
    ResponseEntity<List<Payment>> getPayments() {
        return ResponseEntity.ok(paymentUseCase.getPayments());
    }

    @PostMapping
    ResponseEntity<Payment> savePayment(@RequestBody @Valid PaymentDto paymentDto) {
        return new ResponseEntity<>(paymentUseCase.savePayment(paymentDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<Payment> updatePayment(@RequestBody PaymentDto paymentDto, @PathVariable("id") Long id) {
        return new ResponseEntity<>(paymentUseCase.updatePayment(paymentDto, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deletePayment(@PathVariable("id") Long id) {
        paymentUseCase.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
