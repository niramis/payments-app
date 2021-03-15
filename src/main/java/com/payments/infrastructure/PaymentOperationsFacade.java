package com.payments.infrastructure;

import com.payments.core.dto.PaymentDto;
import com.payments.core.model.Payment;
import com.payments.core.ports.in.PaymentUseCase;
import com.payments.core.ports.out.PaymentOperationsOut;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class PaymentOperationsFacade implements PaymentUseCase {

    @NonNull
    private final PaymentOperationsOut paymentOperationsOut;

    @Override
    public List<Payment> getPayments() {
        return paymentOperationsOut.getPayments();
    }

    @Override
    public Payment savePayment(PaymentDto paymentDto) {
        return paymentOperationsOut.savePayment(paymentDto);
    }

    @Override
    public Payment updatePayment(PaymentDto paymentDto, Long id) {
        return paymentOperationsOut.updatePayment(paymentDto, id);
    }

    @Override
    public void deletePayment(Long id) {
        paymentOperationsOut.deletePayment(id);
    }
}
