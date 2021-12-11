package org.chidebao.restaurant.meal.order.infrastructure.messageproducer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class MQProducerConfig {

    @Bean
    public MQProducer producer() {
        DefaultMQProducer producer = new DefaultMQProducer();
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
