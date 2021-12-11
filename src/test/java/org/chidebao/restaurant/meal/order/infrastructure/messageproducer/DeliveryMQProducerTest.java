package org.chidebao.restaurant.meal.order.infrastructure.messageproducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.chidebao.restaurant.meal.order.BaseUnitTest;
import org.chidebao.restaurant.meal.order.infrastructure.messageproducer.message.DeliveryRequestMassage;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeliveryMQProducerTest extends BaseUnitTest {

    @InjectMocks
    private DeliveryMQProducer deliveryMQProducer;

    @Mock
    private MQProducer producer;

    @Captor
    private ArgumentCaptor<Message> messageArgumentCaptor;

    @Test
    void should_send_message_success() throws MQBrokerException, RemotingException, InterruptedException, MQClientException, JsonProcessingException {
        SendResult sendResult = new SendResult();
        sendResult.setSendStatus(SendStatus.SEND_OK);
        when(producer.send(any(Message.class))).thenReturn(sendResult);

        boolean success = deliveryMQProducer.sendDeliveryRequestMessage(DeliveryRequestMassage.from("餐厅地址", "顾客地址"));

        assertThat(success, is(true));
        verify(producer).send(messageArgumentCaptor.capture());
        assertThat(messageArgumentCaptor.getValue().getTopic(), is("DELIVERY_TOPIC"));
        assertThat(messageArgumentCaptor.getValue().getBody(), is(new ObjectMapper().writeValueAsBytes(DeliveryRequestMassage.from("餐厅地址", "顾客地址"))));
    }

}