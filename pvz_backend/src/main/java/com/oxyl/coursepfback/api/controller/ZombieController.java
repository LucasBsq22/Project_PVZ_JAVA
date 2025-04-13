package com.oxyl.coursepfback.api.controller;

import com.oxyl.coursepfback.api.dto.ZombieDTO;
import com.oxyl.coursepfback.api.mapper.ZombieDTOMapper;
import com.oxyl.coursepfback.core.model.Zombie;
import com.oxyl.coursepfback.core.service.ZombieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST pour les endpoints liés aux zombies.
 */
@RestController
@RequestMapping("/zombies")
public class ZombieController {

    private final ZombieService zombieService;

    public ZombieController(ZombieService zombieService) {
        this.zombieService = zombieService;
    }

    /**
     * Endpoint de validation du format.
     */
    @PostMapping("/validation")
    public ResponseEntity<String> validateZombieFormat(@RequestBody ZombieDTO zombieDTO) {
        Zombie zombie = ZombieDTOMapper.toModel(zombieDTO);
        if (zombieService.validateZombieFormat(zombie)) {
            return ResponseEntity.ok("Format de zombie valide");
        } else {
            return ResponseEntity.badRequest().body("Format de zombie invalide");
        }
    }

    /**
     * Récupère tous les zombies.
     */
    @GetMapping
    public ResponseEntity<List<ZombieDTO>> getAllZombies() {
        List<Zombie> zombies = zombieService.getAllZombies();
        List<ZombieDTO> zombieDTOs = ZombieDTOMapper.toDTOList(zombies);
        return ResponseEntity.ok(zombieDTOs);
    }

    /**
     * Récupère un zombie par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ZombieDTO> getZombieById(@PathVariable("id") int id) {
        Optional<Zombie> zombieOpt = zombieService.getZombieById(id);
        return zombieOpt.map(zombie -> ResponseEntity.ok(ZombieDTOMapper.toDTO(zombie)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Récupère les zombies par ID de map.
     */
    @GetMapping("/map/{mapId}")
    public ResponseEntity<List<ZombieDTO>> getZombiesByMapId(@PathVariable("mapId") int mapId) {
        List<Zombie> zombies = zombieService.getZombiesByMapId(mapId);
        List<ZombieDTO> zombieDTOs = ZombieDTOMapper.toDTOList(zombies);
        return ResponseEntity.ok(zombieDTOs);
    }

    /**
     * Crée un nouveau zombie.
     */
    @PostMapping
    public ResponseEntity<ZombieDTO> createZombie(@RequestBody ZombieDTO zombieDTO) {
        Zombie zombie = ZombieDTOMapper.toModel(zombieDTO);

        if (!zombieService.validateZombieFormat(zombie)) {
            return ResponseEntity.badRequest().build();
        }

        Zombie savedZombie = zombieService.saveZombie(zombie);
        return ResponseEntity.status(HttpStatus.CREATED).body(ZombieDTOMapper.toDTO(savedZombie));
    }

    /**
     * Met à jour un zombie existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ZombieDTO> updateZombie(@PathVariable("id") int id, @RequestBody ZombieDTO zombieDTO) {
        Zombie zombie = ZombieDTOMapper.toModel(zombieDTO);

        if (!zombieService.validateZombieFormat(zombie)) {
            return ResponseEntity.badRequest().build();
        }

        if (zombieService.getZombieById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        zombie.setIdZombie(id);
        Zombie updatedZombie = zombieService.saveZombie(zombie);
        return ResponseEntity.ok(ZombieDTOMapper.toDTO(updatedZombie));
    }

    /**
     * Supprime un zombie.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZombie(@PathVariable("id") int id) {
        if (zombieService.getZombieById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean deleted = zombieService.deleteZombie(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.internalServerError().build();
    }
}