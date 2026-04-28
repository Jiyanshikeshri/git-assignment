package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.constants.AppConstants;
import com.example.restaurant_order_portal.dto.OrderRequestDTO;
import com.example.restaurant_order_portal.dto.OrderResponseDTO;
import com.example.restaurant_order_portal.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * REST Controller for Order APIs.
 *
 * Handles order creation and retrieval operations.
 */
@RestController
@RequestMapping(AppConstants.BASE_ORDER_URL)
public class OrderController {

    private final OrderService orderService;

    /**
     * Constructor-based dependency injection
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create Order
     */
    @PostMapping(AppConstants.CREATE_ORDER)
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        System.out.println("CONTROLLER HIT");
        return orderService.createOrder(orderRequestDTO);
    }

    /**
     * Get Orders by User
     */
    @GetMapping(AppConstants.GET_ORDERS_BY_USER)
    public List<OrderResponseDTO> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    /**
     * Get Orders by Restaurant
     */
    @GetMapping(AppConstants.GET_ORDERS_BY_RESTAURANT)
    public List<OrderResponseDTO> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        return orderService.getOrdersByRestaurant(restaurantId);
    }

    /**
     * Cancel an existing order.
     *
     * Allows user to cancel an order if it's in PLACED state.
     * Refund will be processed to user's wallet.

     */
    @PutMapping(AppConstants.CANCEL_ORDER)
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "Order cancelled successfully and amount refunded";
    }
}
