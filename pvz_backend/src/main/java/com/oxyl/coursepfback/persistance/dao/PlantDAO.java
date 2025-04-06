package com.oxyl.coursepfback.persistance.dao;

import com.oxyl.coursepfback.persistance.entity.PlantEntity;
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
 * DAO pour l'accès aux données des plantes via JDBC.
 */
@Repository
public class PlantDAO {

    private final JdbcTemplate jdbcTemplate;

    // RowMapper pour convertir une ligne de résultat SQL en PlantEntity
    private final RowMapper<PlantEntity> rowMapper = (rs, rowNum) -> {
        PlantEntity entity = new PlantEntity();
        entity.setIdPlante(rs.getInt("id_plante"));
        entity.setNom(rs.getString("nom"));
        entity.setPointDeVie(rs.getInt("point_de_vie"));
        entity.setAttaqueParSeconde(rs.getDouble("attaque_par_seconde"));
        entity.setDegatAttaque(rs.getInt("degat_attaque"));
        entity.setCout(rs.getInt("cout"));
        entity.setSoleilParSeconde(rs.getDouble("soleil_par_seconde"));
        entity.setEffet(rs.getString("effet"));
        entity.setCheminImage(rs.getString("chemin_image"));
        return entity;
    };

    public PlantDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Récupère toutes les plantes.
     */
    public List<PlantEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM Plante", rowMapper);
    }

    /**
     * Récupère une plante par son ID.
     */
    public Optional<PlantEntity> findById(int id) {
        try {
            PlantEntity entity = jdbcTemplate.queryForObject(
                    "SELECT * FROM Plante WHERE id_plante = ?",
                    new Object[]{id},
                    rowMapper
            );
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Insère une nouvelle plante.
     */
    public PlantEntity insert(PlantEntity entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Plante (nom, point_de_vie, attaque_par_seconde, degat_attaque, cout, soleil_par_seconde, effet, chemin_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, entity.getNom());
            ps.setInt(2, entity.getPointDeVie());
            ps.setDouble(3, entity.getAttaqueParSeconde());
            ps.setInt(4, entity.getDegatAttaque());
            ps.setInt(5, entity.getCout());
            ps.setDouble(6, entity.getSoleilParSeconde());
            ps.setString(7, entity.getEffet());
            ps.setString(8, entity.getCheminImage());
            return ps;
        }, keyHolder);

        entity.setIdPlante((Integer) Objects.requireNonNull(keyHolder.getKeys()).get("id_plante"));
        return entity;
    }

    /**
     * Met à jour une plante existante.
     */
    public PlantEntity update(PlantEntity entity) {
        jdbcTemplate.update(
                "UPDATE Plante SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, degat_attaque = ?, cout = ?, soleil_par_seconde = ?, effet = ?, chemin_image = ? WHERE id_plante = ?",
                entity.getNom(),
                entity.getPointDeVie(),
                entity.getAttaqueParSeconde(),
                entity.getDegatAttaque(),
                entity.getCout(),
                entity.getSoleilParSeconde(),
                entity.getEffet(),
                entity.getCheminImage(),
                entity.getIdPlante()
        );
        return entity;
    }

    /**
     * Supprime une plante par son ID.
     */
    public boolean deleteById(int id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM Plante WHERE id_plante = ?", id);
        return rowsAffected > 0;
    }
}