package com.oxyl.coursepfback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configure CORS pour permettre les requêtes depuis http://localhost:5173 (frontend)
        registry.addMapping("/**") // Pour tous les endpoints
                .allowedOrigins("http://localhost:5173") // Origine autorisée
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes HTTP autorisées
                .allowedHeaders("*") // Tous les headers autorisés
                .allowCredentials(true); // Autoriser les cookies
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure le gestionnaire de ressources pour les images
        registry.addResourceHandler("/images/**") // URL pattern
                .addResourceLocations("/images/"); // Emplacement physique
    }
}