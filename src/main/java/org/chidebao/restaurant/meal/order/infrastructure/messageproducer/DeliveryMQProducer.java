package org.chidebao.restaurant.meal.order.infrastructure.messageproducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.chidebao.restaurant.meal.order.infrastructure.messageproducer.message.DeliveryRequestMassage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.rocketmq.client.producer.SendStatus.SEND_OK;

@Component
public class DeliveryMQProducer {

    public static final String DELIVERY_TOPIC = "DELIVERY_TOPIC";

    @Autowired
    private MQProducer producer;

    public boolean sendDeliveryRequestMessage(DeliveryRequestMassage message) {
        try {
            SendResult sendResult = producer.send(new Message(DELIVERY_TOPIC, new ObjectMapper().writeValueAsBytes(message)));
            return SEND_OK == sendResult.getSendStatus();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        return false;
    }
}
