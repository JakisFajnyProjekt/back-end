package com.pl.seeder;

import com.github.javafaker.Faker;
import com.pl.model.*;
import com.pl.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "spring.jpa.hibernate.ddl-auto", havingValue = "create")
public class DataGenerator {

    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final RestaurantDishRepository restaurantDishRepository;

    public DataGenerator(DishRepository dishRepository, OrderRepository orderRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, RestaurantDishRepository restaurantDishRepository) {
        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.restaurantDishRepository = restaurantDishRepository;
    }

    @PostConstruct
    public void generateFakeData() {
        Faker faker = new Faker();

        // Generate and save users
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            // Set other user properties
            userRepository.save(user);
        }

        // Generate and save restaurants
        for (int i = 0; i < 5; i++) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(faker.company().name());
            restaurant.setAddress(faker.address().fullAddress());
            // Set other restaurant properties
            restaurantRepository.save(restaurant);
        }

        // Generate and save dishes
        for (int i = 0; i < 20; i++) {
            Dish dish = new Dish();
            dish.setName(faker.food().dish());
            dish.setDescription(faker.lorem().sentence());
            // Set other dish properties
            dishRepository.save(dish);
        }

        // Create associations (populating the pivot table)
        List<Dish> allDishes = dishRepository.findAll();
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        List<RestaurantDish> restaurantsDishes = new ArrayList<>();
        for (Dish dish : allDishes) {
            for (Restaurant restaurant : allRestaurants) {
                RestaurantDish association = new RestaurantDish();
                association.setDish(dish);
                association.setRestaurant(restaurant);
                association.setPrice(new BigDecimal(faker.number().numberBetween(20, 100) ));
                restaurantsDishes.add(association);
            }
        }
        restaurantDishRepository.saveAll(restaurantsDishes);

        // Generate and save orders
        for (int i = 0; i < 30; i++) {
            Order order = new Order();
            User randomUser = userRepository.findById(faker.number().numberBetween(1L, 10L)).orElse(null);
            Restaurant randomRestaurant = restaurantRepository.findById(faker.number().numberBetween(1L, 5L)).orElse(null);
            order.setUser(randomUser);
            order.setRestaurant(randomRestaurant);
            order.setIsCompleted(faker.bool().bool());
            order.setPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 5, 50)));
            // Set other order properties
            orderRepository.save(order);
        }
    }
}
