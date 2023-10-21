package com.pl.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String houseNumber;
    private String street;

    private String city;

    private String postalCode;
    @ManyToMany(mappedBy = "deliveryAdresses")
    private Set<User> user;
    @OneToMany(mappedBy = "deliveryAddress")
    private List<Order> orders;

    public Address() {

    }

    public Address(String houseNumber, String street, String city, String postalCode, Set<User> user, List<Order> orders) {
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.user = user;
        this.orders = orders;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
