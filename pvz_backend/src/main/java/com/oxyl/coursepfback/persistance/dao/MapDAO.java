package com.oxyl.coursepfback.persistance.dao;

import com.oxyl.coursepfback.persistance.entity.MapEntity;
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
 * DAO pour l'accès aux données des maps via JDBC.
 */
@Repository
public class MapDAO {

    private final JdbcTemplate jdbcTemplate;

    // RowMapper pour convertir une ligne de résultat SQL en MapEntity
    private final RowMapper<MapEntity> rowMapper = (rs, rowNum) -> {
        MapEntity entity = new MapEntity();
        entity.setIdMap(rs.getInt("id_map"));
        entity.setLigne(rs.getInt("ligne"));
        entity.setColonne(rs.getInt("colonne"));
        entity.setCheminImage(rs.getString("chemin_image"));
        return entity;
    };

    public MapDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Récupère toutes les maps.
     */
    public List<MapEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM Map", rowMapper);
    }

    /**
     * Récupère une map par son ID.
     */
    public Optional<MapEntity> findById(int id) {
        try {
            MapEntity entity = jdbcTemplate.queryForObject(
                    "SELECT * FROM Map WHERE id_map = ?",
                    new Object[]{id},
                    rowMapper
            );
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Vérifie si une map est référencée par des zombies.
     */
    public boolean isReferencedByZombies(int mapId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Zombie WHERE id_map = ?",
                Integer.class,
                mapId
        );
        return count != null && count > 0;
    }

    /**
     * Insère une nouvelle map.
     */
    public MapEntity insert(MapEntity entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Map (ligne, colonne, chemin_image) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, entity.getLigne());
            ps.setInt(2, entity.getColonne());
            ps.setString(3, entity.getCheminImage());
            return ps;
        }, keyHolder);

        entity.setIdMap((Integer) Objects.requireNonNull(keyHolder.getKeys()).get("id_map"));
        return entity;
    }

    /**
     * Met à jour une map existante.
     */
    public MapEntity update(MapEntity entity) {
        jdbcTemplate.update(
                "UPDATE Map SET ligne = ?, colonne = ?, chemin_image = ? WHERE id_map = ?",
                entity.getLigne(),
                entity.getColonne(),
                entity.getCheminImage(),
                entity.getIdMap()
        );
        return entity;
    }

    /**
     * Supprime une map par son ID.
     */
    public boolean deleteById(int id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM Map WHERE id_map = ?", id);
        return rowsAffected > 0;
    }
}