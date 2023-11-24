package com.pl.repository;

import com.pl.model.Order;
import com.pl.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("SELECT o FROM Order o, Restaurant  r WHERE o.restaurant.id = :restaurantId")
    List<Order> findAllOrdersByRestaurantAndOwner(@Param("restaurantId") long restaurantId);


}
