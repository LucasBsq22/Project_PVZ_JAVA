package com.oxyl.coursepfback.core.service;

import com.oxyl.coursepfback.core.model.Map;
import com.oxyl.coursepfback.persistance.repository.MapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MapServiceTest {

    private MapService mapService;
    private MapRepository mapRepository;

    @BeforeEach
    void setUp() {
        System.out.println("\n----------- Configuration du test MapService -----------");
        mapRepository = Mockito.mock(MapRepository.class);
        mapService = new MapService(mapRepository);
        System.out.println("✅ Mock MapRepository injecté avec succès dans MapService");
    }

    @Test
    @DisplayName("getAllMaps devrait retourner toutes les maps")
    void getAllMaps_shouldReturnAllMaps() {
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

        List<Map> expectedMaps = Arrays.asList(map1, map2);

        System.out.println("🔧 Configuration: MapRepository configuré pour retourner une liste de 2 maps");
        when(mapRepository.findAll()).thenReturn(expectedMaps);

        // Act
        System.out.println("▶️ Exécution: appel de mapService.getAllMaps()");
        List<Map> result = mapService.getAllMaps();

        // Assert
        assertEquals(expectedMaps.size(), result.size());
        assertEquals(expectedMaps, result);
        verify(mapRepository).findAll();

        System.out.println("✅ Succès: " + result.size() + " maps récupérées");
        System.out.println("   - Map 1: ID=" + result.get(0).getIdMap() + ", dimensions: " + result.get(0).getLigne() + "×" + result.get(0).getColonne());
        System.out.println("   - Map 2: ID=" + result.get(1).getIdMap() + ", dimensions: " + result.get(1).getLigne() + "×" + result.get(1).getColonne());
    }

    @Test
    @DisplayName("getMapById devrait retourner la map correspondante quand elle existe")
    void getMapById_whenMapExists_shouldReturnMap() {
        System.out.println("\n🔍 TEST: getMapById - Récupération d'une map existante");

        // Arrange
        Map expected = new Map();
        expected.setIdMap(1);
        expected.setLigne(5);
        expected.setColonne(9);
        expected.setCheminImage("images/map/gazon.png");

        System.out.println("🔧 Configuration: MapRepository configuré pour retourner une map");
        when(mapRepository.findById(1)).thenReturn(Optional.of(expected));

        // Act
        System.out.println("▶️ Exécution: appel de mapService.getMapById(1)");
        Optional<Map> result = mapService.getMapById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(mapRepository).findById(1);

        System.out.println("✅ Succès: Map trouvée");
        System.out.println("   - ID: " + result.get().getIdMap());
        System.out.println("   - Dimensions: " + result.get().getLigne() + "×" + result.get().getColonne());
    }

    @Test
    @DisplayName("getMapById devrait retourner Optional vide quand la map n'existe pas")
    void getMapById_whenMapDoesNotExist_shouldReturnEmptyOptional() {
        System.out.println("\n🔍 TEST: getMapById - Tentative de récupération d'une map inexistante");

        // Arrange
        System.out.println("🔧 Configuration: MapRepository configuré pour retourner Optional.empty()");
        when(mapRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de mapService.getMapById(999)");
        Optional<Map> result = mapService.getMapById(999);

        // Assert
        assertFalse(result.isPresent());
        verify(mapRepository).findById(999);

        System.out.println("✅ Succès: Optional vide retourné comme attendu");
    }

    @Test
    @DisplayName("saveMap devrait déléguer l'appel au repository")
    void saveMap_shouldDelegateToRepository() {
        System.out.println("\n🔍 TEST: saveMap - Délégation au repository pour sauvegarder une map");

        // Arrange
        Map mapToSave = new Map();
        mapToSave.setLigne(4);
        mapToSave.setColonne(8);
        mapToSave.setCheminImage("images/map/nouvelle.png");

        Map savedMap = new Map();
        savedMap.setIdMap(3);
        savedMap.setLigne(4);
        savedMap.setColonne(8);
        savedMap.setCheminImage("images/map/nouvelle.png");

        System.out.println("🔧 Configuration: MapRepository configuré pour simuler une sauvegarde réussie");
        when(mapRepository.save(any(Map.class))).thenReturn(savedMap);

        // Act
        System.out.println("▶️ Exécution: appel de mapService.saveMap()");
        Map result = mapService.saveMap(mapToSave);

        // Assert
        assertNotNull(result);
        assertEquals(savedMap, result);
        verify(mapRepository).save(mapToSave);

        System.out.println("✅ Succès: Map sauvegardée");
        System.out.println("   - ID généré: " + result.getIdMap());
        System.out.println("   - Dimensions: " + result.getLigne() + "×" + result.getColonne());
    }

    @Test
    @DisplayName("deleteMap devrait déléguer l'appel au repository")
    void deleteMap_shouldDelegateToRepository() {
        System.out.println("\n🔍 TEST: deleteMap - Délégation au repository pour supprimer une map");

        // Arrange
        System.out.println("🔧 Configuration: MapRepository configuré pour simuler une suppression réussie");
        when(mapRepository.deleteById(1)).thenReturn(true);

        // Act
        System.out.println("▶️ Exécution: appel de mapService.deleteMap(1)");
        boolean result = mapService.deleteMap(1);

        // Assert
        assertTrue(result);
        verify(mapRepository).deleteById(1);

        System.out.println("✅ Succès: true retourné, indiquant une suppression réussie");
    }

    @Test
    @DisplayName("validateMapFormat devrait retourner true pour une map valide")
    void validateMapFormat_withValidMap_shouldReturnTrue() {
        System.out.println("\n🔍 TEST: validateMapFormat - Validation d'une map valide");

        // Arrange
        Map validMap = new Map();
        validMap.setLigne(5);
        validMap.setColonne(9);
        validMap.setCheminImage("images/map/gazon.png");

        // Act
        System.out.println("▶️ Exécution: appel de mapService.validateMapFormat() avec une map valide");
        boolean result = mapService.validateMapFormat(validMap);

        // Assert
        assertTrue(result);

        System.out.println("✅ Succès: true retourné, indiquant que la map est valide");
    }

    @Test
    @DisplayName("validateMapFormat devrait retourner false pour une map invalide")
    void validateMapFormat_withInvalidMap_shouldReturnFalse() {
        System.out.println("\n🔍 TEST: validateMapFormat - Validation d'une map invalide");

        // Arrange - map avec nombre de lignes négatif
        Map invalidMap = new Map();
        invalidMap.setLigne(-1);
        invalidMap.setColonne(9);
        invalidMap.setCheminImage("images/map/invalide.png");

        // Act
        System.out.println("▶️ Exécution: appel de mapService.validateMapFormat() avec un nombre de lignes négatif");
        boolean result = mapService.validateMapFormat(invalidMap);

        // Assert
        assertFalse(result);

        System.out.println("✅ Succès: false retourné, indiquant que la map est invalide");

        // Test avec map sans chemin d'image
        invalidMap.setLigne(5);
        invalidMap.setCheminImage(null);

        System.out.println("▶️ Exécution: appel de mapService.validateMapFormat() sans chemin d'image");
        result = mapService.validateMapFormat(invalidMap);

        assertFalse(result);
        System.out.println("✅ Succès: false retourné pour une map sans chemin d'image");

        // Test avec null
        System.out.println("▶️ Exécution: appel de mapService.validateMapFormat() avec null");
        result = mapService.validateMapFormat(null);

        assertFalse(result);
        System.out.println("✅ Succès: false retourné pour une map null");
    }
}