package org.chidebao.restaurant.meal.order.infrastructure.repository;

import org.chidebao.restaurant.meal.order.infrastructure.repository.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

}
