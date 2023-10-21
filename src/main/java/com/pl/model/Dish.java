package com.pl.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Table(name = "dishes")
@Entity
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Dish(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id) && Objects.equals(name, dish.name) && Objects.equals(description, dish.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description +
                '}';
    }
}