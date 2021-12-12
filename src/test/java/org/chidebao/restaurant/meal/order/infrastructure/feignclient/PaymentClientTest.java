package org.chidebao.restaurant.meal.order.infrastructure.feignclient;

import org.chidebao.restaurant.meal.order.BaseUnitTest;
import org.chidebao.restaurant.meal.order.infrastructure.feignclient.dto.PaymentRequest;
import org.chidebao.restaurant.meal.order.infrastructure.feignclient.dto.PaymentResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PaymentClientTest extends BaseUnitTest {

    @InjectMocks
    private PaymentClient paymentClient;

    @Mock
    private PaymentFeignClient paymentFeignClient;

    @Test
    void should_return_success_when_invoke_payment_api_given_return_success() {
        when(paymentFeignClient.pay(any())).thenReturn(PaymentResponse.builder().code("0").build());

        PaymentResponse paymentResponse = paymentClient.pay(PaymentRequest.from("1001", new BigDecimal("50.00")));

        assertThat(paymentResponse.getCode(), is("0"));
    }

    @Test
    void should_return_insufficient_balance_when_invoke_payment_api_given_return_insufficient_balance() {
        when(paymentFeignClient.pay(any())).thenReturn(PaymentResponse.builder().code("1001").message("payer余额不足").build());

        PaymentResponse paymentResponse = paymentClient.pay(PaymentRequest.from("2002", new BigDecimal("50.00")));

        assertThat(paymentResponse.getCode(), is("1001"));
        assertThat(paymentResponse.getMessage(), is("payer余额不足"));
    }

    @Test
    void should_return_success_when_invoke_payment_api_given_return_success_with_attention() {
        when(paymentFeignClient.pay(any())).thenReturn(PaymentResponse.builder().code("0").message("payer余额小于100元").build());

        PaymentResponse paymentResponse = paymentClient.pay(PaymentRequest.from("2010", new BigDecimal("100.00")));

        assertThat(paymentResponse.getCode(), is("0"));
        assertThat(paymentResponse.getMessage(), is("payer余额小于100元"));
    }

}