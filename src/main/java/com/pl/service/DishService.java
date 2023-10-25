package com.pl.service;

import com.pl.exception.InvalidValuesException;
import com.pl.exception.NotFoundException;
import com.pl.mapper.DishMapper;
import com.pl.model.Dish;
import com.pl.model.Restaurant;
import com.pl.model.dto.DishDTO;
import com.pl.repository.DishRepository;
import com.pl.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DishService extends AbstractService<DishRepository, Dish> {
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final RestaurantRepository restaurantRepository;


    public DishService(DishRepository dishRepository, DishMapper dishMapper, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
        this.restaurantRepository = restaurantRepository;
    }

    public DishDTO getDishById(Long dishId) {
        Dish dish = getDishIfExist(dishId);
        LOGGER.info("Dish found with id" + dishId);
        return dishMapper.mapToDishDto(dish);
    }

    public List<DishDTO> listDishes() {
        List<Dish> dishList = dishRepository.findAll();
        if (dishList.isEmpty()) {
            LOGGER.info("List dish list are empty");
            return new ArrayList<>();
        }
        return dishMapper.mapToListDto(dishList);
    }

    @Transactional
    public void removeDish(Long dishId) {
        Dish userById = getDishIfExist(dishId);
        dishRepository.delete(userById);
        LOGGER.info("Dish with id " + dishId + " deleted");
    }

    @Transactional
    public void editDish(long dishId, DishDTO dishDTO) {
        dishRepository.findById(dishId)
                .map(existingUser -> {
                    Optional.ofNullable(dishDTO.name())
                            .ifPresent(existingUser::setName);
                    Optional.ofNullable(dishDTO.description())
                            .ifPresent(existingUser::setDescription);
                    LOGGER.info("Changes are accepted");
                    Dish savedUser = dishRepository.save(existingUser);
                    return dishMapper.mapToDishDto(savedUser);
                }).orElseThrow(() -> {
                    LOGGER.error("Wrong dish id");
                    throw new NotFoundException("Dish Not found");
                });
    }

    @Transactional
    public DishDTO createDish(DishDTO dishDTO) {
        if (dishDTO == null) {
            throw new InvalidValuesException("The dish DTO cannot be null.");
        }
        Dish newDish = createDishObject(dishDTO);
        try {
            Dish savedDish = dishRepository.save(newDish);
            Optional<DishDTO> dtoOptional = Optional.ofNullable(dishMapper.mapToDishDto(savedDish));

            if (dtoOptional.isEmpty()) {
                LOGGER.error("Failed to map dish to dish DTO");
                throw new NotFoundException("Failed to map dish to dish DTO");
            }
            LOGGER.info("Dish added with id" + savedDish.getId());
            return dtoOptional.get();
        } catch (Exception e) {
            LOGGER.error("Failed to create dish", e);
            throw new RuntimeException("Failed to create dish", e);
        }
    }

    private Dish createDishObject(DishDTO dishDTO) {
        Dish newDish = new Dish();
        try {
            String name = Objects
                    .requireNonNull(dishDTO.name(), "name of your dish must not be null");
            String description = Objects
                    .requireNonNull(dishDTO.description(), "description of your dish must not be null");
            BigDecimal price = Objects
                    .requireNonNull(dishDTO.price());
            Optional<Restaurant> findRestaurantInDb = Objects
                    .requireNonNull(restaurantRepository.findById(dishDTO.restaurantId()),"restaurant must not be null");
            newDish.setName(name);
            newDish.setDescription(description);
            newDish.setPrice(price);
            newDish.setRestaurant(findRestaurantInDb
                    .orElseThrow(()-> {
                        throw new NotFoundException("Restaurant Not Found");
                    }));
        } catch (NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new NullPointerException("the dish name, description, price and restaurant cannot be empty.");
        }
        LOGGER.info("Dish object created");
        return newDish;
    }

    private Dish getDishIfExist(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> {
                    LOGGER.error("Dish with given id not found");
                    throw new  NotFoundException("Dish not found with given id " + dishId);
                });
    }
}
