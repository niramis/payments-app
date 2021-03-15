package com.payments.core.ports.in;

import com.payments.core.dto.PaymentDto;
import com.payments.core.model.Payment;

import java.util.List;

public interface PaymentUseCase {
    List<Payment> getPayments();

    Payment savePayment(PaymentDto paymentDto);

    Payment updatePayment(PaymentDto paymentDto, Long id);

    void deletePayment(Long id);
}
