package org.chidebao.restaurant.meal.order.service.model;

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
public class OrderMealDeliveryRequestResult {

    public static final String SUCCESS_RESULT = "SUCCESS";
    public static final String ORDER_NOT_FOUND_RESULT = "ORDER_NOT_FOUND";
    public static final String ORDER_CANCELLED_RESULT = "ORDER_CANCELLED";

    private String result;

    public static OrderMealDeliveryRequestResult success() {
        return OrderMealDeliveryRequestResult.builder().result(SUCCESS_RESULT).build();
    }

    public static OrderMealDeliveryRequestResult orderNotFound() {
        return OrderMealDeliveryRequestResult.builder().result(ORDER_NOT_FOUND_RESULT).build();
    }

    public static OrderMealDeliveryRequestResult orderCancelled() {
        return OrderMealDeliveryRequestResult.builder().result(ORDER_CANCELLED_RESULT).build();
    }
}
