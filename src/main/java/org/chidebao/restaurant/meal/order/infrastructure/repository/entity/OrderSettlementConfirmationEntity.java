package org.chidebao.restaurant.meal.order.infrastructure.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_settlement_confirmation")
public class OrderSettlementConfirmationEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String orderId;

    public static OrderSettlementConfirmationEntity from(String orderId) {
        return OrderSettlementConfirmationEntity.builder().orderId(orderId).build();
    }
}
