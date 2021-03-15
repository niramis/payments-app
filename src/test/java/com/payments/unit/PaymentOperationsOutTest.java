package com.payments.unit;

import com.payments.core.dto.PaymentDto;
import com.payments.core.model.Payment;
import com.payments.core.ports.out.PaymentOperationsOut;
import com.payments.unit.mock.PaymentOperationsOutMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentOperationsOutTest {

    PaymentOperationsOut paymentOperationsOut;

    @BeforeEach
    void setUp() {
        paymentOperationsOut = new PaymentOperationsOutMock();
    }

    @Test
    void shouldGetPaymentListFromRepo() {
        assertThat(paymentOperationsOut.getPayments()).isNotNull();
        assertThat(paymentOperationsOut.getPayments()).size().isEqualTo(0);
    }

    @Test
    void shouldAddPaymentToRepo() {
        PaymentDto paymentDto = PaymentDto.builder().build();

        assertThat(paymentOperationsOut.getPayments()).size().isEqualTo(0);
        Payment payment = paymentOperationsOut.savePayment(paymentDto);
        assertThat(paymentOperationsOut.getPayments()).size().isEqualTo(1);
        assertThat(payment.getId()).isEqualTo(1);
    }

    @Test
    void shouldUpdatePaymentInRepo() {
        PaymentDto paymentDto = PaymentDto.builder()
                .name("name before")
                .build();

        Payment payment = paymentOperationsOut.savePayment(paymentDto);

        PaymentDto paymentUpdatedDto = PaymentDto.builder()
                .name("name after")
                .build();

        Payment updatedPayment = paymentOperationsOut.updatePayment(paymentUpdatedDto, payment.getId());

        assertThat(updatedPayment).isNotNull();
        assertThat(updatedPayment.getName()).isEqualTo("name after");
    }

    @Test
    void shouldDeletePaymentFromRepo() {
        PaymentDto paymentDto = PaymentDto.builder().build();

        Payment payment = paymentOperationsOut.savePayment(paymentDto);

        assertThat(paymentOperationsOut.getPayments()).size().isEqualTo(1);
        paymentOperationsOut.deletePayment(payment.getId());

        assertThat(paymentOperationsOut.getPayments()).size().isEqualTo(0);

    }
}
