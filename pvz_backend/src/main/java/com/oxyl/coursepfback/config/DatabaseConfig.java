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
        // utilisateur perso et mot de passe
        dataSource.setUsername("lucasepf");
        dataSource.setPassword("lucasEPF25!");

        // Test de connexion lors du démarrage
        try {
            dataSource.getConnection().close();
            System.out.println("✓ Connexion à la base de données réussie");
        } catch (SQLException e) {
            System.err.println("✗ Échec de connexion à la base de données: " + e.getMessage());
        }

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
