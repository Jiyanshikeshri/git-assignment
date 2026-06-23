package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.OrderItemResponseDTO;
import com.example.restaurant_order_portal.entity.OrderItem;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.OrderItemRepository;
import com.example.restaurant_order_portal.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of OrderItemService
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    private static final Logger log = LoggerFactory.getLogger(OrderItemServiceImpl.class);

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Get all order items by order ID
     */
    @Override
    public List<OrderItemResponseDTO> getOrderItemsByOrderId(Long orderId) {

        log.info("Fetching order items for orderId: {}", orderId);

        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);

        if (items.isEmpty()) {
            log.error("No order items found for orderId: {}", orderId);
            throw new ResourceNotFoundException("No order items found for this order");
        }

        return items.stream()
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
