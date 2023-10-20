package com.pl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "restaurant_dish_id")
    private RestaurantDish restaurantDish;
}
