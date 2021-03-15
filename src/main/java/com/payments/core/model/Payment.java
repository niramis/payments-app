package com.payments.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class Payment {
    private Long id;
    private String name;
    private BigDecimal amount;
    private Long userId;
    private String currency;
    private Long targetBankAccountNumber;
}
