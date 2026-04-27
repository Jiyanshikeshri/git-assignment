package com.example.restaurant_order_portal.config;

import com.example.restaurant_order_portal.constants.AppConstants;
import com.example.restaurant_order_portal.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

/**
 * Security configuration class.
 *
 * Configures JWT authentication and endpoint security.
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Enable CORS to allow frontend (running on different origin) to access APIs
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(AppConstants.BASE_USER_URL + AppConstants.REGISTER_URL,
                                AppConstants.BASE_USER_URL + AppConstants.LOGIN_URL).permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()

                        /**
                         * OWNER ONLY has access to manage restaurants
                         */
                        .requestMatchers(HttpMethod.POST, AppConstants.BASE_RESTAURANT_URL + "/**").hasRole("RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.PUT, AppConstants.BASE_RESTAURANT_URL + "/**").hasRole("RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.DELETE, AppConstants.BASE_RESTAURANT_URL + "/**").hasRole("RESTAURANT_OWNER")

                        /**
                         * OWNER ONLY has access to manage categories
                         */
                        .requestMatchers(HttpMethod.POST, AppConstants.BASE_CATEGORY_URL + "/**").hasRole("RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.PUT, AppConstants.BASE_CATEGORY_URL + "/**").hasRole("RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.DELETE, AppConstants.BASE_CATEGORY_URL + "/**").hasRole("RESTAURANT_OWNER")

                        /**
                         * OWNER ONLY has access to manage Menu Items
                          */
                        .requestMatchers(HttpMethod.POST, AppConstants.BASE_MENU_ITEM_URL + "/**").hasRole("RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.PUT, AppConstants.BASE_MENU_ITEM_URL + "/**").hasRole("RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.DELETE, AppConstants.BASE_MENU_ITEM_URL + "/**").hasRole("RESTAURANT_OWNER")

                        /**
                         * ORDER APIs (User can create and view order, whereas owner can also view order of its own restaurant)
                         */
                        .requestMatchers(HttpMethod.POST, AppConstants.BASE_ORDER_URL).hasRole("USER")
                        .requestMatchers(HttpMethod.GET, AppConstants.BASE_ORDER_URL + "/user/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, AppConstants.BASE_ORDER_URL + "/restaurant/**").hasRole("RESTAURANT_OWNER")

                        /**
                         * User has access to CART APIs
                         */
                        .requestMatchers(HttpMethod.POST, AppConstants.BASE_CART_URL).hasRole("USER")
                        .requestMatchers(HttpMethod.GET, AppConstants.BASE_CART_URL + "/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, AppConstants.BASE_CART_URL + "/**").hasRole("USER")

                        .anyRequest().authenticated()
                )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
