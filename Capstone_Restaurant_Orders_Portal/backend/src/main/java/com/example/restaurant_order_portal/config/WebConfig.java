package com.example.restaurant_order_portal.config;

import com.example.restaurant_order_portal.constants.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global CORS configuration class.
 *
 * This allows the frontend application (running on a different origin)
 * to communicate with the backend APIs.
 */
@Configuration
public class WebConfig {
    /**
     * Configures CORS mappings for all endpoints.
     *
     * Allows cross-origin requests from frontend
     * to access backend APIs.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")

                        /**
                         * Allow requests only from frontend origin (Live Server)
                         */
                        .allowedOrigins(AppConstants.FRONTEND_URL)

                        /**
                         * Allow all major HTTP methods
                         */
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                        /**
                         * Allow all headers (Authorization, Content-Type, etc.)
                         */
                        .allowedHeaders("*");
            }
        };
    }
}
