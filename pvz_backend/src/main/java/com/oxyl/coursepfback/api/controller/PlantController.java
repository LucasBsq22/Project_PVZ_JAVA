package com.oxyl.coursepfback.api.controller;

import com.oxyl.coursepfback.api.dto.PlantDTO;
import com.oxyl.coursepfback.api.mapper.PlantDTOMapper;
import com.oxyl.coursepfback.core.model.Plant;
import com.oxyl.coursepfback.core.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST pour les endpoints liés aux plantes
 */
@RestController
@RequestMapping("/plantes")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    /**
     * Récupère toutes les plantes
     */
    @GetMapping
    public ResponseEntity<List<PlantDTO>> getAllPlants() {
        List<Plant> plants = plantService.getAllPlants();
        List<PlantDTO> plantDTOs = PlantDTOMapper.toDTOList(plants);
        return ResponseEntity.ok(plantDTOs);
    }

    /**
     * Récupère une plante par son id
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlantDTO> getPlantById(@PathVariable("id") int id) {
        Optional<Plant> plantOpt = plantService.getPlantById(id);
        return plantOpt.map(plant -> ResponseEntity.ok(PlantDTOMapper.toDTO(plant)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crée une nouvelle plante
     */
    @PostMapping
    public ResponseEntity<PlantDTO> createPlant(@RequestBody PlantDTO plantDTO) {
        Plant plant = PlantDTOMapper.toModel(plantDTO);

        if (!plantService.validatePlantFormat(plant)) {
            return ResponseEntity.badRequest().build();
        }

        Plant savedPlant = plantService.savePlant(plant);
        return ResponseEntity.status(HttpStatus.CREATED).body(PlantDTOMapper.toDTO(savedPlant));
    }

    /**
     * Met à jour une plante existante
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlantDTO> updatePlant(@PathVariable("id") int id, @RequestBody PlantDTO plantDTO) {
        // Récupérer d'abord la plante existante
        Optional<Plant> existingPlantOpt = plantService.getPlantById(id);

        if (existingPlantOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Plant existingPlant = existingPlantOpt.get();

        // Mettre à jour seulement les champs non-null du DTO reçu
        if (plantDTO.getNom() != null) existingPlant.setNom(plantDTO.getNom());
        if (plantDTO.getPoint_de_vie() != null) existingPlant.setPointDeVie(plantDTO.getPoint_de_vie());
        if (plantDTO.getAttaque_par_seconde() != null) existingPlant.setAttaqueParSeconde(plantDTO.getAttaque_par_seconde());
        if (plantDTO.getDegat_attaque() != null) existingPlant.setDegatAttaque(plantDTO.getDegat_attaque());
        if (plantDTO.getCout() != null) existingPlant.setCout(plantDTO.getCout());
        if (plantDTO.getSoleil_par_seconde() != null) existingPlant.setSoleilParSeconde(plantDTO.getSoleil_par_seconde());
        if (plantDTO.getEffet() != null) existingPlant.setEffet(plantDTO.getEffet());
        if (plantDTO.getChemin_image() != null) existingPlant.setCheminImage(plantDTO.getChemin_image());

        // Conserver l'ID original
        existingPlant.setIdPlante(id);

        // Sauvegarder la plante mise à jour
        Plant updatedPlant = plantService.savePlant(existingPlant);
        return ResponseEntity.ok(PlantDTOMapper.toDTO(updatedPlant));
    }

    /**
     * Supprime une plante par son id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable("id") int id) {
        if (plantService.getPlantById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean deleted = plantService.deletePlant(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.internalServerError().build();
    }
}