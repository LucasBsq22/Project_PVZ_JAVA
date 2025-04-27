package com.oxyl.coursepfback.persistance.dao;

import com.oxyl.coursepfback.persistance.entity.ZombieEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * DAO pour l'accès aux données des zombies via JDBC
 */
@Repository
public class ZombieDAO {

    private final JdbcTemplate jdbcTemplate;

    // RowMapper pour convertir une ligne de résultat SQL en ZombieEntity
    private final RowMapper<ZombieEntity> rowMapper = (rs, rowNum) -> {
        ZombieEntity entity = new ZombieEntity();
        entity.setIdZombie(rs.getInt("id_zombie"));
        entity.setNom(rs.getString("nom"));
        entity.setPointDeVie(rs.getInt("point_de_vie"));
        entity.setAttaqueParSeconde(rs.getDouble("attaque_par_seconde"));
        entity.setDegatAttaque(rs.getInt("degat_attaque"));
        entity.setVitesseDeDeplacement(rs.getDouble("vitesse_de_deplacement"));
        entity.setCheminImage(rs.getString("chemin_image"));
        entity.setIdMap(rs.getObject("id_map", Integer.class)); // Gestion des NULL possibles
        return entity;
    };

    public ZombieDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Récupère tous les zombies
     */
    public List<ZombieEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM Zombie", rowMapper);
    }

    /**
     * Récupère un zombie par son id
     */
    public Optional<ZombieEntity> findById(int id) {
        try {
            ZombieEntity entity = jdbcTemplate.queryForObject(
                    "SELECT * FROM Zombie WHERE id_zombie = ?",
                    new Object[]{id},
                    rowMapper
            );
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Récupère les zombies par id de map
     */
    public List<ZombieEntity> findByMapId(int mapId) {
        return jdbcTemplate.query(
                "SELECT * FROM Zombie WHERE id_map = ?",
                new Object[]{mapId},
                rowMapper
        );
    }

    /**
     * Insère un nouveau zombie
     */
    public ZombieEntity insert(ZombieEntity entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Zombie (nom, point_de_vie, attaque_par_seconde, degat_attaque, vitesse_de_deplacement, chemin_image, id_map) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, entity.getNom());
            ps.setInt(2, entity.getPointDeVie());
            ps.setDouble(3, entity.getAttaqueParSeconde());
            ps.setInt(4, entity.getDegatAttaque());
            ps.setDouble(5, entity.getVitesseDeDeplacement());
            ps.setString(6, entity.getCheminImage());

            if (entity.getIdMap() != null) {
                ps.setInt(7, entity.getIdMap());
            } else {
                ps.setObject(7, null);
            }

            return ps;
        }, keyHolder);

        entity.setIdZombie((Integer) Objects.requireNonNull(keyHolder.getKeys()).get("id_zombie"));
        return entity;
    }

    /**
     * Met à jour un zombie existant
     */
    public ZombieEntity update(ZombieEntity entity) {
        jdbcTemplate.update(
                "UPDATE Zombie SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, degat_attaque = ?, vitesse_de_deplacement = ?, chemin_image = ?, id_map = ? WHERE id_zombie = ?",
                entity.getNom(),
                entity.getPointDeVie(),
                entity.getAttaqueParSeconde(),
                entity.getDegatAttaque(),
                entity.getVitesseDeDeplacement(),
                entity.getCheminImage(),
                entity.getIdMap(),
                entity.getIdZombie()
        );
        return entity;
    }

    /**
     * Supprime un zombie par son id
     */
    public boolean deleteById(int id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM Zombie WHERE id_zombie = ?", id);
        return rowsAffected > 0;
    }
}