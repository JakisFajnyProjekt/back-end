package com.pl.repository;

import com.pl.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Long, Restaurant> {
    Optional<Restaurant> findById(Long id);
}
