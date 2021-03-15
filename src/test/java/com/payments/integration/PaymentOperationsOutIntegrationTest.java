package com.payments.integration;

import com.payments.core.model.Payment;
import com.payments.core.ports.out.PaymentOperationsOut;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("integration-test")
public class PaymentOperationsOutIntegrationTest {

    @Autowired
    PaymentOperationsOut paymentOperationsOut;

    @Test
    void shouldFetch() {
        List<Payment> payments = paymentOperationsOut.getPayments();
        assertThat(payments).isNotNull();
    }

}
