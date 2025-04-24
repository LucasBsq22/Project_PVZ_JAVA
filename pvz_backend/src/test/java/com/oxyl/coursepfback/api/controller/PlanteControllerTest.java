package com.oxyl.coursepfback.api.controller;

import com.oxyl.coursepfback.api.dto.PlantDTO;
import com.oxyl.coursepfback.api.mapper.PlantDTOMapper;
import com.oxyl.coursepfback.core.model.Plant;
import com.oxyl.coursepfback.core.service.PlantService;
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

class PlantControllerTest {

    private PlantController plantController;
    private PlantService plantService;

    @BeforeEach
    void setUp() {
        System.out.println("\n----------- Configuration du test PlantController -----------");
        plantService = Mockito.mock(PlantService.class);
        plantController = new PlantController(plantService);
        System.out.println("✅ Mock PlantService injecté avec succès dans PlantController");
    }

    @Test
    @DisplayName("getAllPlants devrait retourner une liste de PlantDTO")
    void getAllPlants_shouldReturnListOfPlantDTOs() {
        System.out.println("\n🔍 TEST: getAllPlants - Récupération de toutes les plantes");

        // Arrange
        Plant plant1 = new Plant();
        plant1.setIdPlante(1);
        plant1.setNom("Tournesol");
        plant1.setPointDeVie(100);
        plant1.setAttaqueParSeconde(0.0);
        plant1.setDegatAttaque(0);
        plant1.setCout(50);
        plant1.setSoleilParSeconde(25.0);
        plant1.setEffet("normal");
        plant1.setCheminImage("images/plante/tournesol.png");

        Plant plant2 = new Plant();
        plant2.setIdPlante(2);
        plant2.setNom("Pois Tireur");
        plant2.setPointDeVie(150);
        plant2.setAttaqueParSeconde(1.5);
        plant2.setDegatAttaque(20);
        plant2.setCout(100);
        plant2.setSoleilParSeconde(0.0);
        plant2.setEffet("normal");
        plant2.setCheminImage("images/plante/poistireur.png");

        List<Plant> plants = Arrays.asList(plant1, plant2);

        System.out.println("🔧 Configuration: PlantService configuré pour retourner une liste de 2 plantes");
        when(plantService.getAllPlants()).thenReturn(plants);

        // Act
        System.out.println("▶️ Exécution: appel de plantController.getAllPlants()");
        ResponseEntity<List<PlantDTO>> response = plantController.getAllPlants();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Tournesol", response.getBody().get(0).getNom());
        assertEquals("Pois Tireur", response.getBody().get(1).getNom());
        verify(plantService).getAllPlants();

        System.out.println("✅ Succès: " + response.getBody().size() + " PlantDTO retournés avec code HTTP 200 OK");
        System.out.println("   - PlantDTO 1: " + response.getBody().get(0).getNom() + " (ID: " + response.getBody().get(0).getId_plante() + ")");
        System.out.println("   - PlantDTO 2: " + response.getBody().get(1).getNom() + " (ID: " + response.getBody().get(1).getId_plante() + ")");
    }

    @Test
    @DisplayName("getPlantById devrait retourner le PlantDTO correspondant quand il existe")
    void getPlantById_whenPlantExists_shouldReturnPlantDTO() {
        System.out.println("\n🔍 TEST: getPlantById - Récupération d'une plante existante");

        // Arrange
        Plant plant = new Plant();
        plant.setIdPlante(1);
        plant.setNom("Tournesol");
        plant.setPointDeVie(100);
        plant.setAttaqueParSeconde(0.0);
        plant.setDegatAttaque(0);
        plant.setCout(50);
        plant.setSoleilParSeconde(25.0);
        plant.setEffet("normal");
        plant.setCheminImage("images/plante/tournesol.png");

        System.out.println("🔧 Configuration: PlantService configuré pour retourner une plante");
        when(plantService.getPlantById(1)).thenReturn(Optional.of(plant));

        // Act
        System.out.println("▶️ Exécution: appel de plantController.getPlantById(1)");
        ResponseEntity<PlantDTO> response = plantController.getPlantById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Tournesol", response.getBody().getNom());
        assertEquals(1, response.getBody().getId_plante());
        verify(plantService).getPlantById(1);

        System.out.println("✅ Succès: PlantDTO retourné avec code HTTP 200 OK");
        System.out.println("   - Nom: " + response.getBody().getNom());
        System.out.println("   - ID: " + response.getBody().getId_plante());
    }

