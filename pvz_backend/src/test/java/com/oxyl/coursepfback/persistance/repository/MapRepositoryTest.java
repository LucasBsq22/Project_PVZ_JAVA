package com.oxyl.coursepfback.persistance.repository;

import com.oxyl.coursepfback.core.model.Map;
import com.oxyl.coursepfback.persistance.dao.MapDAO;
import com.oxyl.coursepfback.persistance.entity.MapEntity;
import com.oxyl.coursepfback.persistance.mapper.MapEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class MapRepositoryTest {

    private MapRepository mapRepository;
    private MapDAO mapDAO;

    @BeforeEach
    void setUp() {
        System.out.println("\n----------- Configuration du test MapRepository -----------");
        mapDAO = Mockito.mock(MapDAO.class);
        mapRepository = new MapRepository(mapDAO);
        System.out.println("✅ Mock MapDAO injecté avec succès dans MapRepository");
    }

    @Test
    @DisplayName("findAll devrait convertir les entités en modèles et les retourner")
    void findAll_shouldReturnAllMaps() {
        System.out.println("\n🔍 TEST: findAll - Conversion et récupération de toutes les maps");

        // Arrange
        MapEntity entity1 = new MapEntity();
        entity1.setIdMap(1);
        entity1.setLigne(5);
        entity1.setColonne(9);
        entity1.setCheminImage("images/map/gazon.png");

        MapEntity entity2 = new MapEntity();
        entity2.setIdMap(2);
        entity2.setLigne(6);
        entity2.setColonne(9);
        entity2.setCheminImage("images/map/gazon.png");

        List<MapEntity> entities = Arrays.asList(entity1, entity2);

        System.out.println("🔧 Configuration: MapDAO configuré pour retourner une liste de 2 entités");
        when(mapDAO.findAll()).thenReturn(entities);

        // Act
        System.out.println("▶️ Exécution: appel de mapRepository.findAll()");
        List<Map> result = mapRepository.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(5, result.get(0).getLigne());
        assertEquals(6, result.get(1).getLigne());
        verify(mapDAO).findAll();

        System.out.println("✅ Succès: " + result.size() + " maps récupérées et converties correctement");
        System.out.println("   - Map 1: ID=" + result.get(0).getIdMap() + ", dimensions: " + result.get(0).getLigne() + "×" + result.get(0).getColonne());
        System.out.println("   - Map 2: ID=" + result.get(1).getIdMap() + ", dimensions: " + result.get(1).getLigne() + "×" + result.get(1).getColonne());
    }

    @Test
    @DisplayName("findById devrait convertir l'entité en modèle quand elle existe")
    void findById_whenMapExists_shouldReturnMap() {
        System.out.println("\n🔍 TEST: findById - Conversion et récupération d'une map existante");

        // Arrange
        MapEntity entity = new MapEntity();
        entity.setIdMap(1);
        entity.setLigne(5);
        entity.setColonne(9);
        entity.setCheminImage("images/map/gazon.png");

        System.out.println("🔧 Configuration: MapDAO configuré pour retourner une entité");
        when(mapDAO.findById(1)).thenReturn(Optional.of(entity));

        // Act
        System.out.println("▶️ Exécution: appel de mapRepository.findById(1)");
        Optional<Map> result = mapRepository.findById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(5, result.get().getLigne());
        assertEquals(9, result.get().getColonne());
        assertEquals(1, result.get().getIdMap());
        verify(mapDAO).findById(1);

        System.out.println("✅ Succès: Map trouvée et convertie correctement");
        System.out.println("   - ID: " + result.get().getIdMap());
        System.out.println("   - Dimensions: " + result.get().getLigne() + "×" + result.get().getColonne());
    }

    @Test
    @DisplayName("findById devrait retourner Optional vide quand la map n'existe pas")
    void findById_whenMapDoesNotExist_shouldReturnEmptyOptional() {
        System.out.println("\n🔍 TEST: findById - Tentative de récupération d'une map inexistante");

        // Arrange
        System.out.println("🔧 Configuration: MapDAO configuré pour retourner Optional.empty()");
        when(mapDAO.findById(999)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de mapRepository.findById(999)");
        Optional<Map> result = mapRepository.findById(999);

        // Assert
        assertFalse(result.isPresent());
        verify(mapDAO).findById(999);

        System.out.println("✅ Succès: Optional vide retourné comme attendu");
    }

    @Test
    @DisplayName("isReferencedByZombies devrait vérifier si une map est référencée")
    void isReferencedByZombies_shouldCheckIfMapIsReferenced() {
        System.out.println("\n🔍 TEST: isReferencedByZombies - Vérification si une map est référencée");

        // Arrange
        System.out.println("🔧 Configuration: MapDAO configuré pour retourner true");
        when(mapDAO.isReferencedByZombies(1)).thenReturn(true);

        // Act
        System.out.println("▶️ Exécution: appel de mapRepository.isReferencedByZombies(1)");
        boolean result = mapRepository.isReferencedByZombies(1);

        // Assert
        assertTrue(result);
        verify(mapDAO).isReferencedByZombies(1);

        System.out.println("✅ Succès: true retourné comme attendu");
    }

    @Test
    @DisplayName("save devrait insérer une nouvelle map quand idMap est null")
    void save_whenIdIsNull_shouldInsertNewMap() {
        System.out.println("\n🔍 TEST: save - Insertion d'une nouvelle map (idMap null)");

        // Arrange
        Map mapToSave = new Map();
        mapToSave.setLigne(4);
        mapToSave.setColonne(8);
        mapToSave.setCheminImage("images/map/nouvelle.png");

        MapEntity savedEntity = new MapEntity();
        savedEntity.setIdMap(3);
        savedEntity.setLigne(4);
        savedEntity.setColonne(8);
        savedEntity.setCheminImage("images/map/nouvelle.png");

        System.out.println("🔧 Configuration: MapDAO configuré pour simuler une insertion réussie");
        when(mapDAO.insert(any(MapEntity.class))).thenReturn(savedEntity);

        // Act
        System.out.println("▶️ Exécution: appel de mapRepository.save() avec une nouvelle map");
        Map result = mapRepository.save(mapToSave);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getIdMap());
        assertEquals(4, result.getLigne());
        assertEquals(8, result.getColonne());
        verify(mapDAO).insert(any(MapEntity.class));
        verify(mapDAO, never()).update(any(MapEntity.class));

        System.out.println("✅ Succès: Nouvelle map insérée et convertie correctement");
        System.out.println("   - ID généré: " + result.getIdMap());
        System.out.println("   - Dimensions: " + result.getLigne() + "×" + result.getColonne());
    }

    @Test
    @DisplayName("save devrait mettre à jour une map existante quand idMap n'est pas null")
    void save_whenIdIsNotNull_shouldUpdateMap() {
        System.out.println("\n🔍 TEST: save - Mise à jour d'une map existante (idMap non null)");

        // Arrange
        Map mapToUpdate = new Map();
        mapToUpdate.setIdMap(2);
        mapToUpdate.setLigne(7);
        mapToUpdate.setColonne(10);
        mapToUpdate.setCheminImage("images/map/modifiee.png");

        MapEntity updatedEntity = new MapEntity();
        updatedEntity.setIdMap(2);
        updatedEntity.setLigne(7);
        updatedEntity.setColonne(10);
        updatedEntity.setCheminImage("images/map/modifiee.png");

        System.out.println("🔧 Configuration: MapDAO configuré pour simuler une mise à jour réussie");
        when(mapDAO.update(any(MapEntity.class))).thenReturn(updatedEntity);

        // Act
        System.out.println("▶️ Exécution: appel de mapRepository.save() avec une map existante");
        Map result = mapRepository.save(mapToUpdate);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getIdMap());
        assertEquals(7, result.getLigne());
        assertEquals(10, result.getColonne());
        verify(mapDAO, never()).insert(any(MapEntity.class));
        verify(mapDAO).update(any(MapEntity.class));

        System.out.println("✅ Succès: Map mise à jour et convertie correctement");
        System.out.println("   - ID: " + result.getIdMap());
        System.out.println("   - Nouvelles dimensions: " + result.getLigne() + "×" + result.getColonne());
    }

    @Test
    @DisplayName("deleteById devrait supprimer une map par son ID")
    void deleteById_shouldDeleteMap() {
        System.out.println("\n🔍 TEST: deleteById - Suppression d'une map");

        // Arrange
        System.out.println("🔧 Configuration: MapDAO configuré pour simuler une suppression réussie");
        when(mapDAO.deleteById(1)).thenReturn(true);

        // Act
        System.out.println("▶️ Exécution: appel de mapRepository.deleteById(1)");
        boolean result = mapRepository.deleteById(1);

        // Assert
        assertTrue(result);
        verify(mapDAO).deleteById(1);

        System.out.println("✅ Succès: true retourné, indiquant une suppression réussie");
    }

    @Test
    @DisplayName("deleteById devrait retourner false quand la map n'existe pas ou est référencée")
    void deleteById_whenMapDoesNotExistOrIsReferenced_shouldReturnFalse() {
        System.out.println("\n🔍 TEST: deleteById - Tentative de suppression d'une map inexistante");

        // Arrange
        System.out.println("🔧 Configuration: MapDAO configuré pour simuler une suppression échouée");
        when(mapDAO.deleteById(999)).thenReturn(false);

        // Act
        System.out.println("▶️ Exécution: appel de mapRepository.deleteById(999)");
        boolean result = mapRepository.deleteById(999);

        // Assert
        assertFalse(result);
        verify(mapDAO).deleteById(999);

        System.out.println("✅ Succès: false retourné, indiquant que la map n'a pas été trouvée ou est référencée");
    }
}