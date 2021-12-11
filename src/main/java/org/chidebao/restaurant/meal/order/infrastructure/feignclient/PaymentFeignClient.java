package org.chidebao.restaurant.meal.order.infrastructure.feignclient;

import org.chidebao.restaurant.meal.order.infrastructure.feignclient.dto.PaymentRequest;
import org.chidebao.restaurant.meal.order.infrastructure.feignclient.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment")
public interface PaymentFeignClient {

    @PostMapping("/payment")
    PaymentResponse pay(PaymentRequest request);
}
