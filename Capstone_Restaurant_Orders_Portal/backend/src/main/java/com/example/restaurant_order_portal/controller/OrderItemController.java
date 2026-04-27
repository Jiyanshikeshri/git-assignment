package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.constants.AppConstants;
import com.example.restaurant_order_portal.dto.OrderItemResponseDTO;
import com.example.restaurant_order_portal.service.OrderItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for OrderItem APIs
 */
@RestController
@RequestMapping(AppConstants.BASE_ORDER_ITEM_URL)
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    /**
     * Get items of an order
     */
    @GetMapping(AppConstants.GET_ORDER_ITEMS_BY_ORDER)
    public List<OrderItemResponseDTO> getOrderItems(@PathVariable Long orderId) {
        return orderItemService.getOrderItemsByOrderId(orderId);
    }
}
