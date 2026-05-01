package com.example.restaurant_order_portal.service;

import com.example.restaurant_order_portal.dto.OrderRequestDTO;
import com.example.restaurant_order_portal.dto.OrderResponseDTO;
import com.example.restaurant_order_portal.entity.*;
import com.example.restaurant_order_portal.enums.OrderStatus;
import com.example.restaurant_order_portal.exception.BadRequestException;
import com.example.restaurant_order_portal.exception.ConflictException;
import com.example.restaurant_order_portal.repository.*;
import com.example.restaurant_order_portal.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock private OrderRepository orderRepository;
    @Mock private UserRepository userRepository;
    @Mock private CartRepository cartRepository;
    @Mock private CartItemRepository cartItemRepository;
    @Mock private OrderItemRepository orderItemRepository;
    @Mock private AddressRepository addressRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private User user;
    private Cart cart;
    private CartItem cartItem;
    private Address address;
    private OrderRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("test@gmail.com");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(securityContext);

        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);
        user.setEmail("test@gmail.com");
        user.setWalletBalance(1000.0);

        address = new Address();
        ReflectionTestUtils.setField(address, "id", 10L);

        Restaurant restaurant = new Restaurant();
        ReflectionTestUtils.setField(restaurant, "id", 100L);

        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(100.0);
        menuItem.setRestaurant(restaurant);

        cart = new Cart();
        ReflectionTestUtils.setField(cart, "id", 5L);
        cart.setUser(user);
        cart.setRestaurant(restaurant);

        cartItem = new CartItem(cart, menuItem, 2); // total = 200

        requestDTO = new OrderRequestDTO();
        requestDTO.setAddressId(10L);
    }

    /**
     * Create order
     */

    @Test
    void createOrder_success() {
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(addressRepository.findById(10L)).thenReturn(Optional.of(address));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartId(5L)).thenReturn(List.of(cartItem));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        OrderResponseDTO response = orderService.createOrder(requestDTO);

        assertNotNull(response);
        assertEquals(OrderStatus.PLACED, response.getStatus());
    }

    @Test
    void createOrder_cartEmpty() {
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(addressRepository.findById(10L)).thenReturn(Optional.of(address));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartId(5L)).thenReturn(List.of());

        assertThrows(BadRequestException.class, () ->
                orderService.createOrder(requestDTO));
    }

    @Test
    void createOrder_insufficientBalance() {
        user.setWalletBalance(50.0);

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(addressRepository.findById(10L)).thenReturn(Optional.of(address));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartId(5L)).thenReturn(List.of(cartItem));

        assertThrows(ConflictException.class, () ->
                orderService.createOrder(requestDTO));
    }

    /**
     * Get orders
     */

    @Test
    void getOrdersByUser_success() {
        Order order = new Order();
        ReflectionTestUtils.setField(order, "id", 1L);
        order.setUser(user);
        order.setRestaurant(cart.getRestaurant());
        order.setTotalAmount(200.0);
        order.setStatus(OrderStatus.PLACED);
        order.setAddress(address);

        when(orderRepository.findByUserId(1L)).thenReturn(List.of(order));

        List<OrderResponseDTO> result = orderService.getOrdersByUser(1L);

        assertEquals(1, result.size());
    }

    /**
     * Cancel order
     */

    @Test
    void cancelOrder_success() {
        Order order = new Order();
        ReflectionTestUtils.setField(order, "id", 1L);
        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);
        order.setTotalAmount(200.0);
        ReflectionTestUtils.setField(order, "createdAt",
                LocalDateTime.now().minusSeconds(10));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));

        orderService.cancelOrder(1L);

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void cancelOrder_notOwner() {
        User another = new User();
        ReflectionTestUtils.setField(another, "id", 2L);

        Order order = new Order();
        order.setUser(another);
        order.setStatus(OrderStatus.PLACED);
        ReflectionTestUtils.setField(order, "createdAt",
                LocalDateTime.now().minusSeconds(60));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));

        assertThrows(ConflictException.class, () ->
                orderService.cancelOrder(1L));
    }

    @Test
    void cancelOrder_alreadyCancelled() {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CANCELLED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));

        assertThrows(ConflictException.class, () ->
                orderService.cancelOrder(1L));
    }

    @Test
    void cancelOrder_timeExceeded() {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);
        ReflectionTestUtils.setField(order, "createdAt",
                LocalDateTime.now().minusSeconds(60));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));

        assertThrows(ConflictException.class, () ->
                orderService.cancelOrder(1L));
    }
}