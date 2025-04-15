package com.oxyl.coursepfback.api.controller;

import com.oxyl.coursepfback.api.dto.MapDTO;
import com.oxyl.coursepfback.api.mapper.MapDTOMapper;
import com.oxyl.coursepfback.core.model.Map;
import com.oxyl.coursepfback.core.service.MapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST pour les endpoints liés aux maps.
 */
@RestController
@RequestMapping("/maps")
public class MapController {

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    /**
     * Endpoint de validation du format.
     */
//    @GetMapping("/validation")
//    public ResponseEntity<String> validateMapFormat(@RequestBody MapDTO mapDTO) {
//        Map map = MapDTOMapper.toModel(mapDTO);
//        if (mapService.validateMapFormat(map)) {
//            return ResponseEntity.ok("Format de map valide");
//        } else {
//            return ResponseEntity.badRequest().body("Format de map invalide");
//        }
//    }

    /**
     * Récupère toutes les maps.
     */
    @GetMapping
    public ResponseEntity<List<MapDTO>> getAllMaps() {
        List<Map> maps = mapService.getAllMaps();
        List<MapDTO> mapDTOs = MapDTOMapper.toDTOList(maps);
        return ResponseEntity.ok(mapDTOs);
    }

    /**
     * Récupère une map par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MapDTO> getMapById(@PathVariable("id") int id) {
        Optional<Map> mapOpt = mapService.getMapById(id);
        return mapOpt.map(map -> ResponseEntity.ok(MapDTOMapper.toDTO(map)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crée une nouvelle map.
     */
    @PostMapping
    public ResponseEntity<MapDTO> createMap(@RequestBody MapDTO mapDTO) {
        Map map = MapDTOMapper.toModel(mapDTO);

        if (!mapService.validateMapFormat(map)) {
            return ResponseEntity.badRequest().build();
        }

        Map savedMap = mapService.saveMap(map);
        return ResponseEntity.status(HttpStatus.CREATED).body(MapDTOMapper.toDTO(savedMap));
    }

    /**
     * Met à jour une map existante.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MapDTO> updateMap(@PathVariable("id") int id, @RequestBody MapDTO mapDTO) {
        // Récupérer d'abord la map existante
        Optional<Map> existingMapOpt = mapService.getMapById(id);

        if (existingMapOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Map existingMap = existingMapOpt.get();

        // Mettre à jour seulement les champs non-null du DTO reçu
        if (mapDTO.getLigne() != null) existingMap.setLigne(mapDTO.getLigne());
        if (mapDTO.getColonne() != null) existingMap.setColonne(mapDTO.getColonne());
        if (mapDTO.getChemin_image() != null) existingMap.setCheminImage(mapDTO.getChemin_image());

        // Conserver l'ID original
        existingMap.setIdMap(id);

        // Sauvegarder la map mise à jour
        Map updatedMap = mapService.saveMap(existingMap);
        return ResponseEntity.ok(MapDTOMapper.toDTO(updatedMap));
    }

    /**
     * Supprime une map.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMap(@PathVariable("id") int id) {
        if (mapService.getMapById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean deleted = mapService.deleteMap(id);

        if (!deleted) {
            // La map n'a pas pu être supprimée car elle est référencée par des zombies
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.noContent().build();
    }
}