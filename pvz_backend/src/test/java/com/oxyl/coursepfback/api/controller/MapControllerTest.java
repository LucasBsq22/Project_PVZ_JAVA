package com.oxyl.coursepfback.api.controller;

import com.oxyl.coursepfback.api.dto.MapDTO;
import com.oxyl.coursepfback.api.mapper.MapDTOMapper;
import com.oxyl.coursepfback.core.model.Map;
import com.oxyl.coursepfback.core.service.MapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MapControllerTest {

    private MapController mapController;
    private MapService mapService;

    @BeforeEach
    void setUp() {
        System.out.println("\n----------- Configuration du test MapController -----------");
        mapService = Mockito.mock(MapService.class);
        mapController = new MapController(mapService);
        System.out.println("✅ Mock MapService injecté avec succès dans MapController");
    }

    @Test
    @DisplayName("getAllMaps devrait retourner une liste de MapDTO")
    void getAllMaps_shouldReturnListOfMapDTOs() {
        System.out.println("\n🔍 TEST: getAllMaps - Récupération de toutes les maps");

        // Arrange
        Map map1 = new Map();
        map1.setIdMap(1);
        map1.setLigne(5);
        map1.setColonne(9);
        map1.setCheminImage("images/map/gazon.png");

        Map map2 = new Map();
        map2.setIdMap(2);
        map2.setLigne(6);
        map2.setColonne(9);
        map2.setCheminImage("images/map/gazon.png");

        List<Map> maps = Arrays.asList(map1, map2);

        System.out.println("🔧 Configuration: MapService configuré pour retourner une liste de 2 maps");
        when(mapService.getAllMaps()).thenReturn(maps);

        // Act
        System.out.println("▶️ Exécution: appel de mapController.getAllMaps()");
        ResponseEntity<List<MapDTO>> response = mapController.getAllMaps();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(5, response.getBody().get(0).getLigne());
        assertEquals(6, response.getBody().get(1).getLigne());
        verify(mapService).getAllMaps();

        System.out.println("✅ Succès: " + response.getBody().size() + " MapDTO retournés avec code HTTP 200 OK");
        System.out.println("   - MapDTO 1: ID=" + response.getBody().get(0).getId_map() + ", dimensions: " + response.getBody().get(0).getLigne() + "×" + response.getBody().get(0).getColonne());
        System.out.println("   - MapDTO 2: ID=" + response.getBody().get(1).getId_map() + ", dimensions: " + response.getBody().get(1).getLigne() + "×" + response.getBody().get(1).getColonne());
    }

    @Test
    @DisplayName("getMapById devrait retourner le MapDTO correspondant quand il existe")
    void getMapById_whenMapExists_shouldReturnMapDTO() {
        System.out.println("\n🔍 TEST: getMapById - Récupération d'une map existante");

        // Arrange
        Map map = new Map();
        map.setIdMap(1);
        map.setLigne(5);
        map.setColonne(9);
        map.setCheminImage("images/map/gazon.png");

        System.out.println("🔧 Configuration: MapService configuré pour retourner une map");
        when(mapService.getMapById(1)).thenReturn(Optional.of(map));

        // Act
        System.out.println("▶️ Exécution: appel de mapController.getMapById(1)");
        ResponseEntity<MapDTO> response = mapController.getMapById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(5, response.getBody().getLigne());
        assertEquals(9, response.getBody().getColonne());
        assertEquals(1, response.getBody().getId_map());
        verify(mapService).getMapById(1);

        System.out.println("✅ Succès: MapDTO retourné avec code HTTP 200 OK");
        System.out.println("   - ID: " + response.getBody().getId_map());
        System.out.println("   - Dimensions: " + response.getBody().getLigne() + "×" + response.getBody().getColonne());
    }

    @Test
    @DisplayName("getMapById devrait retourner 404 quand la map n'existe pas")
    void getMapById_whenMapDoesNotExist_shouldReturn404() {
        System.out.println("\n🔍 TEST: getMapById - Tentative de récupération d'une map inexistante");

        // Arrange
        System.out.println("🔧 Configuration: MapService configuré pour retourner Optional.empty()");
        when(mapService.getMapById(999)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de mapController.getMapById(999)");
        ResponseEntity<MapDTO> response = mapController.getMapById(999);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(mapService).getMapById(999);

        System.out.println("✅ Succès: 404 NOT FOUND retourné comme attendu");
    }

    @Test
    @DisplayName("createMap devrait créer une map et retourner 201 CREATED")
    void createMap_withValidMap_shouldReturn201() {
        System.out.println("\n🔍 TEST: createMap - Création d'une nouvelle map valide");

        // Arrange
        MapDTO inputDto = new MapDTO();
        inputDto.setLigne(4);
        inputDto.setColonne(8);
        inputDto.setChemin_image("images/map/nouvelle.png");

        Map convertedMap = MapDTOMapper.toModel(inputDto);

        Map savedMap = new Map();
        savedMap.setIdMap(3);
        savedMap.setLigne(4);
        savedMap.setColonne(8);
        savedMap.setCheminImage("images/map/nouvelle.png");

        System.out.println("🔧 Configuration: MapService configuré pour valider et sauvegarder une map");
        when(mapService.validateMapFormat(any(Map.class))).thenReturn(true);
        when(mapService.saveMap(any(Map.class))).thenReturn(savedMap);

        // Act
        System.out.println("▶️ Exécution: appel de mapController.createMap()");
        ResponseEntity<MapDTO> response = mapController.createMap(inputDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().getId_map());
        assertEquals(4, response.getBody().getLigne());
        assertEquals(8, response.getBody().getColonne());
        verify(mapService).validateMapFormat(any(Map.class));
        verify(mapService).saveMap(any(Map.class));

        System.out.println("✅ Succès: MapDTO retourné avec code HTTP 201 CREATED");
        System.out.println("   - ID généré: " + response.getBody().getId_map());
        System.out.println("   - Dimensions: " + response.getBody().getLigne() + "×" + response.getBody().getColonne());
    }

    @Test
    @DisplayName("createMap devrait retourner 400 BAD REQUEST pour une map invalide")
    void createMap_withInvalidMap_shouldReturn400() {
        System.out.println("\n🔍 TEST: createMap - Tentative de création d'une map invalide");

        // Arrange
        MapDTO invalidDto = new MapDTO();
        invalidDto.setLigne(-1); // Nombre de lignes invalide
        invalidDto.setColonne(8);
        invalidDto.setChemin_image("images/map/invalide.png");

        System.out.println("🔧 Configuration: MapService configuré pour invalider la map");
        when(mapService.validateMapFormat(any(Map.class))).thenReturn(false);

        // Act
        System.out.println("▶️ Exécution: appel de mapController.createMap() avec une map invalide");
        ResponseEntity<MapDTO> response = mapController.createMap(invalidDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(mapService).validateMapFormat(any(Map.class));
        verify(mapService, never()).saveMap(any(Map.class));

        System.out.println("✅ Succès: 400 BAD REQUEST retourné comme attendu");
    }

    @Test
    @DisplayName("updateMap devrait mettre à jour une map existante")
    void updateMap_whenMapExists_shouldUpdateAndReturn200() {
        System.out.println("\n🔍 TEST: updateMap - Mise à jour d'une map existante");

        // Arrange
        int mapId = 1;

        Map existingMap = new Map();
        existingMap.setIdMap(mapId);
        existingMap.setLigne(5);
        existingMap.setColonne(9);
        existingMap.setCheminImage("images/map/gazon.png");

        MapDTO updateDto = new MapDTO();
        updateDto.setLigne(6);
        updateDto.setColonne(10);
        updateDto.setChemin_image("images/map/modifiee.png");

        Map updatedMap = new Map();
        updatedMap.setIdMap(mapId);
        updatedMap.setLigne(6);
        updatedMap.setColonne(10);
        updatedMap.setCheminImage("images/map/modifiee.png");

        System.out.println("🔧 Configuration: MapService configuré pour trouver et mettre à jour une map");
        when(mapService.getMapById(mapId)).thenReturn(Optional.of(existingMap));
        when(mapService.saveMap(any(Map.class))).thenReturn(updatedMap);

        // Act
        System.out.println("▶️ Exécution: appel de mapController.updateMap(" + mapId + ")");
        ResponseEntity<MapDTO> response = mapController.updateMap(mapId, updateDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(6, response.getBody().getLigne());
        assertEquals(10, response.getBody().getColonne());
        assertEquals("images/map/modifiee.png", response.getBody().getChemin_image());
        verify(mapService).getMapById(mapId);
        verify(mapService).saveMap(any(Map.class));

        System.out.println("✅ Succès: MapDTO mis à jour retourné avec code HTTP 200 OK");
        System.out.println("   - Dimensions modifiées: " + response.getBody().getLigne() + "×" + response.getBody().getColonne());
        System.out.println("   - Chemin d'image modifié: " + response.getBody().getChemin_image());
    }

    @Test
    @DisplayName("updateMap devrait retourner 404 quand la map n'existe pas")
    void updateMap_whenMapDoesNotExist_shouldReturn404() {
        System.out.println("\n🔍 TEST: updateMap - Tentative de mise à jour d'une map inexistante");

        // Arrange
        int mapId = 999;

        MapDTO updateDto = new MapDTO();
        updateDto.setLigne(6);
        updateDto.setColonne(10);

        System.out.println("🔧 Configuration: MapService configuré pour ne pas trouver la map");
        when(mapService.getMapById(mapId)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de mapController.updateMap(" + mapId + ")");
        ResponseEntity<MapDTO> response = mapController.updateMap(mapId, updateDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(mapService).getMapById(mapId);
        verify(mapService, never()).saveMap(any(Map.class));

        System.out.println("✅ Succès: 404 NOT FOUND retourné comme attendu");
    }

    @Test
    @DisplayName("deleteMap devrait supprimer une map existante et retourner 204")
    void deleteMap_whenMapExists_shouldReturn204() {
        System.out.println("\n🔍 TEST: deleteMap - Suppression d'une map existante");

        // Arrange
        int mapId = 1;

        System.out.println("🔧 Configuration: MapService configuré pour trouver et supprimer une map");
        when(mapService.getMapById(mapId)).thenReturn(Optional.of(new Map()));
        when(mapService.deleteMap(mapId)).thenReturn(true);

        // Act
        System.out.println("▶️ Exécution: appel de mapController.deleteMap(" + mapId + ")");
        ResponseEntity<Void> response = mapController.deleteMap(mapId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(mapService).getMapById(mapId);
        verify(mapService).deleteMap(mapId);

        System.out.println("✅ Succès: 204 NO CONTENT retourné comme attendu");
    }

    @Test
    @DisplayName("deleteMap devrait retourner 404 quand la map n'existe pas")
    void deleteMap_whenMapDoesNotExist_shouldReturn404() {
        System.out.println("\n🔍 TEST: deleteMap - Tentative de suppression d'une map inexistante");

        // Arrange
        int mapId = 999;

        System.out.println("🔧 Configuration: MapService configuré pour ne pas trouver la map");
        when(mapService.getMapById(mapId)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de mapController.deleteMap(" + mapId + ")");
        ResponseEntity<Void> response = mapController.deleteMap(mapId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(mapService).getMapById(mapId);
        verify(mapService, never()).deleteMap(anyInt());

        System.out.println("✅ Succès: 404 NOT FOUND retourné comme attendu");
    }

    @Test
    @DisplayName("deleteMap devrait retourner 409 CONFLICT quand la map est référencée")
    void deleteMap_whenMapIsReferenced_shouldReturn409() {
        System.out.println("\n🔍 TEST: deleteMap - Tentative de suppression d'une map référencée");

        // Arrange
        int mapId = 1;

        System.out.println("🔧 Configuration: MapService configuré pour trouver mais ne pas pouvoir supprimer une map référencée");
        when(mapService.getMapById(mapId)).thenReturn(Optional.of(new Map()));
        when(mapService.deleteMap(mapId)).thenReturn(false);

        // Act
        System.out.println("▶️ Exécution: appel de mapController.deleteMap(" + mapId + ")");
        ResponseEntity<Void> response = mapController.deleteMap(mapId);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
        verify(mapService).getMapById(mapId);
        verify(mapService).deleteMap(mapId);

        System.out.println("✅ Succès: 409 CONFLICT retourné comme attendu");
    }
}