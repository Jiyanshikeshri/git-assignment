package com.example.restaurant_order_portal.config;

import com.example.restaurant_order_portal.constants.AppConstants;
import com.example.restaurant_order_portal.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(AppConstants.BASE_USER_URL + AppConstants.REGISTER_URL,
                                AppConstants.BASE_USER_URL + AppConstants.LOGIN_URL).permitAll()

                        .requestMatchers(AppConstants.ADMIN_URL).hasRole("RESTAURANT_OWNER")
                        .requestMatchers(AppConstants.USER_URL).hasRole("USER")

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
