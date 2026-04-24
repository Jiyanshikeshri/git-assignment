package com.example.restaurant_order_portal.constants;

/**
 * This class has all application wide constants
 * such as API URLs and role paths.
 */
public class AppConstants {

    /**
     * For testing API through Postman
      */
    public static final String BASE_API = "/api";

    public static final String USER_TEST = "/user/test";
    public static final String ADMIN_TEST = "/admin/test";

    /**
     * Base URLs
      */
    public static final String BASE_USER_URL = "/api/users";

    /**
     * Auth endpoints
      */
    public static final String REGISTER_URL = "/register";
    public static final String LOGIN_URL = "/login";

    /**
     * Role-based endpoints
     */
    public static final String ADMIN_URL = "/api/admin/**";
    public static final String USER_URL = "/api/user/**";

    /**
     * Role for restaurant owner (admin).
     */
    public static final String ROLE_RESTAURANT_OWNER = "RESTAURANT_OWNER";

    /**
     * Frontend URL for CORS configuration.
     */
    public static final String FRONTEND_URL = "http://127.0.0.1:5500";
}

