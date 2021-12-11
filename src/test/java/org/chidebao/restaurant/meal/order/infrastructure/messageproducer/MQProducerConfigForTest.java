package org.chidebao.restaurant.meal.order.infrastructure.messageproducer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Profile("test")
@Configuration
public class MQProducerConfigForTest {

    @Bean
    public MQProducer producer() {
        return mock(DefaultMQProducer.class);
    }
}
