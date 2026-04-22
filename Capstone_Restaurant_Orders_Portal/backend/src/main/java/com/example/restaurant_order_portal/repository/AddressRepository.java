package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    // To get all addresses of a user
    List<Address> findByUserId(Long userId);
}
