package com.pl.seeder;

import com.github.javafaker.Faker;
import com.pl.model.*;
import com.pl.repository.*;
import com.pl.auth.Role;
import com.pl.model.Dish;
import com.pl.model.Restaurant;
import com.pl.model.User;
import com.pl.repository.DishRepository;
import com.pl.repository.OrderRepository;
import com.pl.repository.RestaurantRepository;
import com.pl.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;


@Component
@ConditionalOnProperty(name = "spring.jpa.hibernate.ddl-auto", havingValue = "create")
public class DataGenerator {

    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    public DataGenerator(DishRepository dishRepository, OrderRepository orderRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, AddressRepository addressRepository) {
        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
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
        userRepository.save(new User("Jan", "Kowalski", "admin@gmail.com", passwordEncoder.encode("Qwe123"), Role.ADMIN));
        userRepository.save(new User("Stachu", "Staszewski", "user@gmail.com", passwordEncoder.encode("Qwe123"), Role.USER));

        // Generate and save addresses
        for (User user : userRepository.findAll()) {
            Address address = new Address();
            address.setCity(faker.address().city());
            address.setHouseNumber(faker.address().buildingNumber());
            address.setPostalCode(faker.address().zipCode());
            address.setStreet(faker.address().streetAddress());
            address.setUser( Set.of(user) );
            user.setDeliveryAddresses(Set.of(address));
            addressRepository.save(address);
        }

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
            dish.setDescription(faker.food().ingredient());
            dish.setPrice(new BigDecimal(faker.number().numberBetween(10, 100)));
            dishRepository.save(dish);
        }

        // Generate and save orders
        for (User user : userRepository.findAll()) {
            Order order = new Order();
            order.setUser(user);
            order.setStatus("na chuj to tutaj");
            order.setTotalPrice(new BigDecimal(faker.number().numberBetween(20, 300)));
            orderRepository.save(order);
        }
    }
}
