package org.chidebao.restaurant.meal.order.infrastructure.repository;

import org.chidebao.restaurant.meal.order.BaseApplicationTest;
import org.chidebao.restaurant.meal.order.infrastructure.repository.entity.OrderSettlementConfirmationEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class OrderSettlementConfirmationRepositoryTest extends BaseApplicationTest {

    @Autowired
    private OrderSettlementConfirmationRepository orderSettlementConfirmationRepository;

    @Test
    void should_save_order_settlement_confirmation_success() {
        orderSettlementConfirmationRepository.save(OrderSettlementConfirmationEntity.builder().orderId("1").build());

        assertThat(orderSettlementConfirmationRepository.count(), is(1L));
    }

    @AfterEach
    void tearDown() {
        orderSettlementConfirmationRepository.deleteAll();
    }
}