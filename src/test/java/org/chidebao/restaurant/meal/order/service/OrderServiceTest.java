package org.chidebao.restaurant.meal.order.service;

import org.chidebao.restaurant.meal.order.BaseUnitTest;
import org.chidebao.restaurant.meal.order.infrastructure.feignclient.PaymentClient;
import org.chidebao.restaurant.meal.order.infrastructure.feignclient.dto.PaymentResponse;
import org.chidebao.restaurant.meal.order.infrastructure.messageproducer.DeliveryMQProducer;
import org.chidebao.restaurant.meal.order.infrastructure.repository.OrderRepository;
import org.chidebao.restaurant.meal.order.infrastructure.repository.OrderSettlementConfirmationRepository;
import org.chidebao.restaurant.meal.order.infrastructure.repository.entity.OrderEntity;
import org.chidebao.restaurant.meal.order.service.model.OrderMealDeliveryRequestResult;
import org.chidebao.restaurant.meal.order.service.model.OrderSettleResult;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrderServiceTest extends BaseUnitTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderSettlementConfirmationRepository orderSettlementConfirmationRepository;

    @Mock
    private PaymentClient paymentClient;

    @Mock
    private DeliveryMQProducer deliveryMQProducer;

    @Test
    void should_return_success_when_settle_order_given_payment_success_result() {
        when(orderRepository.findById("1")).thenReturn(Optional.of(
                OrderEntity.builder().id("1").restaurantPayAccount("2001").amount(new BigDecimal("50.00")).build()));
        when(paymentClient.pay(any())).thenReturn(PaymentResponse.builder().code("0").build());

        OrderSettleResult orderSettleResult = orderService.settleOrder("1");

        assertThat(orderSettleResult.getResult(), is("SUCCESS"));
    }

    @Test
    void should_return_insufficient_balance_when_settle_order_given_payment_fail_due_to_insufficient_balance() {
        when(orderRepository.findById("2")).thenReturn(Optional.of(
                OrderEntity.builder().id("2").restaurantPayAccount("2002").amount(new BigDecimal("50.00")).build()));
        when(paymentClient.pay(any())).thenReturn(PaymentResponse.builder().code("1001").message("payer余额不足").build());

        OrderSettleResult orderSettleResult = orderService.settleOrder("2");

        assertThat(orderSettleResult.getResult(), is("INSUFFICIENT_BALANCE"));
    }

    @Test
    void should_return_not_found_when_settle_order_given_order_not_found() {
        when(orderRepository.findById("3")).thenReturn(Optional.empty());

        OrderSettleResult orderSettleResult = orderService.settleOrder("3");

        assertThat(orderSettleResult.getResult(), is("ORDER_NOT_FOUND"));
    }

    @Test
    void should_return_payment_service_unavailable_when_settle_order_given_payment_service_unavailable() {
        when(orderRepository.findById("4")).thenReturn(Optional.of(
                OrderEntity.builder().id("4").restaurantPayAccount("2004").amount(new BigDecimal("50.00")).build()));
        when(paymentClient.pay(any())).thenThrow(new RuntimeException());

        OrderSettleResult orderSettleResult = orderService.settleOrder("4");

        assertThat(orderSettleResult.getResult(), is("PAYMENT_SERVICE_UNAVAILABLE"));
    }

    @Test
    void should_return_success_when_request_meal_delivery_given_send_message_success() {
        when(orderRepository.findById("1")).thenReturn(Optional.of(
                OrderEntity.builder().id("1").restaurantAddress("餐厅地址").customerAddress("顾客地址").cancelStatus("NONE").build()));
        when(deliveryMQProducer.sendDeliveryRequestMessage(any())).thenReturn(true);

        OrderMealDeliveryRequestResult orderMealDeliveryRequestResult = orderService.orderMealDeliveryRequest("1");

        assertThat(orderMealDeliveryRequestResult.getResult(), is("SUCCESS"));
    }

    @Test
    void should_return_order_cancelled_when_request_meal_delivery_given_order_is_cancelled() {
        when(orderRepository.findById("2")).thenReturn(Optional.of(
                OrderEntity.builder().id("2").restaurantAddress("餐厅地址").customerAddress("顾客地址").cancelStatus("CANCELLED").build()));

        OrderMealDeliveryRequestResult orderMealDeliveryRequestResult = orderService.orderMealDeliveryRequest("2");

        assertThat(orderMealDeliveryRequestResult.getResult(), is("ORDER_CANCELLED"));
    }

    @Test
    void should_return_order_cancelled_when_request_meal_delivery_given_order_is_request_to_cancel() {
        when(orderRepository.findById("10")).thenReturn(Optional.of(
                OrderEntity.builder().id("10").restaurantAddress("餐厅地址").customerAddress("顾客地址").cancelStatus("REQUEST").build()));

        OrderMealDeliveryRequestResult orderMealDeliveryRequestResult = orderService.orderMealDeliveryRequest("10");

        assertThat(orderMealDeliveryRequestResult.getResult(), is("ORDER_CANCELLED"));
    }

    @Test
    void should_return_not_found_when_request_meal_delivery_given_order_not_found() {
        when(orderRepository.findById("3")).thenReturn(Optional.empty());

        OrderMealDeliveryRequestResult orderMealDeliveryRequestResult = orderService.orderMealDeliveryRequest("3");

        assertThat(orderMealDeliveryRequestResult.getResult(), is("ORDER_NOT_FOUND"));

    }
    @Test
    void should_return_success_when_request_meal_delivery_given_send_message_success_but_delivery_unavailable() {
        when(orderRepository.findById("4")).thenReturn(Optional.of(
                OrderEntity.builder().id("4").restaurantAddress("餐厅地址").customerAddress("顾客地址").cancelStatus("NONE").build()));
        when(deliveryMQProducer.sendDeliveryRequestMessage(any())).thenReturn(true);

        OrderMealDeliveryRequestResult orderMealDeliveryRequestResult = orderService.orderMealDeliveryRequest("4");

        assertThat(orderMealDeliveryRequestResult.getResult(), is("SUCCESS"));

    }


}