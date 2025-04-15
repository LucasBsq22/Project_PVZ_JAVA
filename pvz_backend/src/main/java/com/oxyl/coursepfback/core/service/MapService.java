package com.oxyl.coursepfback.core.service;

import com.oxyl.coursepfback.core.model.Map;
import com.oxyl.coursepfback.persistance.repository.MapRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des maps.
 */
@Service
public class MapService {

    private final MapRepository mapRepository;

    public MapService(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    /**
     * Récupère toutes les maps.
     */
    public List<Map> getAllMaps() {
        return mapRepository.findAll();
    }

    /**
     * Récupère une map par son ID.
     */
    public Optional<Map> getMapById(int id) {
        return mapRepository.findById(id);
    }

    /**
     * Sauvegarde une map (création ou mise à jour).
     */
    public Map saveMap(Map map) {
        return mapRepository.save(map);
    }

    /**
     * Supprime une map par son ID.
     *
     * @return false si la map est référencée par des zombies
     */
    public boolean deleteMap(int id) {
        return mapRepository.deleteById(id);
    }

    /**
     * Valide le format d'une map.
     */
    public boolean validateMapFormat(Map map) {
        return map != null
                && map.getLigne() != null && map.getLigne() > 0
                && map.getColonne() != null && map.getColonne() > 0
                && map.getCheminImage() != null && !map.getCheminImage().isEmpty();
    }
}