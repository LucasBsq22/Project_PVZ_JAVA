package com.oxyl.coursepfback.persistance.repository;

import com.oxyl.coursepfback.core.model.Plant;
import com.oxyl.coursepfback.persistance.dao.PlantDAO;
import com.oxyl.coursepfback.persistance.entity.PlantEntity;
import com.oxyl.coursepfback.persistance.mapper.PlantEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository pour les plantes.
 * Fait le lien entre le domaine métier et la couche d'accès aux données.
 */
@Repository
public class PlantRepository {

    private final PlantDAO plantDAO;

    public PlantRepository(PlantDAO plantDAO) {
        this.plantDAO = plantDAO;
    }

    /**
     * Récupère toutes les plantes.
     */
    public List<Plant> findAll() {
        List<PlantEntity> entities = plantDAO.findAll();
        return entities.stream()
                .map(PlantEntityMapper::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une plante par son ID.
     */
    public Optional<Plant> findById(int id) {
        Optional<PlantEntity> entityOpt = plantDAO.findById(id);
        return entityOpt.map(PlantEntityMapper::toModel);
    }

    /**
     * Sauvegarde une plante (création ou mise à jour).
     */
    public Plant save(Plant plant) {
        PlantEntity entity = PlantEntityMapper.toEntity(plant);
        PlantEntity savedEntity;

        if (plant.getIdPlante() == null) {
            savedEntity = plantDAO.insert(entity);
        } else {
            savedEntity = plantDAO.update(entity);
        }

        return PlantEntityMapper.toModel(savedEntity);
    }

    /**
     * Supprime une plante par son ID.
     */
    public boolean deleteById(int id) {
        return plantDAO.deleteById(id);
    }
}