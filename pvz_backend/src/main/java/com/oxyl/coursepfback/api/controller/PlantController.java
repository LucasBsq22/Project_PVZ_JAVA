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
 * Controller REST pour les endpoints liés aux plantes.
 */
@RestController
@RequestMapping("/plantes")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    /**
     * Endpoint de validation du format.
     */
    @PostMapping("/validation")
    public ResponseEntity<String> validatePlantFormat(@RequestBody PlantDTO plantDTO) {
        Plant plant = PlantDTOMapper.toModel(plantDTO);
        if (plantService.validatePlantFormat(plant)) {
            return ResponseEntity.ok("Format de plante valide");
        } else {
            return ResponseEntity.badRequest().body("Format de plante invalide");
        }
    }

    /**
     * Récupère toutes les plantes.
     */
    @GetMapping
    public ResponseEntity<List<PlantDTO>> getAllPlants() {
        List<Plant> plants = plantService.getAllPlants();
        List<PlantDTO> plantDTOs = PlantDTOMapper.toDTOList(plants);
        return ResponseEntity.ok(plantDTOs);
    }

    /**
     * Récupère une plante par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlantDTO> getPlantById(@PathVariable int id) {
        Optional<Plant> plantOpt = plantService.getPlantById(id);
        return plantOpt.map(plant -> ResponseEntity.ok(PlantDTOMapper.toDTO(plant)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crée une nouvelle plante.
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
     * Met à jour une plante existante.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlantDTO> updatePlant(@PathVariable int id, @RequestBody PlantDTO plantDTO) {
        Plant plant = PlantDTOMapper.toModel(plantDTO);

        if (!plantService.validatePlantFormat(plant)) {
            return ResponseEntity.badRequest().build();
        }

        if (plantService.getPlantById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        plant.setIdPlante(id);
        Plant updatedPlant = plantService.savePlant(plant);
        return ResponseEntity.ok(PlantDTOMapper.toDTO(updatedPlant));
    }

    /**
     * Supprime une plante.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable int id) {
        if (plantService.getPlantById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean deleted = plantService.deletePlant(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.internalServerError().build();
    }
}