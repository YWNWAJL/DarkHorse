package org.chidebao.restaurant.meal.order.infrastructure.feignclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private static final String CHIDEBAO_ACCOUNT = "1001";

    private String payer;

    private String payee;

    private BigDecimal amount;

    public static PaymentRequest from(String payee, BigDecimal amount) {
        return PaymentRequest.builder()
                .payer(CHIDEBAO_ACCOUNT)
                .payee(payee)
                .amount(amount)
                .build();
    }
}
