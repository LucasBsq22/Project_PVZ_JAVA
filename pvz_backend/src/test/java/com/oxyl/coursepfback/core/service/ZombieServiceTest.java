package com.oxyl.coursepfback.core.service;

import com.oxyl.coursepfback.core.model.Zombie;
import com.oxyl.coursepfback.persistance.repository.ZombieRepository;
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

class ZombieServiceTest {

    private ZombieService zombieService;
    private ZombieRepository zombieRepository;

    @BeforeEach
    void setUp() {
        System.out.println("\n----------- Configuration du test ZombieService -----------");
        zombieRepository = Mockito.mock(ZombieRepository.class);
        zombieService = new ZombieService(zombieRepository);
        System.out.println("✅ Mock ZombieRepository injecté avec succès dans ZombieService");
    }

    @Test
    @DisplayName("getAllZombies devrait retourner tous les zombies")
    void getAllZombies_shouldReturnAllZombies() {
        System.out.println("\n🔍 TEST: getAllZombies - Récupération de tous les zombies");

        // Arrange
        Zombie zombie1 = new Zombie();
        zombie1.setIdZombie(1);
        zombie1.setNom("Zombie de base");

        Zombie zombie2 = new Zombie();
        zombie2.setIdZombie(2);
        zombie2.setNom("Zombie Cone");

        List<Zombie> expectedZombies = Arrays.asList(zombie1, zombie2);

        System.out.println("🔧 Configuration: ZombieRepository configuré pour retourner une liste de 2 zombies");
        when(zombieRepository.findAll()).thenReturn(expectedZombies);

        // Act
        System.out.println("▶️ Exécution: appel de zombieService.getAllZombies()");
        List<Zombie> result = zombieService.getAllZombies();

        // Assert
        assertEquals(expectedZombies.size(), result.size());
        assertEquals(expectedZombies, result);
        verify(zombieRepository).findAll();

        System.out.println("✅ Succès: " + result.size() + " zombies récupérés");
        System.out.println("   - Zombie 1: " + result.get(0).getNom() + " (ID: " + result.get(0).getIdZombie() + ")");
        System.out.println("   - Zombie 2: " + result.get(1).getNom() + " (ID: " + result.get(1).getIdZombie() + ")");
    }

    @Test
    @DisplayName("getZombieById devrait retourner le zombie correspondant quand il existe")
    void getZombieById_whenZombieExists_shouldReturnZombie() {
        System.out.println("\n🔍 TEST: getZombieById - Récupération d'un zombie existant");

        // Arrange
        Zombie expected = new Zombie();
        expected.setIdZombie(1);
        expected.setNom("Zombie de base");

        System.out.println("🔧 Configuration: ZombieRepository configuré pour retourner un zombie");
        when(zombieRepository.findById(1)).thenReturn(Optional.of(expected));

        // Act
        System.out.println("▶️ Exécution: appel de zombieService.getZombieById(1)");
        Optional<Zombie> result = zombieService.getZombieById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(zombieRepository).findById(1);

        System.out.println("✅ Succès: Zombie trouvé");
        System.out.println("   - Nom: " + result.get().getNom());
        System.out.println("   - ID: " + result.get().getIdZombie());
    }

