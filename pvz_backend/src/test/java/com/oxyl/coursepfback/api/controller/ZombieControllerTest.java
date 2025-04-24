package com.oxyl.coursepfback.api.controller;

import com.oxyl.coursepfback.api.dto.ZombieDTO;
import com.oxyl.coursepfback.api.mapper.ZombieDTOMapper;
import com.oxyl.coursepfback.core.model.Zombie;
import com.oxyl.coursepfback.core.service.ZombieService;
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

class ZombieControllerTest {

    private ZombieController zombieController;
    private ZombieService zombieService;

    @BeforeEach
    void setUp() {
        System.out.println("\n----------- Configuration du test ZombieController -----------");
        zombieService = Mockito.mock(ZombieService.class);
        zombieController = new ZombieController(zombieService);
        System.out.println("✅ Mock ZombieService injecté avec succès dans ZombieController");
    }

    @Test
    @DisplayName("getAllZombies devrait retourner une liste de ZombieDTO")
    void getAllZombies_shouldReturnListOfZombieDTOs() {
        System.out.println("\n🔍 TEST: getAllZombies - Récupération de tous les zombies");

        // Arrange
        Zombie zombie1 = new Zombie();
        zombie1.setIdZombie(1);
        zombie1.setNom("Zombie de base");
        zombie1.setPointDeVie(100);
        zombie1.setAttaqueParSeconde(0.8);
        zombie1.setDegatAttaque(10);
        zombie1.setVitesseDeDeplacement(0.5);
        zombie1.setCheminImage("images/zombie/zombie.png");
        zombie1.setIdMap(1);

        Zombie zombie2 = new Zombie();
        zombie2.setIdZombie(2);
        zombie2.setNom("Zombie Cone");
        zombie2.setPointDeVie(200);
        zombie2.setAttaqueParSeconde(0.8);
        zombie2.setDegatAttaque(10);
        zombie2.setVitesseDeDeplacement(0.45);
        zombie2.setCheminImage("images/zombie/conehead.png");
        zombie2.setIdMap(1);

        List<Zombie> zombies = Arrays.asList(zombie1, zombie2);

        System.out.println("🔧 Configuration: ZombieService configuré pour retourner une liste de 2 zombies");
        when(zombieService.getAllZombies()).thenReturn(zombies);

        // Act
        System.out.println("▶️ Exécution: appel de zombieController.getAllZombies()");
        ResponseEntity<List<ZombieDTO>> response = zombieController.getAllZombies();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Zombie de base", response.getBody().get(0).getNom());
        assertEquals("Zombie Cone", response.getBody().get(1).getNom());
        verify(zombieService).getAllZombies();

        System.out.println("✅ Succès: " + response.getBody().size() + " ZombieDTO retournés avec code HTTP 200 OK");
        System.out.println("   - ZombieDTO 1: " + response.getBody().get(0).getNom() + " (ID: " + response.getBody().get(0).getId_zombie() + ")");
        System.out.println("   - ZombieDTO 2: " + response.getBody().get(1).getNom() + " (ID: " + response.getBody().get(1).getId_zombie() + ")");
    }

    @Test
    @DisplayName("getZombieById devrait retourner le ZombieDTO correspondant quand il existe")
    void getZombieById_whenZombieExists_shouldReturnZombieDTO() {
        System.out.println("\n🔍 TEST: getZombieById - Récupération d'un zombie existant");

        // Arrange
        Zombie zombie = new Zombie();
        zombie.setIdZombie(1);
        zombie.setNom("Zombie de base");
        zombie.setPointDeVie(100);
        zombie.setAttaqueParSeconde(0.8);
        zombie.setDegatAttaque(10);
        zombie.setVitesseDeDeplacement(0.5);
        zombie.setCheminImage("images/zombie/zombie.png");
        zombie.setIdMap(1);

        System.out.println("🔧 Configuration: ZombieService configuré pour retourner un zombie");
        when(zombieService.getZombieById(1)).thenReturn(Optional.of(zombie));

        // Act
        System.out.println("▶️ Exécution: appel de zombieController.getZombieById(1)");
        ResponseEntity<ZombieDTO> response = zombieController.getZombieById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Zombie de base", response.getBody().getNom());
        assertEquals(1, response.getBody().getId_zombie());
        verify(zombieService).getZombieById(1);

        System.out.println("✅ Succès: ZombieDTO retourné avec code HTTP 200 OK");
        System.out.println("   - Nom: " + response.getBody().getNom());
        System.out.println("   - ID: " + response.getBody().getId_zombie());
    }

