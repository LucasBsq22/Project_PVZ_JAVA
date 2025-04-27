package com.oxyl.coursepfback.persistance.repository;

import com.oxyl.coursepfback.core.model.Map;
import com.oxyl.coursepfback.persistance.dao.MapDAO;
import com.oxyl.coursepfback.persistance.entity.MapEntity;
import com.oxyl.coursepfback.persistance.mapper.MapEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Fait le lien entre le domaine métier et la couche d'accès aux données
 */
@Repository
public class MapRepository {

    private final MapDAO mapDAO;

    public MapRepository(MapDAO mapDAO) {
        this.mapDAO = mapDAO;
    }

    /**
     * Récupère toutes les maps
     */
    public List<Map> findAll() {
        List<MapEntity> entities = mapDAO.findAll();
        return entities.stream()
                .map(MapEntityMapper::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une map par son id
     */
    public Optional<Map> findById(int id) {
        Optional<MapEntity> entityOpt = mapDAO.findById(id);
        return entityOpt.map(MapEntityMapper::toModel);
    }

    /**
     * Vérifie si une map est référencée par des zombies
     */
    public boolean isReferencedByZombies(int mapId) {
        return mapDAO.isReferencedByZombies(mapId);
    }

    /**
     * Sauvegarde une map (création ou mise à jour)
     */
    public Map save(Map map) {
        MapEntity entity = MapEntityMapper.toEntity(map);
        MapEntity savedEntity;

        if (map.getIdMap() == null) {
            savedEntity = mapDAO.insert(entity);
        } else {
            savedEntity = mapDAO.update(entity);
        }

        return MapEntityMapper.toModel(savedEntity);
    }

    /**
     * Supprime une map par son ID, seulement si elle n'est pas référencée par des zombies
     */
    public boolean deleteById(int id) {
        if (isReferencedByZombies(id)) {
            return false;
        }
        return mapDAO.deleteById(id);
    }
}