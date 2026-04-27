package com.example.restaurant_order_portal.constants;

/**
 * This class has all application wide constants
 * such as API URLs and role paths.
 */
public class AppConstants {

    /**
     * Base API path for all endpoints.
      */
    public static final String BASE_API = "/api";

    /**
     * Test endpoint accessible by USER role.
     */
    public static final String USER_TEST = "/user/test";

    /**
     * Test endpoint accessible by ADMIN role.
     */
    public static final String ADMIN_TEST = "/admin/test";

    /**
     * Base URL for user-related APIs.
      */
    public static final String BASE_USER_URL = "/api/users";

    /**
     * Endpoint for user registration.
     */
    public static final String REGISTER_URL = "/register";

    /**
     * Endpoint for user login.
     */
    public static final String LOGIN_URL = "/login";

    /**
     * URL pattern for user-restricted APIs. (rbac)
     */
    public static final String ADMIN_URL = "/api/admin/**";

    /**
     * URL pattern for user-restricted APIs. (rbac)
     */
    public static final String USER_URL = "/api/user/**";

    /**
     * Role for restaurant owner (admin).
     */
    public static final String ROLE_RESTAURANT_OWNER = "RESTAURANT_OWNER";

    /**
     * Frontend URL for CORS configuration.
     */
    public static final String FRONTEND_URL = "http://127.0.0.1:5500";

    /**
     * Base URL for restaurant-related APIs.
     */
    public static final String BASE_RESTAURANT_URL = "/api/restaurants";

    /**
     * Path variable for restaurant ID.
     */
    public static final String RESTAURANT_ID = "/{id}";

    /**
     * Base URL for category-related APIs.
     */
    public static final String BASE_CATEGORY_URL = "/api/categories";

    /**
     * Endpoint to create a category.
     */
    public static final String CREATE_CATEGORY = "";

    /**
     * Endpoint to fetch categories by restaurant ID.
     */
    public static final String GET_BY_RESTAURANT = "/restaurant/{restaurantId}";

    /**
     * Endpoint to update a category by ID.
     */
    public static final String UPDATE_CATEGORY = "/{id}";

    /**
     * Endpoint to delete a category by ID.
     */
    public static final String DELETE_CATEGORY = "/{id}";

    /**
     * Base URL for menu item-related APIs.
     */
    public static final String BASE_MENU_ITEM_URL = "/api/menu-items";

    /**
     * Endpoint to fetch menu items by restaurant ID.
     */
    public static final String GET_MENUITEM_BY_RESTAURANT = "/restaurant/";

    /**
     * Endpoint to fetch menu items by category ID.
     */
    public static final String GET_BY_CATEGORY = "/category/";

    /**
     * Base URL for order-related APIs.
     */
    public static final String BASE_ORDER_URL = "/api/orders";

    /**
     * Endpoint to create a new order.
     */
    public static final String CREATE_ORDER = "";

    /**
     * Endpoint to fetch orders by user ID.
     */
    public static final String GET_ORDERS_BY_USER = "/user/{userId}";

    /**
     * Endpoint to fetch orders by restaurant ID.
     */
    public static final String GET_ORDERS_BY_RESTAURANT = "/restaurant/{restaurantId}";

    /**
     * Base URL for cart-related APIs.
     */
    public static final String BASE_CART_URL = "/api/carts";

    /**
     * Endpoint to create a cart.
     */
    public static final String CREATE_CART = "";

    /**
     * Endpoint to fetch cart by user ID.
     */
    public static final String GET_CART_BY_USER = "/user/{userId}";

    /**
     * Endpoint to clear cart by user ID.
     */
    public static final String CLEAR_CART = "/clear/{userId}";
}


