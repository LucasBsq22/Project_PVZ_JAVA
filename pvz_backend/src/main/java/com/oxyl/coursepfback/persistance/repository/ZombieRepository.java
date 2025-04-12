// dans com.oxyl.coursepfback.persistance.repository.ZombieRepository.java
package com.oxyl.coursepfback.persistance.repository;

import com.oxyl.coursepfback.persistance.dao.ZombieDao;
import com.oxyl.coursepfback.persistance.entity.ZombieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ZombieRepository implements ZombieDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ZombieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ZombieEntity> rowMapper = (ResultSet rs, int rowNum) -> {
        ZombieEntity zombie = new ZombieEntity();
        zombie.setIdZombie(rs.getInt("id_zombie"));
        zombie.setNom(rs.getString("nom"));
        zombie.setPointDeVie(rs.getInt("point_de_vie"));
        zombie.setAttaqueParSeconde(rs.getDouble("attaque_par_seconde"));
        zombie.setDegatAttaque(rs.getInt("degat_attaque"));
        zombie.setVitesseDeDeplacement(rs.getDouble("vitesse_de_deplacement"));
        zombie.setCheminImage(rs.getString("chemin_image"));
        zombie.setIdMap(rs.getInt("id_map"));
        return zombie;
    };

    @Override
    public List<ZombieEntity> findAll() {
        String sql = "SELECT * FROM Zombie";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ZombieEntity findById(Integer id) {
        String sql = "SELECT * FROM Zombie WHERE id_zombie = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ZombieEntity save(ZombieEntity zombie) {
        String sql = "INSERT INTO Zombie (nom, point_de_vie, attaque_par_seconde, degat_attaque, vitesse_de_deplacement, chemin_image, id_map) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                zombie.getNom(),
                zombie.getPointDeVie(),
                zombie.getAttaqueParSeconde(),
                zombie.getDegatAttaque(),
                zombie.getVitesseDeDeplacement(),
                zombie.getCheminImage(),
                zombie.getIdMap());

        // Récupération de l'ID généré
        Integer id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        zombie.setIdZombie(id);

        return zombie;
    }

    @Override
    public ZombieEntity update(ZombieEntity zombie) {
        String sql = "UPDATE Zombie SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, " +
                "degat_attaque = ?, vitesse_de_deplacement = ?, chemin_image = ?, id_map = ? " +
                "WHERE id_zombie = ?";

        int rowsAffected = jdbcTemplate.update(sql,
                zombie.getNom(),
                zombie.getPointDeVie(),
                zombie.getAttaqueParSeconde(),
                zombie.getDegatAttaque(),
                zombie.getVitesseDeDeplacement(),
                zombie.getCheminImage(),
                zombie.getIdMap(),
                zombie.getIdZombie());

        return rowsAffected > 0 ? zombie : null;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM Zombie WHERE id_zombie = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<ZombieEntity> findByMapId(Integer mapId) {
        String sql = "SELECT * FROM Zombie WHERE id_map = ?";
        return jdbcTemplate.query(sql, rowMapper, mapId);
    }
}