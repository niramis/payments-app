package com.payments.unit.mock;

import com.payments.core.dto.PaymentDto;
import com.payments.core.model.Payment;
import com.payments.core.ports.out.PaymentOperationsOut;
import com.payments.infrastructure.PaymentMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentOperationsOutMock implements PaymentOperationsOut {

    private final Map<Long, Payment> payments;

    public PaymentOperationsOutMock() {
        payments = new HashMap<>();
    }

    @Override
    public List<Payment> getPayments() {
        return new ArrayList<>(payments.values());
    }

    @Override
    public Payment savePayment(PaymentDto paymentDto) {
        Payment payment = PaymentMapper.mapToPayment(paymentDto);

        Long id = Long.valueOf(payments.size()) + 1;
        payment.setId(id);

        payments.put(Long.valueOf(id), payment);

        return payment;
    }

    @Override
    public Payment updatePayment(PaymentDto paymentDto, Long id) {
        Payment payment = payments.get(id);

        if (payment != null) {
            payment = PaymentMapper.mapToPayment(paymentDto);
            payment.setId(Long.valueOf(id));
        }

        return payment;
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = payments.get(id);
        payments.remove(id, payment);
    }
}
