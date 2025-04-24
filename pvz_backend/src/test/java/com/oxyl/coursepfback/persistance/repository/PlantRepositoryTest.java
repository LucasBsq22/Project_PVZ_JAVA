package com.oxyl.coursepfback.persistance.repository;

import com.oxyl.coursepfback.core.model.Plant;
import com.oxyl.coursepfback.persistance.dao.PlantDAO;
import com.oxyl.coursepfback.persistance.entity.PlantEntity;
import com.oxyl.coursepfback.persistance.mapper.PlantEntityMapper;
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

class PlantRepositoryTest {

    private PlantRepository plantRepository;
    private PlantDAO plantDAO;

    @BeforeEach
    void setUp() {
        System.out.println("\n----------- Configuration du test PlantRepository -----------");
        plantDAO = Mockito.mock(PlantDAO.class);
        plantRepository = new PlantRepository(plantDAO);
        System.out.println("✅ Mock PlantDAO injecté avec succès dans PlantRepository");
    }

    @Test
    @DisplayName("findAll devrait convertir les entités en modèles et les retourner")
    void findAll_shouldReturnAllPlants() {
        System.out.println("\n🔍 TEST: findAll - Conversion et récupération de toutes les plantes");

        // Arrange
        PlantEntity entity1 = new PlantEntity();
        entity1.setIdPlante(1);
        entity1.setNom("Tournesol");
        entity1.setPointDeVie(100);
        entity1.setAttaqueParSeconde(0.0);
        entity1.setDegatAttaque(0);
        entity1.setCout(50);
        entity1.setSoleilParSeconde(25.0);
        entity1.setEffet("normal");
        entity1.setCheminImage("images/plante/tournesol.png");

        PlantEntity entity2 = new PlantEntity();
        entity2.setIdPlante(2);
        entity2.setNom("Pois Tireur");
        entity2.setPointDeVie(150);
        entity2.setAttaqueParSeconde(1.5);
        entity2.setDegatAttaque(20);
        entity2.setCout(100);
        entity2.setSoleilParSeconde(0.0);
        entity2.setEffet("normal");
        entity2.setCheminImage("images/plante/poistireur.png");

        List<PlantEntity> entities = Arrays.asList(entity1, entity2);

        System.out.println("🔧 Configuration: PlantDAO configuré pour retourner une liste de 2 entités");
        when(plantDAO.findAll()).thenReturn(entities);

        // Act
        System.out.println("▶️ Exécution: appel de plantRepository.findAll()");
        List<Plant> result = plantRepository.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Tournesol", result.get(0).getNom());
        assertEquals("Pois Tireur", result.get(1).getNom());
        verify(plantDAO).findAll();

        System.out.println("✅ Succès: " + result.size() + " plantes récupérées et converties correctement");
        System.out.println("   - Plante 1: " + result.get(0).getNom() + " (ID: " + result.get(0).getIdPlante() + ")");
        System.out.println("   - Plante 2: " + result.get(1).getNom() + " (ID: " + result.get(1).getIdPlante() + ")");
    }

    @Test
    @DisplayName("findById devrait convertir l'entité en modèle quand elle existe")
    void findById_whenPlantExists_shouldReturnPlant() {
        System.out.println("\n🔍 TEST: findById - Conversion et récupération d'une plante existante");

        // Arrange
        PlantEntity entity = new PlantEntity();
        entity.setIdPlante(1);
        entity.setNom("Tournesol");
        entity.setPointDeVie(100);
        entity.setAttaqueParSeconde(0.0);
        entity.setDegatAttaque(0);
        entity.setCout(50);
        entity.setSoleilParSeconde(25.0);
        entity.setEffet("normal");
        entity.setCheminImage("images/plante/tournesol.png");

        System.out.println("🔧 Configuration: PlantDAO configuré pour retourner une entité");
        when(plantDAO.findById(1)).thenReturn(Optional.of(entity));

        // Act
        System.out.println("▶️ Exécution: appel de plantRepository.findById(1)");
        Optional<Plant> result = plantRepository.findById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Tournesol", result.get().getNom());
        assertEquals(1, result.get().getIdPlante());
        verify(plantDAO).findById(1);

        System.out.println("✅ Succès: Plante trouvée et convertie correctement");
        System.out.println("   - Nom: " + result.get().getNom());
        System.out.println("   - ID: " + result.get().getIdPlante());
    }

