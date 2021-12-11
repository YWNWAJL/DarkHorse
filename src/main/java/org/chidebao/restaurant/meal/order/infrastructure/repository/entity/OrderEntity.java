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
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant_order")
public class OrderEntity {

    @Id
    private String id;

    private BigDecimal amount;

    private String restaurantPayAccount;

    private String cancelStatus;

    private String deliveryStatus;

    private String restaurantAddress;

    private String customerAddress;
}
