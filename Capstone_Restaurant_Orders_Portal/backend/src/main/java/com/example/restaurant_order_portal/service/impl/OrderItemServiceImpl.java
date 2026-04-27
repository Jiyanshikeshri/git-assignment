package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.OrderItemResponseDTO;
import com.example.restaurant_order_portal.entity.OrderItem;
import com.example.restaurant_order_portal.repository.OrderItemRepository;
import com.example.restaurant_order_portal.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of OrderItemService
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Get all order items by order ID
     */
    @Override
    public List<OrderItemResponseDTO> getOrderItemsByOrderId(Long orderId) {

        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert entity to DTO
     */
    private OrderItemResponseDTO mapToDTO(OrderItem item) {

        OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();

        orderItemResponseDTO.setId(item.getId());
        orderItemResponseDTO.setOrderId(item.getOrder().getId());
        orderItemResponseDTO.setMenuItemId(item.getMenuItem().getId());
        orderItemResponseDTO.setQuantity(item.getQuantity());
        orderItemResponseDTO.setPrice(item.getPrice());

        return orderItemResponseDTO;
    }
}
