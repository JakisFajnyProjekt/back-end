package com.pl.service;

import com.pl.exception.InvalidValuesException;
import com.pl.exception.NotFoundException;
import com.pl.mapper.DishMapper;
import com.pl.model.Dish;
import com.pl.model.User;
import com.pl.model.dto.DishDTO;
import com.pl.model.dto.UserDTO;
import com.pl.repository.DishRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DishService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    DishRepository dishRepository;
    DishMapper dishMapper;


    public DishService(DishRepository dishRepository, DishMapper dishMapper) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
    }

    public DishDTO getDishById(Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException("Dish not found with given id " + dishId));
        LOGGER.info("Dish found with id" + dishId);
        return dishMapper.mapToDishDto(dish);
    }

    public List<DishDTO> listDishes() {
        return dishMapper.mapToListDto(dishRepository.findAll());
    }

    public void removeDish(Long dishId) {
        Dish userById = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException("Dish not found with given id " + dishId));
        dishRepository.delete(userById);
        LOGGER.info("User with id " + dishId + " deleted");
    }

    public DishDTO editDish(long dishId, Map<String,Object> update) {
        return dishRepository.findById(dishId)
                .map(existingUser -> {
                        Optional.ofNullable(update.get("name"))
                                .ifPresent(value -> existingUser.setName(value.toString()));
                        Optional.ofNullable(update.get("description"))
                                .ifPresent(value -> existingUser.setDescription(value.toString()));
                        LOGGER.info("Changes are accepted");
                        Dish savedUser = dishRepository.save(existingUser);
                        return dishMapper.mapToDishDto(savedUser);
                }).orElseThrow(() -> {
                        LOGGER.error("Wrong user id");
                        throw new NotFoundException("Dish Not found");
                });
    }

//    public DishDTO createDish(Map<String, Object> update) {
//        if(update.containsKey("name") && update.containsKey("description")) {
//            Dish newDish = new Dish();
//            newDish.setName(update.get("name").toString());
//            newDish.setDescription(update.get("description").toString());
//            dishRepository.save(newDish);
//            return dishMapper.mapToDishDto(newDish);
//        }
//    }

    public DishDTO createDish(Map<String, Object> update) {
        if (update.containsKey("name") && update.containsKey("description")) {
            Dish newDish = createDishFromUpdate(update);
            Dish savedDish = dishRepository.save(newDish);
            return dishMapper.mapToDishDto(savedDish);
        }
        throw new InvalidValuesException("Provided values does not contain name and description properties");
    }

    private Dish createDishFromUpdate(Map<String, Object> update) {
        Dish newDish = new Dish();
        newDish.setName(update.get("name").toString());
        newDish.setDescription(update.get("description").toString());
        return newDish;
    }
}
