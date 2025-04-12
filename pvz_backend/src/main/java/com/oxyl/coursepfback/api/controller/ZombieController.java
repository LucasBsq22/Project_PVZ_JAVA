// dans com.oxyl.coursepfback.api.controller.ZombieController.java
package com.oxyl.coursepfback.api.controller;

import com.oxyl.coursepfback.api.dto.ZombieDTO;
import com.oxyl.coursepfback.api.mapper.ZombieDTOMapper;
import com.oxyl.coursepfback.core.model.Zombie;
import com.oxyl.coursepfback.core.service.ZombieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/zombies")
public class ZombieController {

    private final ZombieService zombieService;

    @Autowired
    public ZombieController(ZombieService zombieService) {
        this.zombieService = zombieService;
    }

    @GetMapping
    public ResponseEntity<List<ZombieDTO>> getAllZombies() {
        List<ZombieDTO> zombies = zombieService.getAllZombies().stream()
                .map(ZombieDTOMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(zombies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZombieDTO> getZombieById(@PathVariable("id") Integer id) {
        Zombie zombie = zombieService.getZombieById(id);
        if (zombie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ZombieDTOMapper.toDTO(zombie));
    }

    @PostMapping
    public ResponseEntity<ZombieDTO> createZombie(@RequestBody ZombieDTO zombieDTO) {
        Zombie zombie = ZombieDTOMapper.toModel(zombieDTO);
        Zombie createdZombie = zombieService.createZombie(zombie);
        return new ResponseEntity<>(ZombieDTOMapper.toDTO(createdZombie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZombieDTO> updateZombie(@PathVariable("id") Integer id, @RequestBody ZombieDTO zombieDTO) {
        Zombie zombie = ZombieDTOMapper.toModel(zombieDTO);
        Zombie updatedZombie = zombieService.updateZombie(id, zombie);
        if (updatedZombie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ZombieDTOMapper.toDTO(updatedZombie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZombie(@PathVariable("id") Integer id) {
        boolean deleted = zombieService.deleteZombie(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/map/{mapId}")
    public ResponseEntity<List<ZombieDTO>> getZombiesByMapId(@PathVariable("mapId") Integer mapId) {
        List<ZombieDTO> zombies = zombieService.getZombiesByMapId(mapId).stream()
                .map(ZombieDTOMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(zombies);
    }

    @GetMapping("/validation")
    public ResponseEntity<Map<String, String>> validateZombieFormat() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Le format des données Zombie est valide");
        return ResponseEntity.ok(response);
    }
}