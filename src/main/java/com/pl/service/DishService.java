package com.pl.service;

import com.pl.exception.NotFoudException;
import com.pl.exception.NotFoundException;
import com.pl.mapper.DishMapper;
import com.pl.model.Dish;
import com.pl.model.User;
import com.pl.model.dto.DishDTO;
import com.pl.repository.DishRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

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
                .orElseThrow(() -> new NotFoudException("Dish not found with given id " + dishId));
        dishRepository.delete(userById);
        LOGGER.info("User with id " + dishId + " deleted");
    }

    public void editDish(long dishId, DishDTO updatedDish) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoudException("Dish not found with given id " + dishId));

        if(dish != null) {
            dish.setName(updatedDish.name());
            dish.setDescription(updatedDish.description());
            dishRepository.save(dish);
        }
    }
}
