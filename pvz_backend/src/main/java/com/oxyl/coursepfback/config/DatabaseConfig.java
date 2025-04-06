package com.oxyl.coursepfback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/pvz");
        dataSource.setUsername("lucasepf");
        dataSource.setPassword("lucasEPF25!");

        // Test de connexion lors du démarrage
        try {
            dataSource.getConnection().close();
            System.out.println("✓ Connexion à la base de données réussie");
        } catch (SQLException e) {
            System.err.println("✗ Échec de connexion à la base de données: " + e.getMessage());
            // Vous pourriez également logger l'erreur ou lancer une exception personnalisée
        }

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