    @Test
    @DisplayName("findById devrait retourner Optional vide quand la plante n'existe pas")
    void findById_whenPlantDoesNotExist_shouldReturnEmptyOptional() {
        System.out.println("\n🔍 TEST: findById - Tentative de récupération d'une plante inexistante");

        // Arrange
        System.out.println("🔧 Configuration: PlantDAO configuré pour retourner Optional.empty()");
        when(plantDAO.findById(999)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de plantRepository.findById(999)");
        Optional<Plant> result = plantRepository.findById(999);

        // Assert
        assertFalse(result.isPresent());
        verify(plantDAO).findById(999);

        System.out.println("✅ Succès: Optional vide retourné comme attendu");
    }

    @Test
    @DisplayName("save devrait insérer une nouvelle plante quand idPlante est null")
    void save_whenIdIsNull_shouldInsertNewPlant() {
        System.out.println("\n🔍 TEST: save - Insertion d'une nouvelle plante (idPlante null)");

        // Arrange
        Plant plantToSave = new Plant();
        plantToSave.setNom("Nouvelle Plante");
        plantToSave.setPointDeVie(100);
        plantToSave.setAttaqueParSeconde(1.0);
        plantToSave.setDegatAttaque(15);
        plantToSave.setCout(75);
        plantToSave.setSoleilParSeconde(0.0);
        plantToSave.setEffet("normal");
        plantToSave.setCheminImage("images/plante/nouvelle.png");

        PlantEntity savedEntity = new PlantEntity();
        savedEntity.setIdPlante(3);
        savedEntity.setNom("Nouvelle Plante");
        savedEntity.setPointDeVie(100);
        savedEntity.setAttaqueParSeconde(1.0);
        savedEntity.setDegatAttaque(15);
        savedEntity.setCout(75);
        savedEntity.setSoleilParSeconde(0.0);
        savedEntity.setEffet("normal");
        savedEntity.setCheminImage("images/plante/nouvelle.png");

        System.out.println("🔧 Configuration: PlantDAO configuré pour simuler une insertion réussie");
        when(plantDAO.insert(any(PlantEntity.class))).thenReturn(savedEntity);

        // Act
        System.out.println("▶️ Exécution: appel de plantRepository.save() avec une nouvelle plante");
        Plant result = plantRepository.save(plantToSave);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getIdPlante());
        assertEquals("Nouvelle Plante", result.getNom());
        verify(plantDAO).insert(any(PlantEntity.class));
        verify(plantDAO, never()).update(any(PlantEntity.class));

        System.out.println("✅ Succès: Nouvelle plante insérée et convertie correctement");
        System.out.println("   - ID généré: " + result.getIdPlante());
        System.out.println("   - Nom: " + result.getNom());
    }

    @Test
    @DisplayName("save devrait mettre à jour une plante existante quand idPlante n'est pas null")
    void save_whenIdIsNotNull_shouldUpdatePlant() {
        System.out.println("\n🔍 TEST: save - Mise à jour d'une plante existante (idPlante non null)");

        // Arrange
        Plant plantToUpdate = new Plant();
        plantToUpdate.setIdPlante(2);
        plantToUpdate.setNom("Plante Modifiée");
        plantToUpdate.setPointDeVie(150);
        plantToUpdate.setAttaqueParSeconde(1.5);
        plantToUpdate.setDegatAttaque(25);
        plantToUpdate.setCout(120);
        plantToUpdate.setSoleilParSeconde(0.0);
        plantToUpdate.setEffet("normal");
        plantToUpdate.setCheminImage("images/plante/modifiee.png");

        PlantEntity updatedEntity = new PlantEntity();
        updatedEntity.setIdPlante(2);
        updatedEntity.setNom("Plante Modifiée");
        updatedEntity.setPointDeVie(150);
        updatedEntity.setAttaqueParSeconde(1.5);
        updatedEntity.setDegatAttaque(25);
        updatedEntity.setCout(120);
        updatedEntity.setSoleilParSeconde(0.0);
        updatedEntity.setEffet("normal");
        updatedEntity.setCheminImage("images/plante/modifiee.png");

        System.out.println("🔧 Configuration: PlantDAO configuré pour simuler une mise à jour réussie");
        when(plantDAO.update(any(PlantEntity.class))).thenReturn(updatedEntity);

        // Act
        System.out.println("▶️ Exécution: appel de plantRepository.save() avec une plante existante");
        Plant result = plantRepository.save(plantToUpdate);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getIdPlante());
        assertEquals("Plante Modifiée", result.getNom());
        verify(plantDAO, never()).insert(any(PlantEntity.class));
        verify(plantDAO).update(any(PlantEntity.class));

        System.out.println("✅ Succès: Plante mise à jour et convertie correctement");
        System.out.println("   - ID: " + result.getIdPlante());
        System.out.println("   - Nom: " + result.getNom());
    }

    @Test
    @DisplayName("deleteById devrait supprimer une plante par son ID")
    void deleteById_shouldDeletePlant() {
        System.out.println("\n🔍 TEST: deleteById - Suppression d'une plante");

        // Arrange
        System.out.println("🔧 Configuration: PlantDAO configuré pour simuler une suppression réussie");
        when(plantDAO.deleteById(1)).thenReturn(true);

        // Act
        System.out.println("▶️ Exécution: appel de plantRepository.deleteById(1)");
        boolean result = plantRepository.deleteById(1);

        // Assert
        assertTrue(result);
        verify(plantDAO).deleteById(1);

        System.out.println("✅ Succès: true retourné, indiquant une suppression réussie");
    }

    @Test
    @DisplayName("deleteById devrait retourner false quand la plante n'existe pas")
    void deleteById_whenPlantDoesNotExist_shouldReturnFalse() {
        System.out.println("\n🔍 TEST: deleteById - Tentative de suppression d'une plante inexistante");

        // Arrange
        System.out.println("🔧 Configuration: PlantDAO configuré pour simuler une suppression échouée");
        when(plantDAO.deleteById(999)).thenReturn(false);

        // Act
        System.out.println("▶️ Exécution: appel de plantRepository.deleteById(999)");
        boolean result = plantRepository.deleteById(999);

        // Assert
        assertFalse(result);
        verify(plantDAO).deleteById(999);

        System.out.println("✅ Succès: false retourné, indiquant que la plante n'a pas été trouvée");
    }
}