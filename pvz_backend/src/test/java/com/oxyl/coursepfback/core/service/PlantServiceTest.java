package com.oxyl.coursepfback.core.service;

import com.oxyl.coursepfback.core.model.Plant;
import com.oxyl.coursepfback.persistance.repository.PlantRepository;
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

class PlantServiceTest {

    private PlantService plantService;
    private PlantRepository plantRepository;

    @BeforeEach
    void setUp() {
        System.out.println("\n----------- Configuration du test PlantService -----------");
        plantRepository = Mockito.mock(PlantRepository.class);
        plantService = new PlantService(plantRepository);
        System.out.println("✅ Mock PlantRepository injecté avec succès dans PlantService");
    }

    @Test
    @DisplayName("getAllPlants devrait retourner toutes les plantes")
    void getAllPlants_shouldReturnAllPlants() {
        System.out.println("\n🔍 TEST: getAllPlants - Récupération de toutes les plantes");

        // Arrange
        Plant plant1 = new Plant();
        plant1.setIdPlante(1);
        plant1.setNom("Tournesol");

        Plant plant2 = new Plant();
        plant2.setIdPlante(2);
        plant2.setNom("Pois Tireur");

        List<Plant> expectedPlants = Arrays.asList(plant1, plant2);

        System.out.println("🔧 Configuration: PlantRepository configuré pour retourner une liste de 2 plantes");
        when(plantRepository.findAll()).thenReturn(expectedPlants);

        // Act
        System.out.println("▶️ Exécution: appel de plantService.getAllPlants()");
        List<Plant> result = plantService.getAllPlants();

        // Assert
        assertEquals(expectedPlants.size(), result.size());
        assertEquals(expectedPlants, result);
        verify(plantRepository).findAll();

        System.out.println("✅ Succès: " + result.size() + " plantes récupérées");
        System.out.println("   - Plante 1: " + result.get(0).getNom() + " (ID: " + result.get(0).getIdPlante() + ")");
        System.out.println("   - Plante 2: " + result.get(1).getNom() + " (ID: " + result.get(1).getIdPlante() + ")");
    }

    @Test
    @DisplayName("getPlantById devrait retourner la plante correspondante quand elle existe")
    void getPlantById_whenPlantExists_shouldReturnPlant() {
        System.out.println("\n🔍 TEST: getPlantById - Récupération d'une plante existante");

        // Arrange
        Plant expected = new Plant();
        expected.setIdPlante(1);
        expected.setNom("Tournesol");

        System.out.println("🔧 Configuration: PlantRepository configuré pour retourner une plante");
        when(plantRepository.findById(1)).thenReturn(Optional.of(expected));

        // Act
        System.out.println("▶️ Exécution: appel de plantService.getPlantById(1)");
        Optional<Plant> result = plantService.getPlantById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(plantRepository).findById(1);

        System.out.println("✅ Succès: Plante trouvée");
        System.out.println("   - Nom: " + result.get().getNom());
        System.out.println("   - ID: " + result.get().getIdPlante());
    }

