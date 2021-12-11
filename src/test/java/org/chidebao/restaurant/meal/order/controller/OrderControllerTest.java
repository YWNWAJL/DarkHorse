package org.chidebao.restaurant.meal.order.controller;

import org.chidebao.restaurant.meal.order.BaseApplicationTest;
import org.chidebao.restaurant.meal.order.service.OrderService;
import org.chidebao.restaurant.meal.order.service.model.OrderMealDeliveryRequestResult;
import org.chidebao.restaurant.meal.order.service.model.OrderSettleResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends BaseApplicationTest {

    @MockBean
    private OrderService orderService;

    @Test
    void should_return_success_when_settle_order_given_success_result() throws Exception {

        when(orderService.settleOrder("1")).thenReturn(OrderSettleResult.builder().result("SUCCESS").build());

        mockMvc.perform(post("/restaurant/orders/1/settlement-confirmation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("SUCCESS")))
                .andExpect(jsonPath("$.message", is("结算成功")));
    }

    @Test
    void should_return_insufficient_balance_when_settle_order_given_insufficient_balance_result() throws Exception {

        when(orderService.settleOrder("2")).thenReturn(OrderSettleResult.builder().result("INSUFFICIENT_BALANCE").build());

        mockMvc.perform(post("/restaurant/orders/2/settlement-confirmation"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", is("INSUFFICIENT_BALANCE")))
                .andExpect(jsonPath("$.message", is("余额不足")));
    }

    @Test
    void should_return_not_found_when_settle_order_given_not_found_result() throws Exception {

        when(orderService.settleOrder("3")).thenReturn(OrderSettleResult.builder().result("ORDER_NOT_FOUND").build());

        mockMvc.perform(post("/restaurant/orders/3/settlement-confirmation"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is("ORDER_NOT_FOUND")))
                .andExpect(jsonPath("$.message", is("订单不存在")));
    }

    @Test
    void should_return_payment_service_unavailable_when_settle_order_given_payment_service_unavailable_result() throws Exception {

        when(orderService.settleOrder("4")).thenReturn(OrderSettleResult.builder().result("PAYMENT_SERVICE_UNAVAILABLE").build());

        mockMvc.perform(post("/restaurant/orders/4/settlement-confirmation"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.code", is("PAYMENT_SERVICE_UNAVAILABLE")))
                .andExpect(jsonPath("$.message", is("支付服务不可用")));
    }

    @Test
    void should_return_success_when_request_meal_delivery_given_success_result() throws Exception {

        when(orderService.orderMealDeliveryRequest("1")).thenReturn(OrderMealDeliveryRequestResult.builder().result("SUCCESS").build());

        mockMvc.perform(post("/restaurant/orders/1/meal-delivery-request"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("SUCCESS")))
                .andExpect(jsonPath("$.message", is("请求成功")));
    }

    @Test
    void should_return_order_cancelled_when_request_meal_delivery_order_cancelled_result() throws Exception {

        when(orderService.orderMealDeliveryRequest("2")).thenReturn(OrderMealDeliveryRequestResult.builder().result("ORDER_CANCELLED").build());

        mockMvc.perform(post("/restaurant/orders/2/meal-delivery-request"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", is("ORDER_CANCELLED")))
                .andExpect(jsonPath("$.message", is("订单已经取消，无法申请配送")));
    }

    @Test
    void should_return_not_found_when_request_meal_delivery_given_not_found_result() throws Exception {

        when(orderService.orderMealDeliveryRequest("3")).thenReturn(OrderMealDeliveryRequestResult.builder().result("ORDER_NOT_FOUND").build());

        mockMvc.perform(post("/restaurant/orders/3/meal-delivery-request"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is("ORDER_NOT_FOUND")))
                .andExpect(jsonPath("$.message", is("订单不存在")));
    }

    @Test
    void should_return_success_when_request_meal_delivery_given_success_result_but_delivery_unavailable() throws Exception {

        when(orderService.orderMealDeliveryRequest("4")).thenReturn(OrderMealDeliveryRequestResult.builder().result("SUCCESS").build());

        mockMvc.perform(post("/restaurant/orders/4/meal-delivery-request"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("SUCCESS")))
                .andExpect(jsonPath("$.message", is("请求成功")));
    }
}