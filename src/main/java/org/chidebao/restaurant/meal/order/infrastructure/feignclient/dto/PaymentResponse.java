package org.chidebao.restaurant.meal.order.infrastructure.feignclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    public static final String SUCCESS_CODE = "0";
    public static final String INSUFFICIENT_BALANCE_CODE = "1001";

    private String code;

    private String message;
}
