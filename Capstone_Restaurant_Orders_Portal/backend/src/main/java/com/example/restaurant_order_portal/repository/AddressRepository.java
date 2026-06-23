package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Address entity.
 *
 * Provides queries related to user addresses.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * To get all addresses of a user
      */
    List<Address> findByUserId(Long userId);
}
