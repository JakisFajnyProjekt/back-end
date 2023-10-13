package com.pl.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Restaurant restaurant;
    private boolean isCompleted;

    private LocalDateTime date = LocalDateTime.now();

    private BigDecimal price;

    public Order() {
    }

    public Order(Long id, boolean isCompleted, LocalDateTime date, BigDecimal price) {
        this.id = id;
        this.isCompleted = isCompleted;
        this.date = date;
        this.price = price;
    }

    public Order(boolean isCompleted, LocalDateTime date, BigDecimal price) {
        this.isCompleted = isCompleted;
        this.date = date;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean completed) {
        isCompleted = completed;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return isCompleted == order.isCompleted && Objects.equals(id, order.id) && Objects.equals(date, order.date) && Objects.equals(price, order.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isCompleted, date, price);
    }
}
