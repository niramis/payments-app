package com.payments.integration;

import com.google.gson.Gson;
import com.payments.application.PaymentController;
import com.payments.core.dto.PaymentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
//@EnableWebMvc
public class PaymentControllerTest {

    MockMvc mockMvc;

    @Autowired
    PaymentController paymentController;

    @BeforeEach
    void init() {
//        paymentController = new PaymentController(new PaymentUseCaseMock());
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    void shouldInitializeController() {
        assertThat(paymentController).isNotNull();
    }

    @Test
    public void shouldGetPaymentListAndStatusOk() throws Exception {
        this.mockMvc.perform(get("/payments")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldCreate() throws Exception {
        PaymentDto paymentDto = PaymentDto
                .builder()
                .name("testName")
                .currency("usd")
                .amount(BigDecimal.valueOf(10))
                .userId(Long.valueOf(1))
                .targetBankAccountNumber(Long.valueOf(2))
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(paymentDto);

        this.mockMvc.perform(post("/payments")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.currency").value("usd"))
                .andExpect(jsonPath("$.amount").value(10))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.targetBankAccountNumber").value(2));
    }

    @Test
    public void shouldUpdate() throws Exception {

        PaymentDto paymentDto = PaymentDto
                .builder()
                .name("testName")
                .currency("usd")
                .amount(BigDecimal.valueOf(10))
                .userId(Long.valueOf(1))
                .targetBankAccountNumber(Long.valueOf(2))
                .build();
        Gson gson = new Gson();

        String json = gson.toJson(paymentDto);

        this.mockMvc.perform(post("/payments")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json));

        this.mockMvc.perform(put("/payments/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.currency").value("usd"))
                .andExpect(jsonPath("$.amount").value(10))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.targetBankAccountNumber").value(2));
    }

    @Test
    public void shouldDelete() throws Exception {
        this.mockMvc.perform(delete("/payments/1")).andExpect(status().isNoContent());
    }

}
