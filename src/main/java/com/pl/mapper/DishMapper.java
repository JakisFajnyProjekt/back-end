package com.pl.mapper;

import com.pl.model.Dish;
import com.pl.model.dto.DishDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DishMapper {



    public Dish mapToDish(DishDTO dishDTO){
        return new Dish(dishDTO.id(),
                dishDTO.name(),
                dishDTO.description()
        );
    }

    public DishDTO mapToDishDto(Dish dish){
        return new DishDTO(dish.getId(),
                dish.getName(),
                dish.getDescription());
    }

    public List<DishDTO> mapToListDto(final List<Dish> dishes){
        return dishes.stream()
                .map(this::mapToDishDto)
                .toList();
    }
}