    @Test
    @DisplayName("getZombieById devrait retourner 404 quand le zombie n'existe pas")
    void getZombieById_whenZombieDoesNotExist_shouldReturn404() {
        System.out.println("\n🔍 TEST: getZombieById - Tentative de récupération d'un zombie inexistant");

        // Arrange
        System.out.println("🔧 Configuration: ZombieService configuré pour retourner Optional.empty()");
        when(zombieService.getZombieById(999)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de zombieController.getZombieById(999)");
        ResponseEntity<ZombieDTO> response = zombieController.getZombieById(999);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(zombieService).getZombieById(999);

        System.out.println("✅ Succès: 404 NOT FOUND retourné comme attendu");
    }

    @Test
    @DisplayName("getZombiesByMapId devrait retourner une liste de ZombieDTO")
    void getZombiesByMapId_shouldReturnListOfZombieDTOs() {
        System.out.println("\n🔍 TEST: getZombiesByMapId - Récupération des zombies pour une map spécifique");

        // Arrange
        Zombie zombie1 = new Zombie();
        zombie1.setIdZombie(1);
        zombie1.setNom("Zombie de base");
        zombie1.setIdMap(1);

        Zombie zombie2 = new Zombie();
        zombie2.setIdZombie(3);
        zombie2.setNom("Zombie Seau");
        zombie2.setIdMap(1);

        List<Zombie> zombies = Arrays.asList(zombie1, zombie2);

        System.out.println("🔧 Configuration: ZombieService configuré pour retourner une liste de 2 zombies");
        when(zombieService.getZombiesByMapId(1)).thenReturn(zombies);

        // Act
        System.out.println("▶️ Exécution: appel de zombieController.getZombiesByMapId(1)");
        ResponseEntity<List<ZombieDTO>> response = zombieController.getZombiesByMapId(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Zombie de base", response.getBody().get(0).getNom());
        assertEquals("Zombie Seau", response.getBody().get(1).getNom());
        verify(zombieService).getZombiesByMapId(1);

        System.out.println("✅ Succès: " + response.getBody().size() + " ZombieDTO retournés avec code HTTP 200 OK");
        System.out.println("   - ZombieDTO 1: " + response.getBody().get(0).getNom());
        System.out.println("   - ZombieDTO 2: " + response.getBody().get(1).getNom());
    }

    @Test
    @DisplayName("createZombie devrait créer un zombie et retourner 201 CREATED")
    void createZombie_withValidZombie_shouldReturn201() {
        System.out.println("\n🔍 TEST: createZombie - Création d'un nouveau zombie valide");

        // Arrange
        ZombieDTO inputDto = new ZombieDTO();
        inputDto.setNom("Nouveau Zombie");
        inputDto.setPoint_de_vie(120);
        inputDto.setAttaque_par_seconde(0.9);
        inputDto.setDegat_attaque(12);
        inputDto.setVitesse_de_deplacement(0.55);
        inputDto.setChemin_image("images/zombie/nouveau.png");
        inputDto.setId_map(2);

        Zombie convertedZombie = ZombieDTOMapper.toModel(inputDto);

        Zombie savedZombie = new Zombie();
        savedZombie.setIdZombie(5);
        savedZombie.setNom("Nouveau Zombie");
        savedZombie.setPointDeVie(120);
        savedZombie.setAttaqueParSeconde(0.9);
        savedZombie.setDegatAttaque(12);
        savedZombie.setVitesseDeDeplacement(0.55);
        savedZombie.setCheminImage("images/zombie/nouveau.png");
        savedZombie.setIdMap(2);

        System.out.println("🔧 Configuration: ZombieService configuré pour valider et sauvegarder un zombie");
        when(zombieService.validateZombieFormat(any(Zombie.class))).thenReturn(true);
        when(zombieService.saveZombie(any(Zombie.class))).thenReturn(savedZombie);

        // Act
        System.out.println("▶️ Exécution: appel de zombieController.createZombie()");
        ResponseEntity<ZombieDTO> response = zombieController.createZombie(inputDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(5, response.getBody().getId_zombie());
        assertEquals("Nouveau Zombie", response.getBody().getNom());
        verify(zombieService).validateZombieFormat(any(Zombie.class));
        verify(zombieService).saveZombie(any(Zombie.class));

        System.out.println("✅ Succès: ZombieDTO retourné avec code HTTP 201 CREATED");
        System.out.println("   - ID généré: " + response.getBody().getId_zombie());
        System.out.println("   - Nom: " + response.getBody().getNom());
    }

    @Test
    @DisplayName("createZombie devrait retourner 400 BAD REQUEST pour un zombie invalide")
    void createZombie_withInvalidZombie_shouldReturn400() {
        System.out.println("\n🔍 TEST: createZombie - Tentative de création d'un zombie invalide");

        // Arrange
        ZombieDTO invalidDto = new ZombieDTO();
        invalidDto.setPoint_de_vie(100);
        // Nom manquant

        System.out.println("🔧 Configuration: ZombieService configuré pour invalider le zombie");
        when(zombieService.validateZombieFormat(any(Zombie.class))).thenReturn(false);

        // Act
        System.out.println("▶️ Exécution: appel de zombieController.createZombie() avec un zombie invalide");
        ResponseEntity<ZombieDTO> response = zombieController.createZombie(invalidDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(zombieService).validateZombieFormat(any(Zombie.class));
        verify(zombieService, never()).saveZombie(any(Zombie.class));

        System.out.println("✅ Succès: 400 BAD REQUEST retourné comme attendu");
    }

    @Test
    @DisplayName("updateZombie devrait mettre à jour un zombie existant")
    void updateZombie_whenZombieExists_shouldUpdateAndReturn200() {
        System.out.println("\n🔍 TEST: updateZombie - Mise à jour d'un zombie existant");

        // Arrange
        int zombieId = 1;

        Zombie existingZombie = new Zombie();
        existingZombie.setIdZombie(zombieId);
        existingZombie.setNom("Zombie de base");
        existingZombie.setPointDeVie(100);

        ZombieDTO updateDto = new ZombieDTO();
        updateDto.setNom("Zombie Modifié");
        updateDto.setPoint_de_vie(120);

        Zombie updatedZombie = new Zombie();
        updatedZombie.setIdZombie(zombieId);
        updatedZombie.setNom("Zombie Modifié");
        updatedZombie.setPointDeVie(120);

        System.out.println("🔧 Configuration: ZombieService configuré pour trouver et mettre à jour un zombie");
        when(zombieService.getZombieById(zombieId)).thenReturn(Optional.of(existingZombie));
        when(zombieService.saveZombie(any(Zombie.class))).thenReturn(updatedZombie);

        // Act
        System.out.println("▶️ Exécution: appel de zombieController.updateZombie(" + zombieId + ")");
        ResponseEntity<ZombieDTO> response = zombieController.updateZombie(zombieId, updateDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Zombie Modifié", response.getBody().getNom());
        assertEquals(120, response.getBody().getPoint_de_vie());
        verify(zombieService).getZombieById(zombieId);
        verify(zombieService).saveZombie(any(Zombie.class));

        System.out.println("✅ Succès: ZombieDTO mis à jour retourné avec code HTTP 200 OK");
        System.out.println("   - Nom modifié: " + response.getBody().getNom());
        System.out.println("   - Points de vie modifiés: " + response.getBody().getPoint_de_vie());
    }

    @Test
    @DisplayName("updateZombie devrait retourner 404 quand le zombie n'existe pas")
    void updateZombie_whenZombieDoesNotExist_shouldReturn404() {
        System.out.println("\n🔍 TEST: updateZombie - Tentative de mise à jour d'un zombie inexistant");

        // Arrange
        int zombieId = 999;

        ZombieDTO updateDto = new ZombieDTO();
        updateDto.setNom("Zombie Inexistant");

        System.out.println("🔧 Configuration: ZombieService configuré pour ne pas trouver le zombie");
        when(zombieService.getZombieById(zombieId)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de zombieController.updateZombie(" + zombieId + ")");
        ResponseEntity<ZombieDTO> response = zombieController.updateZombie(zombieId, updateDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(zombieService).getZombieById(zombieId);
        verify(zombieService, never()).saveZombie(any(Zombie.class));

        System.out.println("✅ Succès: 404 NOT FOUND retourné comme attendu");
    }

    @Test
    @DisplayName("deleteZombie devrait supprimer un zombie existant et retourner 204")
    void deleteZombie_whenZombieExists_shouldReturn204() {
        System.out.println("\n🔍 TEST: deleteZombie - Suppression d'un zombie existant");

        // Arrange
        int zombieId = 1;

        System.out.println("🔧 Configuration: ZombieService configuré pour trouver et supprimer un zombie");
        when(zombieService.getZombieById(zombieId)).thenReturn(Optional.of(new Zombie()));
        when(zombieService.deleteZombie(zombieId)).thenReturn(true);

        // Act
        System.out.println("▶️ Exécution: appel de zombieController.deleteZombie(" + zombieId + ")");
        ResponseEntity<Void> response = zombieController.deleteZombie(zombieId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(zombieService).getZombieById(zombieId);
        verify(zombieService).deleteZombie(zombieId);

        System.out.println("✅ Succès: 204 NO CONTENT retourné comme attendu");
    }

    @Test
    @DisplayName("deleteZombie devrait retourner 404 quand le zombie n'existe pas")
    void deleteZombie_whenZombieDoesNotExist_shouldReturn404() {
        System.out.println("\n🔍 TEST: deleteZombie - Tentative de suppression d'un zombie inexistant");

        // Arrange
        int zombieId = 999;

        System.out.println("🔧 Configuration: ZombieService configuré pour ne pas trouver le zombie");
        when(zombieService.getZombieById(zombieId)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de zombieController.deleteZombie(" + zombieId + ")");
        ResponseEntity<Void> response = zombieController.deleteZombie(zombieId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(zombieService).getZombieById(zombieId);
        verify(zombieService, never()).deleteZombie(anyInt());

        System.out.println("✅ Succès: 404 NOT FOUND retourné comme attendu");
    }

    @Test
    @DisplayName("deleteZombie devrait retourner 500 en cas d'erreur interne")
    void deleteZombie_whenDeleteFails_shouldReturn500() {
        System.out.println("\n🔍 TEST: deleteZombie - Erreur lors de la suppression d'un zombie");

        // Arrange
        int zombieId = 1;

        System.out.println("🔧 Configuration: ZombieService configuré pour trouver mais échouer à supprimer un zombie");
        when(zombieService.getZombieById(zombieId)).thenReturn(Optional.of(new Zombie()));
        when(zombieService.deleteZombie(zombieId)).thenReturn(false);

        // Act
        System.out.println("▶️ Exécution: appel de zombieController.deleteZombie(" + zombieId + ")");
        ResponseEntity<Void> response = zombieController.deleteZombie(zombieId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(zombieService).getZombieById(zombieId);
        verify(zombieService).deleteZombie(zombieId);

        System.out.println("✅ Succès: 500 INTERNAL SERVER ERROR retourné comme attendu");
    }
}