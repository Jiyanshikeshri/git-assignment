package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.OrderRequestDTO;
import com.example.restaurant_order_portal.dto.OrderResponseDTO;
import com.example.restaurant_order_portal.entity.*;
import com.example.restaurant_order_portal.enums.OrderStatus;
import com.example.restaurant_order_portal.exception.BadRequestException;
import com.example.restaurant_order_portal.exception.ConflictException;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.*;
import com.example.restaurant_order_portal.service.OrderService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

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

        log.info("Creating order for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found: {}", email);
                    return new ResourceNotFoundException("User not found");
                });

        Address address = addressRepository.findById(orderRequestDTO.getAddressId())
                .orElseThrow(() -> {
                    log.error("Address not found with id: {}", orderRequestDTO.getAddressId());
                    return new ResourceNotFoundException("Address not found");
                });

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> {
                    log.error("Cart not found for userId: {}", user.getId());
                    return new ResourceNotFoundException("Cart not found");
                });

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            log.error("Cart is empty for userId: {}", user.getId());
            throw new BadRequestException("Cart is empty");
        }

        double totalAmount = 0.0;

        for (CartItem item : cartItems) {
            double price = item.getMenuItem().getPrice();
            totalAmount += price * item.getQuantity();
        }

        log.info("Total amount calculated: {}", totalAmount);

        if (user.getWalletBalance() < totalAmount) {
            log.error("Insufficient balance. Required: {}, Available: {}",
                    totalAmount, user.getWalletBalance());
            throw new ConflictException("Insufficient wallet balance");
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
        log.info("Order created with id: {}", savedOrder.getId());

        for (CartItem item : cartItems) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setMenuItem(item.getMenuItem());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getMenuItem().getPrice()); // snapshot price

            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteByCartId(cart.getId());
        log.info("Cart cleared for userId: {}", user.getId());

        return orderResponseDTO(savedOrder);
    }

    /**
     * Get all orders by user
     */
    @Override
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {

        log.info("Fetching orders for userId: {}", userId);

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

        log.info("Fetching orders for restaurantId: {}", restaurantId);

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

        log.info("Cancelling order with id: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found with id: {}", orderId);
                    return new ResourceNotFoundException("Order not found");
                });

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!order.getUser().getId().equals(loggedInUser.getId())) {
            log.error("Unauthorized cancel attempt by user: {}", email);
            throw new ConflictException("You are not allowed to cancel this order");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new ConflictException("Order is already cancelled");
        }

        if (order.getStatus() != OrderStatus.PLACED) {
            throw new ConflictException("Only PLACED orders can be cancelled");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime orderTime = order.getCreatedAt();

        long diffInSeconds = Duration.between(orderTime, now).getSeconds();

        if (diffInSeconds > 30) {
            throw new ConflictException("Cancellation time exceeded (30 seconds limit)");
        }

        User user = order.getUser();

        user.setWalletBalance(user.getWalletBalance() + order.getTotalAmount());
        userRepository.save(user);

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        log.info("Order cancelled and amount refunded for orderId: {}", orderId);
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

    /**
     * Method to view all orders
     */
    @Override
    public List<OrderResponseDTO> getOrdersForLoggedInUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        log.info("Fetching orders for logged-in user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new ResourceNotFoundException("User not found");
                });

        List<Order> orders = orderRepository.findByUserId(user.getId());

        if (orders.isEmpty()) {
            log.warn("No orders found for userId: {}", user.getId());
        } else {
            log.info("Found {} orders for userId: {}", orders.size(), user.getId());
        }

        return orders.stream()
                .map(this::orderResponseDTO)
                .collect(Collectors.toList());
    }
}
