package org.chidebao.restaurant.meal.order.controller;

import org.chidebao.restaurant.meal.order.controller.dto.Response;
import org.chidebao.restaurant.meal.order.service.OrderService;
import org.chidebao.restaurant.meal.order.service.model.OrderMealDeliveryRequestResult;
import org.chidebao.restaurant.meal.order.service.model.OrderSettleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{oid}/settlement-confirmation")
    public ResponseEntity<Response> settleOrder(@PathVariable("oid") String orderId) {
        OrderSettleResult orderSettleResult = orderService.settleOrder(orderId);
        switch (orderSettleResult.getResult()) {
            case OrderSettleResult.SUCCESS_RESULT:
                return ResponseEntity.ok(Response.from(orderSettleResult.getResult(), Response.SETTLE_SUCCESS_MESSAGE));
            case OrderSettleResult.ORDER_NOT_FOUND_RESULT:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.from(orderSettleResult.getResult(), Response.ORDER_NOT_FOUND_MESSAGE));
            case OrderSettleResult.INSUFFICIENT_BALANCE_RESULT:
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Response.from(orderSettleResult.getResult(), Response.INSUFFICIENT_BALANCE_MESSAGE));
            case OrderSettleResult.PAYMENT_SERVICE_UNAVAILABLE_RESULT:
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Response.from(orderSettleResult.getResult(), Response.PAYMENT_SERVICE_UNAVAILABLE_MESSAGE));
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ResponseBody
    @PostMapping("/{oid}/meal-delivery-request")
    public ResponseEntity<Response> orderMealDelivery(@PathVariable("oid") String orderId) {
        OrderMealDeliveryRequestResult orderMealDeliveryRequestResult = orderService.orderMealDeliveryRequest(orderId);
        switch (orderMealDeliveryRequestResult.getResult()) {
            case OrderMealDeliveryRequestResult.SUCCESS_RESULT:
                return ResponseEntity.ok(Response.from(orderMealDeliveryRequestResult.getResult(), Response.REQUEST_SUCCESS_MESSAGE));
            case OrderMealDeliveryRequestResult.ORDER_NOT_FOUND_RESULT:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.from(orderMealDeliveryRequestResult.getResult(), Response.ORDER_NOT_FOUND_MESSAGE));
            case OrderMealDeliveryRequestResult.ORDER_CANCELLED_RESULT:
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Response.from(orderMealDeliveryRequestResult.getResult(), Response.ORDER_CANCELED_MESSAGE));
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
