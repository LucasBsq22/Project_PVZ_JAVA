package com.oxyl.coursepfback.core.service;

import com.oxyl.coursepfback.core.model.Plant;
import com.oxyl.coursepfback.persistance.repository.PlantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des plantes
 */
@Service
public class PlantService {

    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    /**
     * Récupère toutes les plantes
     */
    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    /**
     * Récupère une plante par son id
     */
    public Optional<Plant> getPlantById(int id) {
        return plantRepository.findById(id);
    }

    /**
     * Sauvegarde une plante (création ou mise à jour)
     */
    public Plant savePlant(Plant plant) {
        return plantRepository.save(plant);
    }

    /**
     * Supprime une plante par son id
     */
    public boolean deletePlant(int id) {
        return plantRepository.deleteById(id);
    }

    /**
     * Valide le format d'une plante
     */
    public boolean validatePlantFormat(Plant plant) {
        return plant != null
                && plant.getNom() != null && !plant.getNom().isEmpty()
                && plant.getPointDeVie() != null && plant.getPointDeVie() > 0
                && plant.getAttaqueParSeconde() != null && plant.getAttaqueParSeconde() >= 0
                && plant.getDegatAttaque() != null && plant.getDegatAttaque() >= 0
                && plant.getCout() != null && plant.getCout() >= 0
                && plant.getSoleilParSeconde() != null && plant.getSoleilParSeconde() >= 0
                && plant.getEffet() != null
                && plant.getCheminImage() != null && !plant.getCheminImage().isEmpty();
    }
}