package com.example.restaurant_order_portal.entity;

import jakarta.persistence.*;

@Entity
@Table (name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One cart belongs to one user therefore, OneToOne relationship
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Cart belongs to one restaurant means Only one restaurant order at a time therefore, OneToOne relationship
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Cart() {

    }

    public Cart(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
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
}
