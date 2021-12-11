package org.chidebao.restaurant.meal.order.service;

import org.apache.commons.lang3.StringUtils;
import org.chidebao.restaurant.meal.order.infrastructure.feignclient.PaymentClient;
import org.chidebao.restaurant.meal.order.infrastructure.feignclient.dto.PaymentRequest;
import org.chidebao.restaurant.meal.order.infrastructure.feignclient.dto.PaymentResponse;
import org.chidebao.restaurant.meal.order.infrastructure.messageproducer.DeliveryMQProducer;
import org.chidebao.restaurant.meal.order.infrastructure.messageproducer.message.DeliveryRequestMassage;
import org.chidebao.restaurant.meal.order.infrastructure.repository.OrderRepository;
import org.chidebao.restaurant.meal.order.infrastructure.repository.OrderSettlementConfirmationRepository;
import org.chidebao.restaurant.meal.order.infrastructure.repository.entity.OrderSettlementConfirmationEntity;
import org.chidebao.restaurant.meal.order.service.model.OrderMealDeliveryRequestResult;
import org.chidebao.restaurant.meal.order.service.model.OrderSettleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderSettlementConfirmationRepository orderSettlementConfirmationRepository;

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private DeliveryMQProducer deliveryMQProducer;

    public OrderSettleResult settleOrder(String orderId) {
        return orderRepository.findById(orderId).map(order -> {
            try {
                PaymentResponse paymentResponse = paymentClient.pay(
                        PaymentRequest.from(order.getRestaurantPayAccount(), order.getAmount())
                );
                orderSettlementConfirmationRepository.save(OrderSettlementConfirmationEntity.from(orderId));
                return OrderSettleResult.from(paymentResponse);
            } catch (Exception e) {
                return OrderSettleResult.paymentServiceUnavailable();
            }
        }).orElseGet(OrderSettleResult::orderNotFound);
    }

    public OrderMealDeliveryRequestResult orderMealDeliveryRequest(String orderId) {
        return orderRepository.findById(orderId).map(order -> {
            if (StringUtils.equalsIgnoreCase(order.getCancelStatus(), "CANCELLED")) {
                return OrderMealDeliveryRequestResult.orderCancelled();
            }
            deliveryMQProducer.sendDeliveryRequestMessage(
                    DeliveryRequestMassage.from(order.getRestaurantAddress(), order.getCustomerAddress())
            );
            order.setDeliveryStatus("REQUEST");
            orderRepository.save(order);
            return OrderMealDeliveryRequestResult.success();

        }).orElseGet(OrderMealDeliveryRequestResult::orderNotFound);
    }
}
