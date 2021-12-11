package org.chidebao.restaurant.meal.order.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.chidebao.restaurant.meal.order.infrastructure.feignclient.dto.PaymentResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSettleResult {

    public static final String SUCCESS_RESULT = "SUCCESS";
    public static final String ORDER_NOT_FOUND_RESULT = "ORDER_NOT_FOUND";
    public static final String INSUFFICIENT_BALANCE_RESULT = "INSUFFICIENT_BALANCE";
    public static final String PAYMENT_SERVICE_UNAVAILABLE_RESULT = "PAYMENT_SERVICE_UNAVAILABLE";

    private String result;

    public static OrderSettleResult from(PaymentResponse response) {
        if (StringUtils.equalsIgnoreCase(response.getCode(), PaymentResponse.SUCCESS_CODE)) {
            return OrderSettleResult.builder().result(SUCCESS_RESULT).build();
        }
        return OrderSettleResult.builder().result(INSUFFICIENT_BALANCE_RESULT).build();
    }

    public static OrderSettleResult orderNotFound() {
        return OrderSettleResult.builder().result(ORDER_NOT_FOUND_RESULT).build();
    }

    public static OrderSettleResult paymentServiceUnavailable() {
        return OrderSettleResult.builder().result(PAYMENT_SERVICE_UNAVAILABLE_RESULT).build();
    }
}
