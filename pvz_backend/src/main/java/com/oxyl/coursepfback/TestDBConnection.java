package com.oxyl.coursepfback;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDbConnection {
    public static void main(String[] args) {
        try {
            // Charger le pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pvz",
                    "lucasepf",
                    "lucasEPF25!"
            );

            System.out.println("✅ Connexion à la base de données réussie !");
            conn.close();
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Pilote JDBC non trouvé : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Erreur de connexion à la base de données : " + e.getMessage());
        }
    }
}