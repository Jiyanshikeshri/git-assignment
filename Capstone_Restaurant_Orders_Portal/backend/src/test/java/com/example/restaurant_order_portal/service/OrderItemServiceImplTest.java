package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.OrderItemResponseDTO;
import com.example.restaurant_order_portal.entity.MenuItem;
import com.example.restaurant_order_portal.entity.Order;
import com.example.restaurant_order_portal.entity.OrderItem;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.OrderItemRepository;
import com.example.restaurant_order_portal.service.impl.OrderItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class OrderItemServiceImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    private Order order;
    private MenuItem menuItem;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        order = new Order();
        ReflectionTestUtils.setField(order, "id", 1L);

        menuItem = new MenuItem();
        ReflectionTestUtils.setField(menuItem, "id", 10L);

        orderItem = new OrderItem();
        ReflectionTestUtils.setField(orderItem, "id", 100L);

        orderItem.setOrder(order);
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(2);
        orderItem.setPrice(400.0);
    }

    /**
     * Succes test for get order items
     */

    @Test
    void getOrderItemsByOrderId_success() {
        when(orderItemRepository.findByOrderId(1L))
                .thenReturn(List.of(orderItem));

        List<OrderItemResponseDTO> result =
                orderItemService.getOrderItemsByOrderId(1L);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getQuantity());
    }

    /**
     * Failure test for get order items
     */

    @Test
    void getOrderItemsByOrderId_emptyList() {
        when(orderItemRepository.findByOrderId(1L))
                .thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () ->
                orderItemService.getOrderItemsByOrderId(1L));
    }
}