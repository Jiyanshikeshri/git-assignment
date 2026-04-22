package com.example.restaurant_order_portal.entity;

import com.example.restaurant_order_portal.enums.RestaurantStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Restaurant status (open or closed)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RestaurantStatus status;

    // Many restaurants can belong to one owner, defining ManyToOne relationship
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public Long getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RestaurantStatus getStatus() {
        return status;
    }

    public void setStatus(RestaurantStatus status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Restaurant(){

    }

    public Restaurant(String name, RestaurantStatus status, User owner) {
        this.name = name;
        this.status = status;
        this.owner = owner;
    }
}
