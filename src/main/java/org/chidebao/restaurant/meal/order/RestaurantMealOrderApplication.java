package org.chidebao.restaurant.meal.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RestaurantMealOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantMealOrderApplication.class, args);
    }
}
