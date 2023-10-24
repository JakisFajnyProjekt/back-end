package com.pl.seeder;

import com.github.javafaker.Faker;
import com.pl.model.Dish;
import com.pl.model.Restaurant;
import com.pl.model.User;
import com.pl.repository.DishRepository;
import com.pl.repository.OrderRepository;
import com.pl.repository.RestaurantRepository;
import com.pl.repository.UserRepository;
import com.pl.security.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnProperty(name = "spring.jpa.hibernate.ddl-auto", havingValue = "create")
public class DataGenerator {

    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public DataGenerator(DishRepository dishRepository, OrderRepository orderRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
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
            user.setPassword(faker.internet().password());
            user.setRole(Role.USER);
            // Set other user properties
            userRepository.save(user);
        }
        userRepository.save(new User("Jan", "Kowalski", "admin@gmail.com", "qwe123", Role.ADMIN));
        userRepository.save(new User("Stachu", "Staszewski", "user@gmail.com", "qwe123", Role.USER));

        // Generate and save restaurants
        for (int i = 0; i < 5; i++) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(faker.company().name());

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

//        // Create associations (populating the pivot table)
//        List<Dish> allDishes = dishRepository.findAll();
//        List<Restaurant> allRestaurants = restaurantRepository.findAll();
//        List<RestaurantDish> restaurantsDishes = new ArrayList<>();
//        for (Dish dish : allDishes) {
//            for (Restaurant restaurant : allRestaurants) {
//                RestaurantDish association = new RestaurantDish();
//                association.setDish(dish);
//                association.setRestaurant(restaurant);
//                association.setPrice(new BigDecimal(faker.number().numberBetween(20, 100) ));
//                restaurantsDishes.add(association);
//            }
//        }
//        restaurantDishRepository.saveAll(restaurantsDishes);
//
//        // Generate and save orders
//        for (int i = 0; i < 30; i++) {
//            Order order = new Order();
//            User randomUser = userRepository.findById(faker.number().numberBetween(1L, 10L)).orElse(null);
//            Restaurant randomRestaurant = restaurantRepository.findById(faker.number().numberBetween(1L, 5L)).orElse(null);
//            order.setUser(randomUser);
//            order.setRestaurant(randomRestaurant);
//            order.setIsCompleted(faker.bool().bool());
//
//            order.setTotalCost(BigDecimal.valueOf(faker.number().randomDouble(2, 5, 50)));
//
//            // Set other order properties
//            orderRepository.save(order);
//        }
    }
}