    @Test
    @DisplayName("getPlantById devrait retourner 404 quand la plante n'existe pas")
    void getPlantById_whenPlantDoesNotExist_shouldReturn404() {
        System.out.println("\n🔍 TEST: getPlantById - Tentative de récupération d'une plante inexistante");

        // Arrange
        System.out.println("🔧 Configuration: PlantService configuré pour retourner Optional.empty()");
        when(plantService.getPlantById(999)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de plantController.getPlantById(999)");
        ResponseEntity<PlantDTO> response = plantController.getPlantById(999);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(plantService).getPlantById(999);

        System.out.println("✅ Succès: 404 NOT FOUND retourné comme attendu");
    }

    @Test
    @DisplayName("createPlant devrait créer une plante et retourner 201 CREATED")
    void createPlant_withValidPlant_shouldReturn201() {
        System.out.println("\n🔍 TEST: createPlant - Création d'une nouvelle plante valide");

        // Arrange
        PlantDTO inputDto = new PlantDTO();
        inputDto.setNom("Nouvelle Plante");
        inputDto.setPoint_de_vie(100);
        inputDto.setAttaque_par_seconde(1.0);
        inputDto.setDegat_attaque(15);
        inputDto.setCout(75);
        inputDto.setSoleil_par_seconde(0.0);
        inputDto.setEffet("normal");
        inputDto.setChemin_image("images/plante/nouvelle.png");

        Plant convertedPlant = inputDto.toPlant();

        Plant savedPlant = new Plant();
        savedPlant.setIdPlante(3);
        savedPlant.setNom("Nouvelle Plante");
        savedPlant.setPointDeVie(100);
        savedPlant.setAttaqueParSeconde(1.0);
        savedPlant.setDegatAttaque(15);
        savedPlant.setCout(75);
        savedPlant.setSoleilParSeconde(0.0);
        savedPlant.setEffet("normal");
        savedPlant.setCheminImage("images/plante/nouvelle.png");

        System.out.println("🔧 Configuration: PlantService configuré pour valider et sauvegarder une plante");
        when(plantService.validatePlantFormat(any(Plant.class))).thenReturn(true);
        when(plantService.savePlant(any(Plant.class))).thenReturn(savedPlant);

        // Act
        System.out.println("▶️ Exécution: appel de plantController.createPlant()");
        ResponseEntity<PlantDTO> response = plantController.createPlant(inputDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().getId_plante());
        assertEquals("Nouvelle Plante", response.getBody().getNom());
        verify(plantService).validatePlantFormat(any(Plant.class));
        verify(plantService).savePlant(any(Plant.class));

        System.out.println("✅ Succès: PlantDTO retourné avec code HTTP 201 CREATED");
        System.out.println("   - ID généré: " + response.getBody().getId_plante());
        System.out.println("   - Nom: " + response.getBody().getNom());
    }

    @Test
    @DisplayName("createPlant devrait retourner 400 BAD REQUEST pour une plante invalide")
    void createPlant_withInvalidPlant_shouldReturn400() {
        System.out.println("\n🔍 TEST: createPlant - Tentative de création d'une plante invalide");

        // Arrange
        PlantDTO invalidDto = new PlantDTO();
        invalidDto.setPoint_de_vie(100);
        // Nom manquant

        System.out.println("🔧 Configuration: PlantService configuré pour invalider la plante");
        when(plantService.validatePlantFormat(any(Plant.class))).thenReturn(false);

        // Act
        System.out.println("▶️ Exécution: appel de plantController.createPlant() avec une plante invalide");
        ResponseEntity<PlantDTO> response = plantController.createPlant(invalidDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(plantService).validatePlantFormat(any(Plant.class));
        verify(plantService, never()).savePlant(any(Plant.class));

        System.out.println("✅ Succès: 400 BAD REQUEST retourné comme attendu");
    }

    @Test
    @DisplayName("updatePlant devrait mettre à jour une plante existante")
    void updatePlant_whenPlantExists_shouldUpdateAndReturn200() {
        System.out.println("\n🔍 TEST: updatePlant - Mise à jour d'une plante existante");

        // Arrange
        int plantId = 1;

        Plant existingPlant = new Plant();
        existingPlant.setIdPlante(plantId);
        existingPlant.setNom("Tournesol");
        existingPlant.setPointDeVie(100);

        PlantDTO updateDto = new PlantDTO();
        updateDto.setNom("Tournesol Modifié");
        updateDto.setPoint_de_vie(120);

        Plant updatedPlant = new Plant();
        updatedPlant.setIdPlante(plantId);
        updatedPlant.setNom("Tournesol Modifié");
        updatedPlant.setPointDeVie(120);

        System.out.println("🔧 Configuration: PlantService configuré pour trouver et mettre à jour une plante");
        when(plantService.getPlantById(plantId)).thenReturn(Optional.of(existingPlant));
        when(plantService.savePlant(any(Plant.class))).thenReturn(updatedPlant);

        // Act
        System.out.println("▶️ Exécution: appel de plantController.updatePlant(" + plantId + ")");
        ResponseEntity<PlantDTO> response = plantController.updatePlant(plantId, updateDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Tournesol Modifié", response.getBody().getNom());
        assertEquals(120, response.getBody().getPoint_de_vie());
        verify(plantService).getPlantById(plantId);
        verify(plantService).savePlant(any(Plant.class));

        System.out.println("✅ Succès: PlantDTO mis à jour retourné avec code HTTP 200 OK");
        System.out.println("   - Nom modifié: " + response.getBody().getNom());
        System.out.println("   - Points de vie modifiés: " + response.getBody().getPoint_de_vie());
    }

    @Test
    @DisplayName("updatePlant devrait retourner 404 quand la plante n'existe pas")
    void updatePlant_whenPlantDoesNotExist_shouldReturn404() {
        System.out.println("\n🔍 TEST: updatePlant - Tentative de mise à jour d'une plante inexistante");

        // Arrange
        int plantId = 999;

        PlantDTO updateDto = new PlantDTO();
        updateDto.setNom("Plante Inexistante");

        System.out.println("🔧 Configuration: PlantService configuré pour ne pas trouver la plante");
        when(plantService.getPlantById(plantId)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de plantController.updatePlant(" + plantId + ")");
        ResponseEntity<PlantDTO> response = plantController.updatePlant(plantId, updateDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(plantService).getPlantById(plantId);
        verify(plantService, never()).savePlant(any(Plant.class));

        System.out.println("✅ Succès: 404 NOT FOUND retourné comme attendu");
    }

    @Test
    @DisplayName("deletePlant devrait supprimer une plante existante et retourner 204")
    void deletePlant_whenPlantExists_shouldReturn204() {
        System.out.println("\n🔍 TEST: deletePlant - Suppression d'une plante existante");

        // Arrange
        int plantId = 1;

        System.out.println("🔧 Configuration: PlantService configuré pour trouver et supprimer une plante");
        when(plantService.getPlantById(plantId)).thenReturn(Optional.of(new Plant()));
        when(plantService.deletePlant(plantId)).thenReturn(true);

        // Act
        System.out.println("▶️ Exécution: appel de plantController.deletePlant(" + plantId + ")");
        ResponseEntity<Void> response = plantController.deletePlant(plantId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(plantService).getPlantById(plantId);
        verify(plantService).deletePlant(plantId);

        System.out.println("✅ Succès: 204 NO CONTENT retourné comme attendu");
    }

    @Test
    @DisplayName("deletePlant devrait retourner 404 quand la plante n'existe pas")
    void deletePlant_whenPlantDoesNotExist_shouldReturn404() {
        System.out.println("\n🔍 TEST: deletePlant - Tentative de suppression d'une plante inexistante");

        // Arrange
        int plantId = 999;

        System.out.println("🔧 Configuration: PlantService configuré pour ne pas trouver la plante");
        when(plantService.getPlantById(plantId)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de plantController.deletePlant(" + plantId + ")");
        ResponseEntity<Void> response = plantController.deletePlant(plantId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(plantService).getPlantById(plantId);
        verify(plantService, never()).deletePlant(anyInt());

        System.out.println("✅ Succès: 404 NOT FOUND retourné comme attendu");
    }

    @Test
    @DisplayName("deletePlant devrait retourner 500 en cas d'erreur interne")
    void deletePlant_whenDeleteFails_shouldReturn500() {
        System.out.println("\n🔍 TEST: deletePlant - Erreur lors de la suppression d'une plante");

        // Arrange
        int plantId = 1;

        System.out.println("🔧 Configuration: PlantService configuré pour trouver mais échouer à supprimer une plante");
        when(plantService.getPlantById(plantId)).thenReturn(Optional.of(new Plant()));
        when(plantService.deletePlant(plantId)).thenReturn(false);

        // Act
        System.out.println("▶️ Exécution: appel de plantController.deletePlant(" + plantId + ")");
        ResponseEntity<Void> response = plantController.deletePlant(plantId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(plantService).getPlantById(plantId);
        verify(plantService).deletePlant(plantId);

        System.out.println("✅ Succès: 500 INTERNAL SERVER ERROR retourné comme attendu");
    }
}