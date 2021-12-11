package org.chidebao.restaurant.meal.order.infrastructure.repository;

import org.chidebao.restaurant.meal.order.BaseApplicationTest;
import org.chidebao.restaurant.meal.order.infrastructure.repository.entity.OrderEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class OrderRepositoryTest extends BaseApplicationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void should_find_order_by_id() {
        orderRepository.save(OrderEntity.builder().id("1").build());

        Optional<OrderEntity> orderEntityOptional = orderRepository.findById("1");

        assertThat(orderEntityOptional.isPresent(), is(true));
        assertThat(orderEntityOptional.get().getId(), is("1"));
    }

    @Test
    void should_find_optional_empty_by_id() {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById("3");

        assertThat(orderEntityOptional.isPresent(), is(false));
    }

    @Test
    void should_update_delivery_status_by_id() {
        orderRepository.save(OrderEntity.builder().id("1").deliveryStatus("NONE").build());

        orderRepository.save(OrderEntity.builder().id("1").deliveryStatus("REQUEST").build());

        Optional<OrderEntity> orderEntityOptional = orderRepository.findById("1");
        assertThat(orderEntityOptional.isPresent(), is(true));
        assertThat(orderEntityOptional.get().getDeliveryStatus(), is("REQUEST"));
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }
}