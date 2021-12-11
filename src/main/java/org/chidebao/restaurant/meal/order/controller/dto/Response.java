package org.chidebao.restaurant.meal.order.controller.dto;

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
public class Response {

    public static final String SETTLE_SUCCESS_MESSAGE = "结算成功";
    public static final String REQUEST_SUCCESS_MESSAGE = "请求成功";
    public static final String ORDER_NOT_FOUND_MESSAGE = "订单不存在";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "余额不足";
    public static final String PAYMENT_SERVICE_UNAVAILABLE_MESSAGE = "支付服务不可用";
    public static final String ORDER_CANCELED_MESSAGE = "订单已经取消，无法申请配送";

    private String code;

    private String message;

    public static Response from(String code, String message) {
        return Response.builder().code(code).message(message).build();
    }
}
