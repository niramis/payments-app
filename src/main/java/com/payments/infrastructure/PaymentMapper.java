package com.payments.infrastructure;

import com.payments.core.dto.PaymentDto;
import com.payments.core.model.Payment;

public class PaymentMapper {
    public static Payment mapToPayment(PaymentDto paymentDto) {
        Payment payment = Payment
                .builder()
                .name(paymentDto.getName())
                .amount(paymentDto.getAmount())
                .currency(paymentDto.getCurrency())
                .userId(paymentDto.getUserId())
                .targetBankAccountNumber(paymentDto.getTargetBankAccountNumber())
                .build();

        return payment;
    }
}
