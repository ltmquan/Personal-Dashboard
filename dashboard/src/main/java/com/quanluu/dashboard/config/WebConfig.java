package com.quanluu.dashboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration for CORS settings.
 * 
 * Allows the Vue frontend (running on localhost:5173 in development)
 * to make cross-origin requests to this API (localhost:8080).
 * 
 * For production deployment:
 * - Update allowedOrigins to your deployed frontend URL
 * - Consider environment-specific configuration
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}