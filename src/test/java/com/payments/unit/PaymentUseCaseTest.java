package com.payments.unit;

import com.payments.core.dto.PaymentDto;
import com.payments.core.model.Payment;
import com.payments.core.ports.in.PaymentUseCase;
import com.payments.unit.mock.PaymentUseCaseMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentUseCaseTest {
    PaymentUseCase paymentUseCase;

    @BeforeEach
    void setUp() {
        paymentUseCase = new PaymentUseCaseMock();
    }

    @Test
    void shouldGetPaymentListFromRepo() {
        assertThat(paymentUseCase.getPayments()).isNotNull();
        assertThat(paymentUseCase.getPayments()).size().isEqualTo(0);
    }

    @Test
    void shouldAddPaymentToRepo() {
        PaymentDto paymentDto = PaymentDto.builder().build();

        assertThat(paymentUseCase.getPayments()).size().isEqualTo(0);
        Payment payment = paymentUseCase.savePayment(paymentDto);
        assertThat(paymentUseCase.getPayments()).size().isEqualTo(1);
        assertThat(payment.getId()).isEqualTo(1);
    }

    @Test
    void shouldUpdatePaymentInRepo() {
        PaymentDto paymentDto = PaymentDto.builder()
                .name("name before")
                .build();

        Payment payment = paymentUseCase.savePayment(paymentDto);

        PaymentDto paymentUpdatedDto = PaymentDto.builder()
                .name("name after")
                .build();

        Payment updatedPayment = paymentUseCase.updatePayment(paymentUpdatedDto, payment.getId());

        assertThat(updatedPayment).isNotNull();
        assertThat(updatedPayment.getName()).isEqualTo("name after");
    }

    @Test
    void shouldDeletePaymentFromRepo() {
        PaymentDto paymentDto = PaymentDto.builder().build();

        Payment payment = paymentUseCase.savePayment(paymentDto);

        assertThat(paymentUseCase.getPayments()).size().isEqualTo(1);
        paymentUseCase.deletePayment(payment.getId());

        assertThat(paymentUseCase.getPayments()).size().isEqualTo(0);

    }
}
