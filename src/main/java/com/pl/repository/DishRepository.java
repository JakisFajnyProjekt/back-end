package com.pl.repository;

import com.pl.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DishRepository extends JpaRepository<Dish,Long> {

    Set<Dish> findByIdIn(Set<Long> longs);
}
