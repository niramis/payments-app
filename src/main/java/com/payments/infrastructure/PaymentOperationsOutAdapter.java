package com.payments.infrastructure;

import com.payments.application.exception.NotFoundPaymentException;
import com.payments.core.dto.PaymentDto;
import com.payments.core.model.Payment;
import com.payments.core.ports.out.PaymentOperationsOut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("!production")
public class PaymentOperationsOutAdapter implements PaymentOperationsOut {

    private final Map<Long, Payment> paymentsMap;

    PaymentOperationsOutAdapter() {
        paymentsMap = new HashMap<>();
    }

    @Override
    public List<Payment> getPayments() {
        List<Payment> payments = new ArrayList<>(paymentsMap.values());
        return payments;
    }

    @Override
    public Payment savePayment(PaymentDto paymentDto) {
        Payment payment = PaymentMapper.mapToPayment(paymentDto);
        Long id = Long.valueOf(paymentsMap.size() + 1);
        payment.setId(id);
        paymentsMap.put(id, payment);

        return payment;
    }

    @Override
    public Payment updatePayment(PaymentDto paymentDto, Long id) {
        Payment payment = paymentsMap.get(id);

        if (payment != null) {
            payment = PaymentMapper.mapToPayment(paymentDto);
            payment.setId(id);
            paymentsMap.put(id, payment);
        } else {
            throw new NotFoundPaymentException();
        }

        return payment;
    }

    @Override
    public void deletePayment(Long id) {
        paymentsMap.remove(id);
    }
}
