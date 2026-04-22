package com.example.restaurant_order_portal.repository;

import com.example.restaurant_order_portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 *
 * Provides queries related to application users.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * To find user on the basis of email
      */
    Optional<User> findByEmail(String email);
}
