package com.payments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Payment app integration test")
@Tag("integration-test")
@SpringBootTest
public class PaymentAppIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void shouldInit() {
        assertThat(applicationContext).isNotNull();
        assertThat(applicationContext.getEnvironment().getProperty("spring.application.name")).isEqualTo("payments-app");
        assertThat(applicationContext.getBeanDefinitionNames()).containsAll(Arrays.asList("paymentController", "paymentOperationsOutAdapter", "paymentOperationsFacade"));
    }
}
