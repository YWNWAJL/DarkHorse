package org.chidebao.restaurant.meal.order.infrastructure.messageproducer.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequestMassage {

    private String restaurantAddress;

    private String customerAddress;

    public static DeliveryRequestMassage from(String restaurantAddress, String customerAddress) {
        return DeliveryRequestMassage.builder()
                .restaurantAddress(restaurantAddress)
                .customerAddress(customerAddress).build();
    }
}
