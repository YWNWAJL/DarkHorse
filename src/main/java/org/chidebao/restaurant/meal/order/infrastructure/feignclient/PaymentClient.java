package org.chidebao.restaurant.meal.order.infrastructure.feignclient;

import org.chidebao.restaurant.meal.order.infrastructure.feignclient.dto.PaymentRequest;
import org.chidebao.restaurant.meal.order.infrastructure.feignclient.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentClient {

    @Autowired
    private PaymentFeignClient paymentFeignClient;

    public PaymentResponse pay(PaymentRequest request) {
        return paymentFeignClient.pay(request);
    }
}
