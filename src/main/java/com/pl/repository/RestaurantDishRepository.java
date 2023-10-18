package com.pl.repository;

import com.pl.model.RestaurantDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantDishRepository extends JpaRepository<RestaurantDish, Long> {
}
