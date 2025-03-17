package com.oxyl.coursepfback;

import com.oxyl.coursepfback.config.DatabaseConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class MainApp {
    public static void main(String[] args) {
        // Charger la configuration Spring
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);

        // Récupérer JdbcTemplate
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);

        // Vérifier la connexion
        try {
            List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT 1 AS test");
            System.out.println("✅ Connexion réussie ! Test SQL : " + result);
        } catch (Exception e) {
            System.err.println("❌ Échec de connexion à la base de données !");
            e.printStackTrace();
        }

        // Fermer le contexte Spring
        context.close();
    }
}