    @Test
    @DisplayName("getPlantById devrait retourner Optional vide quand la plante n'existe pas")
    void getPlantById_whenPlantDoesNotExist_shouldReturnEmptyOptional() {
        System.out.println("\n🔍 TEST: getPlantById - Tentative de récupération d'une plante inexistante");

        // Arrange
        System.out.println("🔧 Configuration: PlantRepository configuré pour retourner Optional.empty()");
        when(plantRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de plantService.getPlantById(999)");
        Optional<Plant> result = plantService.getPlantById(999);

        // Assert
        assertFalse(result.isPresent());
        verify(plantRepository).findById(999);

        System.out.println("✅ Succès: Optional vide retourné comme attendu");
    }

    @Test
    @DisplayName("savePlant devrait déléguer l'appel au repository")
    void savePlant_shouldDelegateToRepository() {
        System.out.println("\n🔍 TEST: savePlant - Délégation au repository pour sauvegarder une plante");

        // Arrange
        Plant plantToSave = new Plant();
        plantToSave.setNom("Nouvelle Plante");

        Plant savedPlant = new Plant();
        savedPlant.setIdPlante(3);
        savedPlant.setNom("Nouvelle Plante");

        System.out.println("🔧 Configuration: PlantRepository configuré pour simuler une sauvegarde réussie");
        when(plantRepository.save(any(Plant.class))).thenReturn(savedPlant);

        // Act
        System.out.println("▶️ Exécution: appel de plantService.savePlant()");
        Plant result = plantService.savePlant(plantToSave);

        // Assert
        assertNotNull(result);
        assertEquals(savedPlant, result);
        verify(plantRepository).save(plantToSave);

        System.out.println("✅ Succès: Plante sauvegardée");
        System.out.println("   - ID généré: " + result.getIdPlante());
        System.out.println("   - Nom: " + result.getNom());
    }

    @Test
    @DisplayName("deletePlant devrait déléguer l'appel au repository")
    void deletePlant_shouldDelegateToRepository() {
        System.out.println("\n🔍 TEST: deletePlant - Délégation au repository pour supprimer une plante");

        // Arrange
        System.out.println("🔧 Configuration: PlantRepository configuré pour simuler une suppression réussie");
        when(plantRepository.deleteById(1)).thenReturn(true);

        // Act
        System.out.println("▶️ Exécution: appel de plantService.deletePlant(1)");
        boolean result = plantService.deletePlant(1);

        // Assert
        assertTrue(result);
        verify(plantRepository).deleteById(1);

        System.out.println("✅ Succès: true retourné, indiquant une suppression réussie");
    }

    @Test
    @DisplayName("validatePlantFormat devrait retourner true pour une plante valide")
    void validatePlantFormat_withValidPlant_shouldReturnTrue() {
        System.out.println("\n🔍 TEST: validatePlantFormat - Validation d'une plante valide");

        // Arrange
        Plant validPlant = new Plant();
        validPlant.setNom("Plante Valide");
        validPlant.setPointDeVie(100);
        validPlant.setAttaqueParSeconde(1.5);
        validPlant.setDegatAttaque(20);
        validPlant.setCout(150);
        validPlant.setSoleilParSeconde(0.0);
        validPlant.setEffet("normal");
        validPlant.setCheminImage("images/plante/valide.png");

        // Act
        System.out.println("▶️ Exécution: appel de plantService.validatePlantFormat() avec une plante valide");
        boolean result = plantService.validatePlantFormat(validPlant);

        // Assert
        assertTrue(result);

        System.out.println("✅ Succès: true retourné, indiquant que la plante est valide");
    }

    @Test
    @DisplayName("validatePlantFormat devrait retourner false pour une plante invalide")
    void validatePlantFormat_withInvalidPlant_shouldReturnFalse() {
        System.out.println("\n🔍 TEST: validatePlantFormat - Validation d'une plante invalide");

        // Arrange - plante sans nom
        Plant invalidPlant = new Plant();
        invalidPlant.setPointDeVie(100);
        invalidPlant.setAttaqueParSeconde(1.5);
        invalidPlant.setDegatAttaque(20);
        invalidPlant.setCout(150);
        invalidPlant.setSoleilParSeconde(0.0);
        invalidPlant.setEffet("normal");
        invalidPlant.setCheminImage("images/plante/invalide.png");

        // Act
        System.out.println("▶️ Exécution: appel de plantService.validatePlantFormat() avec une plante sans nom");
        boolean result = plantService.validatePlantFormat(invalidPlant);

        // Assert
        assertFalse(result);

        System.out.println("✅ Succès: false retourné, indiquant que la plante est invalide");

        // Test avec plante avec points de vie négatifs
        invalidPlant.setNom("Plante Invalide");
        invalidPlant.setPointDeVie(-10);

        System.out.println("▶️ Exécution: appel de plantService.validatePlantFormat() avec des points de vie négatifs");
        result = plantService.validatePlantFormat(invalidPlant);

        assertFalse(result);
        System.out.println("✅ Succès: false retourné pour des points de vie négatifs");

        // Test avec null
        System.out.println("▶️ Exécution: appel de plantService.validatePlantFormat() avec null");
        result = plantService.validatePlantFormat(null);

        assertFalse(result);
        System.out.println("✅ Succès: false retourné pour une plante null");
    }
}