    @Test
    @DisplayName("getZombieById devrait retourner Optional vide quand le zombie n'existe pas")
    void getZombieById_whenZombieDoesNotExist_shouldReturnEmptyOptional() {
        System.out.println("\n🔍 TEST: getZombieById - Tentative de récupération d'un zombie inexistant");

        // Arrange
        System.out.println("🔧 Configuration: ZombieRepository configuré pour retourner Optional.empty()");
        when(zombieRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de zombieService.getZombieById(999)");
        Optional<Zombie> result = zombieService.getZombieById(999);

        // Assert
        assertFalse(result.isPresent());
        verify(zombieRepository).findById(999);

        System.out.println("✅ Succès: Optional vide retourné comme attendu");
    }

    @Test
    @DisplayName("getZombiesByMapId devrait retourner les zombies pour une map spécifique")
    void getZombiesByMapId_shouldReturnZombiesForMap() {
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

        List<Zombie> expectedZombies = Arrays.asList(zombie1, zombie2);

        System.out.println("🔧 Configuration: ZombieRepository configuré pour retourner une liste de 2 zombies");
        when(zombieRepository.findByMapId(1)).thenReturn(expectedZombies);

        // Act
        System.out.println("▶️ Exécution: appel de zombieService.getZombiesByMapId(1)");
        List<Zombie> result = zombieService.getZombiesByMapId(1);

        // Assert
        assertEquals(expectedZombies.size(), result.size());
        assertEquals(expectedZombies, result);
        verify(zombieRepository).findByMapId(1);

        System.out.println("✅ Succès: " + result.size() + " zombies récupérés pour la map ID=1");
        System.out.println("   - Zombie 1: " + result.get(0).getNom() + " (ID: " + result.get(0).getIdZombie() + ")");
        System.out.println("   - Zombie 2: " + result.get(1).getNom() + " (ID: " + result.get(1).getIdZombie() + ")");
    }

    @Test
    @DisplayName("saveZombie devrait déléguer l'appel au repository")
    void saveZombie_shouldDelegateToRepository() {
        System.out.println("\n🔍 TEST: saveZombie - Délégation au repository pour sauvegarder un zombie");

        // Arrange
        Zombie zombieToSave = new Zombie();
        zombieToSave.setNom("Nouveau Zombie");

        Zombie savedZombie = new Zombie();
        savedZombie.setIdZombie(5);
        savedZombie.setNom("Nouveau Zombie");

        System.out.println("🔧 Configuration: ZombieRepository configuré pour simuler une sauvegarde réussie");
        when(zombieRepository.save(any(Zombie.class))).thenReturn(savedZombie);

        // Act
        System.out.println("▶️ Exécution: appel de zombieService.saveZombie()");
        Zombie result = zombieService.saveZombie(zombieToSave);

        // Assert
        assertNotNull(result);
        assertEquals(savedZombie, result);
        verify(zombieRepository).save(zombieToSave);

        System.out.println("✅ Succès: Zombie sauvegardé");
        System.out.println("   - ID généré: " + result.getIdZombie());
        System.out.println("   - Nom: " + result.getNom());
    }

    @Test
    @DisplayName("deleteZombie devrait déléguer l'appel au repository")
    void deleteZombie_shouldDelegateToRepository() {
        System.out.println("\n🔍 TEST: deleteZombie - Délégation au repository pour supprimer un zombie");

        // Arrange
        System.out.println("🔧 Configuration: ZombieRepository configuré pour simuler une suppression réussie");
        when(zombieRepository.deleteById(1)).thenReturn(true);

        // Act
        System.out.println("▶️ Exécution: appel de zombieService.deleteZombie(1)");
        boolean result = zombieService.deleteZombie(1);

        // Assert
        assertTrue(result);
        verify(zombieRepository).deleteById(1);

        System.out.println("✅ Succès: true retourné, indiquant une suppression réussie");
    }

    @Test
    @DisplayName("validateZombieFormat devrait retourner true pour un zombie valide")
    void validateZombieFormat_withValidZombie_shouldReturnTrue() {
        System.out.println("\n🔍 TEST: validateZombieFormat - Validation d'un zombie valide");

        // Arrange
        Zombie validZombie = new Zombie();
        validZombie.setNom("Zombie Valide");
        validZombie.setPointDeVie(100);
        validZombie.setAttaqueParSeconde(0.8);
        validZombie.setDegatAttaque(10);
        validZombie.setVitesseDeDeplacement(0.5);
        validZombie.setCheminImage("images/zombie/valide.png");

        // Act
        System.out.println("▶️ Exécution: appel de zombieService.validateZombieFormat() avec un zombie valide");
        boolean result = zombieService.validateZombieFormat(validZombie);

        // Assert
        assertTrue(result);

        System.out.println("✅ Succès: true retourné, indiquant que le zombie est valide");
    }

    @Test
    @DisplayName("validateZombieFormat devrait retourner false pour un zombie invalide")
    void validateZombieFormat_withInvalidZombie_shouldReturnFalse() {
        System.out.println("\n🔍 TEST: validateZombieFormat - Validation d'un zombie invalide");

        // Arrange - zombie sans nom
        Zombie invalidZombie = new Zombie();
        invalidZombie.setPointDeVie(100);
        invalidZombie.setAttaqueParSeconde(0.8);
        invalidZombie.setDegatAttaque(10);
        invalidZombie.setVitesseDeDeplacement(0.5);
        invalidZombie.setCheminImage("images/zombie/invalide.png");

        // Act
        System.out.println("▶️ Exécution: appel de zombieService.validateZombieFormat() avec un zombie sans nom");
        boolean result = zombieService.validateZombieFormat(invalidZombie);

        // Assert
        assertFalse(result);

        System.out.println("✅ Succès: false retourné, indiquant que le zombie est invalide");

        // Test avec zombie avec points de vie négatifs
        invalidZombie.setNom("Zombie Invalide");
        invalidZombie.setPointDeVie(-10);

        System.out.println("▶️ Exécution: appel de zombieService.validateZombieFormat() avec des points de vie négatifs");
        result = zombieService.validateZombieFormat(invalidZombie);

        assertFalse(result);
        System.out.println("✅ Succès: false retourné pour des points de vie négatifs");

        // Test avec null
        System.out.println("▶️ Exécution: appel de zombieService.validateZombieFormat() avec null");
        result = zombieService.validateZombieFormat(null);

        assertFalse(result);
        System.out.println("✅ Succès: false retourné pour un zombie null");
    }
}