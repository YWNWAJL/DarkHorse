package org.chidebao.restaurant.meal.order.infrastructure.repository;

import org.chidebao.restaurant.meal.order.infrastructure.repository.entity.OrderSettlementConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSettlementConfirmationRepository extends JpaRepository<OrderSettlementConfirmationEntity, String> {

}
