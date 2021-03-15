package com.payments.unit;

import com.payments.core.dto.PaymentDto;
import com.payments.core.model.Payment;
import com.payments.infrastructure.PaymentMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentMapperTest {

    @Test
    void testPaymentMapper() {
        String name = "testname";
        BigDecimal amount = BigDecimal.valueOf(100);
        String currency = "usd";
        Long targetBankAccountNumber = Long.valueOf(10);
        Long userId = Long.valueOf(1);

        PaymentDto paymentDto = PaymentDto
                .builder()
                .name(name)
                .amount(amount)
                .currency(currency)
                .targetBankAccountNumber(targetBankAccountNumber)
                .userId(userId)
                .build();

        Payment payment = PaymentMapper.mapToPayment(paymentDto);

        assertThat(payment.getName()).isEqualTo(name);
        assertThat(payment.getAmount()).isEqualTo(amount.abs());
        assertThat(payment.getCurrency()).isEqualTo(currency);
        assertThat(payment.getTargetBankAccountNumber()).isEqualTo(Long.valueOf(targetBankAccountNumber));
        assertThat(payment.getUserId()).isEqualTo(Long.valueOf(userId));
    }
}
