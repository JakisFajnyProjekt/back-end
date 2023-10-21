package com.pl.model;

import jakarta.persistence.*;

import java.util.Objects;

@Table(name = "restaurants")
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantDish> restaurantDishes;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Restaurant() {
    }

    public Restaurant(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Restaurant(String name) {
        this.name = name;
    }

    public List<RestaurantDish> getRestaurantDishes() {
        return restaurantDishes;
    }

    public void setRestaurantDishes(List<RestaurantDish> restaurantDishes) {
        this.restaurantDishes = restaurantDishes;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }
}