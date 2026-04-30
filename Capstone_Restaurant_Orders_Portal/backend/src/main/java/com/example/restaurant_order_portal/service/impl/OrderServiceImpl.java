package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.OrderRequestDTO;
import com.example.restaurant_order_portal.dto.OrderResponseDTO;
import com.example.restaurant_order_portal.entity.*;
import com.example.restaurant_order_portal.enums.OrderStatus;
import com.example.restaurant_order_portal.repository.*;
import com.example.restaurant_order_portal.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of OrderService interface.
 *
 * Contains actual business logic for handling orders.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;

    /**
     * Constructor-based dependency injection
     */
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            CartRepository cartRepository,
                            CartItemRepository cartItemRepository,
                            OrderItemRepository orderItemRepository,
                            AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderItemRepository = orderItemRepository;
        this.addressRepository = addressRepository;
    }

    /**
     * Create a new order
     */
    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(orderRequestDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double totalAmount = 0.0;

        for (CartItem item : cartItems) {
            double price = item.getMenuItem().getPrice();
            totalAmount += price * item.getQuantity();
        }

        if (user.getWalletBalance() < totalAmount) {
            throw new RuntimeException("Insufficient wallet balance");
        }

        user.setWalletBalance(user.getWalletBalance() - totalAmount);
        userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(cart.getRestaurant());
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PLACED);
        order.setAddress(address);

        Order savedOrder = orderRepository.save(order);

        for (CartItem item : cartItems) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setMenuItem(item.getMenuItem());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getMenuItem().getPrice()); // snapshot price

            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteByCartId(cart.getId());

        return orderResponseDTO(savedOrder);
    }

    /**
     * Get all orders by user
     */
    @Override
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {

        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::orderResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all orders by restaurant
     */
    @Override
    public List<OrderResponseDTO> getOrdersByRestaurant(Long restaurantId) {

        return orderRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(this::orderResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cancels an order and refunds the amount to user's wallet.
     */
    @Override
    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!order.getUser().getId().equals(loggedInUser.getId())) {
            throw new RuntimeException("You are not allowed to cancel this order");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order is already cancelled");
        }

        if (order.getStatus() != OrderStatus.PLACED) {
            throw new RuntimeException("Only PLACED orders can be cancelled");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime orderTime = order.getCreatedAt();

        long diffInSeconds = Duration.between(orderTime, now).getSeconds();

        if (diffInSeconds > 30) {
            throw new RuntimeException("Cancellation time exceeded (30 seconds limit)");
        }

        User user = order.getUser();

        user.setWalletBalance(user.getWalletBalance() + order.getTotalAmount());
        userRepository.save(user);

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    /**
     * Entity to DTO conversion
     */
    private OrderResponseDTO orderResponseDTO(Order order) {

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();

        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setUserId(order.getUser().getId());
        orderResponseDTO.setRestaurantId(order.getRestaurant().getId());
        orderResponseDTO.setTotalAmount(order.getTotalAmount());
        orderResponseDTO.setStatus(order.getStatus());
        orderResponseDTO.setCreatedAt(order.getCreatedAt());
        orderResponseDTO.setAddress(
                order.getAddress().getStreetAddress() + ", " +
                        order.getAddress().getCity() + " - " +
                        order.getAddress().getPincode()
        );

        return orderResponseDTO;
    }
}
