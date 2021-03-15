package com.payments.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class PaymentDto {

    @NotBlank
    private String name;
    @NotNull
    @Min(value = 0L)
    private BigDecimal amount;
    @NotNull
    @Min(value = 0L)
    private Long userId;
    @NotBlank
    private String currency;
    @NotNull
    @Min(value = 0L)
    private Long targetBankAccountNumber;
}
