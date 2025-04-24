package com.oxyl.coursepfback.persistance.repository;

import com.oxyl.coursepfback.core.model.Zombie;
import com.oxyl.coursepfback.persistance.dao.ZombieDAO;
import com.oxyl.coursepfback.persistance.entity.ZombieEntity;
import com.oxyl.coursepfback.persistance.mapper.ZombieEntityMapper;
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

class ZombieRepositoryTest {

    private ZombieRepository zombieRepository;
    private ZombieDAO zombieDAO;

    @BeforeEach
    void setUp() {
        System.out.println("\n----------- Configuration du test ZombieRepository -----------");
        zombieDAO = Mockito.mock(ZombieDAO.class);
        zombieRepository = new ZombieRepository(zombieDAO);
        System.out.println("✅ Mock ZombieDAO injecté avec succès dans ZombieRepository");
    }

    @Test
    @DisplayName("findAll devrait convertir les entités en modèles et les retourner")
    void findAll_shouldReturnAllZombies() {
        System.out.println("\n🔍 TEST: findAll - Conversion et récupération de tous les zombies");

        // Arrange
        ZombieEntity entity1 = new ZombieEntity();
        entity1.setIdZombie(1);
        entity1.setNom("Zombie de base");
        entity1.setPointDeVie(100);
        entity1.setAttaqueParSeconde(0.8);
        entity1.setDegatAttaque(10);
        entity1.setVitesseDeDeplacement(0.5);
        entity1.setCheminImage("images/zombie/zombie.png");
        entity1.setIdMap(1);

        ZombieEntity entity2 = new ZombieEntity();
        entity2.setIdZombie(2);
        entity2.setNom("Zombie Cone");
        entity2.setPointDeVie(200);
        entity2.setAttaqueParSeconde(0.8);
        entity2.setDegatAttaque(10);
        entity2.setVitesseDeDeplacement(0.45);
        entity2.setCheminImage("images/zombie/conehead.png");
        entity2.setIdMap(1);

        List<ZombieEntity> entities = Arrays.asList(entity1, entity2);

        System.out.println("🔧 Configuration: ZombieDAO configuré pour retourner une liste de 2 entités");
        when(zombieDAO.findAll()).thenReturn(entities);

        // Act
        System.out.println("▶️ Exécution: appel de zombieRepository.findAll()");
        List<Zombie> result = zombieRepository.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Zombie de base", result.get(0).getNom());
        assertEquals("Zombie Cone", result.get(1).getNom());
        verify(zombieDAO).findAll();

        System.out.println("✅ Succès: " + result.size() + " zombies récupérés et convertis correctement");
        System.out.println("   - Zombie 1: " + result.get(0).getNom() + " (ID: " + result.get(0).getIdZombie() + ")");
        System.out.println("   - Zombie 2: " + result.get(1).getNom() + " (ID: " + result.get(1).getIdZombie() + ")");
    }

    @Test
    @DisplayName("findById devrait convertir l'entité en modèle quand elle existe")
    void findById_whenZombieExists_shouldReturnZombie() {
        System.out.println("\n🔍 TEST: findById - Conversion et récupération d'un zombie existant");

        // Arrange
        ZombieEntity entity = new ZombieEntity();
        entity.setIdZombie(1);
        entity.setNom("Zombie de base");
        entity.setPointDeVie(100);
        entity.setAttaqueParSeconde(0.8);
        entity.setDegatAttaque(10);
        entity.setVitesseDeDeplacement(0.5);
        entity.setCheminImage("images/zombie/zombie.png");
        entity.setIdMap(1);

        System.out.println("🔧 Configuration: ZombieDAO configuré pour retourner une entité");
        when(zombieDAO.findById(1)).thenReturn(Optional.of(entity));

        // Act
        System.out.println("▶️ Exécution: appel de zombieRepository.findById(1)");
        Optional<Zombie> result = zombieRepository.findById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Zombie de base", result.get().getNom());
        assertEquals(1, result.get().getIdZombie());
        verify(zombieDAO).findById(1);

        System.out.println("✅ Succès: Zombie trouvé et converti correctement");
        System.out.println("   - Nom: " + result.get().getNom());
        System.out.println("   - ID: " + result.get().getIdZombie());
    }

    @Test
    @DisplayName("findById devrait retourner Optional vide quand le zombie n'existe pas")
    void findById_whenZombieDoesNotExist_shouldReturnEmptyOptional() {
        System.out.println("\n🔍 TEST: findById - Tentative de récupération d'un zombie inexistant");

        // Arrange
        System.out.println("🔧 Configuration: ZombieDAO configuré pour retourner Optional.empty()");
        when(zombieDAO.findById(999)).thenReturn(Optional.empty());

        // Act
        System.out.println("▶️ Exécution: appel de zombieRepository.findById(999)");
        Optional<Zombie> result = zombieRepository.findById(999);

        // Assert
        assertFalse(result.isPresent());
        verify(zombieDAO).findById(999);

        System.out.println("✅ Succès: Optional vide retourné comme attendu");
    }

    @Test
    @DisplayName("findByMapId devrait retourner la liste des zombies pour une map spécifique")
    void findByMapId_shouldReturnZombiesForMap() {
        System.out.println("\n🔍 TEST: findByMapId - Récupération des zombies d'une map spécifique");

        // Arrange
        ZombieEntity entity1 = new ZombieEntity();
        entity1.setIdZombie(1);
        entity1.setNom("Zombie de base");
        entity1.setPointDeVie(100);
        entity1.setAttaqueParSeconde(0.8);
        entity1.setDegatAttaque(10);
        entity1.setVitesseDeDeplacement(0.5);
        entity1.setCheminImage("images/zombie/zombie.png");
        entity1.setIdMap(1);

        ZombieEntity entity2 = new ZombieEntity();
        entity2.setIdZombie(3);
        entity2.setNom("Zombie Seau");
        entity2.setPointDeVie(300);
        entity2.setAttaqueParSeconde(0.7);
        entity2.setDegatAttaque(10);
        entity2.setVitesseDeDeplacement(0.4);
        entity2.setCheminImage("images/zombie/buckethead.png");
        entity2.setIdMap(1);

        List<ZombieEntity> entities = Arrays.asList(entity1, entity2);

        System.out.println("🔧 Configuration: ZombieDAO configuré pour retourner une liste de 2 entités");
        when(zombieDAO.findByMapId(1)).thenReturn(entities);

        // Act
        System.out.println("▶️ Exécution: appel de zombieRepository.findByMapId(1)");
        List<Zombie> result = zombieRepository.findByMapId(1);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Zombie de base", result.get(0).getNom());
        assertEquals("Zombie Seau", result.get(1).getNom());
        verify(zombieDAO).findByMapId(1);

        System.out.println("✅ Succès: " + result.size() + " zombies récupérés pour la map ID=1");
        System.out.println("   - Zombie 1: " + result.get(0).getNom() + " (ID: " + result.get(0).getIdZombie() + ")");
        System.out.println("   - Zombie 2: " + result.get(1).getNom() + " (ID: " + result.get(1).getIdZombie() + ")");
    }

    @Test
    @DisplayName("save devrait insérer un nouveau zombie quand idZombie est null")
    void save_whenIdIsNull_shouldInsertNewZombie() {
        System.out.println("\n🔍 TEST: save - Insertion d'un nouveau zombie (idZombie null)");

        // Arrange
        Zombie zombieToSave = new Zombie();
        zombieToSave.setNom("Nouveau Zombie");
        zombieToSave.setPointDeVie(120);
        zombieToSave.setAttaqueParSeconde(0.9);
        zombieToSave.setDegatAttaque(12);
        zombieToSave.setVitesseDeDeplacement(0.55);
        zombieToSave.setCheminImage("images/zombie/nouveau.png");
        zombieToSave.setIdMap(2);

        ZombieEntity savedEntity = new ZombieEntity();
        savedEntity.setIdZombie(5);
        savedEntity.setNom("Nouveau Zombie");
        savedEntity.setPointDeVie(120);
        savedEntity.setAttaqueParSeconde(0.9);
        savedEntity.setDegatAttaque(12);
        savedEntity.setVitesseDeDeplacement(0.55);
        savedEntity.setCheminImage("images/zombie/nouveau.png");
        savedEntity.setIdMap(2);

        System.out.println("🔧 Configuration: ZombieDAO configuré pour simuler une insertion réussie");
        when(zombieDAO.insert(any(ZombieEntity.class))).thenReturn(savedEntity);

        // Act
        System.out.println("▶️ Exécution: appel de zombieRepository.save() avec un nouveau zombie");
        Zombie result = zombieRepository.save(zombieToSave);

        // Assert
        assertNotNull(result);
        assertEquals(5, result.getIdZombie());
        assertEquals("Nouveau Zombie", result.getNom());
        verify(zombieDAO).insert(any(ZombieEntity.class));
        verify(zombieDAO, never()).update(any(ZombieEntity.class));

        System.out.println("✅ Succès: Nouveau zombie inséré et converti correctement");
        System.out.println("   - ID généré: " + result.getIdZombie());
        System.out.println("   - Nom: " + result.getNom());
    }

    @Test
    @DisplayName("save devrait mettre à jour un zombie existant quand idZombie n'est pas null")
    void save_whenIdIsNotNull_shouldUpdateZombie() {
        System.out.println("\n🔍 TEST: save - Mise à jour d'un zombie existant (idZombie non null)");

        // Arrange
        Zombie zombieToUpdate = new Zombie();
        zombieToUpdate.setIdZombie(2);
        zombieToUpdate.setNom("Zombie Modifié");
        zombieToUpdate.setPointDeVie(180);
        zombieToUpdate.setAttaqueParSeconde(0.85);
        zombieToUpdate.setDegatAttaque(11);
        zombieToUpdate.setVitesseDeDeplacement(0.5);
        zombieToUpdate.setCheminImage("images/zombie/modifie.png");
        zombieToUpdate.setIdMap(1);

        ZombieEntity updatedEntity = new ZombieEntity();
        updatedEntity.setIdZombie(2);
        updatedEntity.setNom("Zombie Modifié");
        updatedEntity.setPointDeVie(180);
        updatedEntity.setAttaqueParSeconde(0.85);
        updatedEntity.setDegatAttaque(11);
        updatedEntity.setVitesseDeDeplacement(0.5);
        updatedEntity.setCheminImage("images/zombie/modifie.png");
        updatedEntity.setIdMap(1);

        System.out.println("🔧 Configuration: ZombieDAO configuré pour simuler une mise à jour réussie");
        when(zombieDAO.update(any(ZombieEntity.class))).thenReturn(updatedEntity);

        // Act
        System.out.println("▶️ Exécution: appel de zombieRepository.save() avec un zombie existant");
        Zombie result = zombieRepository.save(zombieToUpdate);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getIdZombie());
        assertEquals("Zombie Modifié", result.getNom());
        verify(zombieDAO, never()).insert(any(ZombieEntity.class));
        verify(zombieDAO).update(any(ZombieEntity.class));

        System.out.println("✅ Succès: Zombie mis à jour et converti correctement");
        System.out.println("   - ID: " + result.getIdZombie());
        System.out.println("   - Nom: " + result.getNom());
    }

    @Test
    @DisplayName("deleteById devrait supprimer un zombie par son ID")
    void deleteById_shouldDeleteZombie() {
        System.out.println("\n🔍 TEST: deleteById - Suppression d'un zombie");

        // Arrange
        System.out.println("🔧 Configuration: ZombieDAO configuré pour simuler une suppression réussie");
        when(zombieDAO.deleteById(1)).thenReturn(true);

        // Act
        System.out.println("▶️ Exécution: appel de zombieRepository.deleteById(1)");
        boolean result = zombieRepository.deleteById(1);

        // Assert
        assertTrue(result);
        verify(zombieDAO).deleteById(1);

        System.out.println("✅ Succès: true retourné, indiquant une suppression réussie");
    }

    @Test
    @DisplayName("deleteById devrait retourner false quand le zombie n'existe pas")
    void deleteById_whenZombieDoesNotExist_shouldReturnFalse() {
        System.out.println("\n🔍 TEST: deleteById - Tentative de suppression d'un zombie inexistant");

        // Arrange
        System.out.println("🔧 Configuration: ZombieDAO configuré pour simuler une suppression échouée");
        when(zombieDAO.deleteById(999)).thenReturn(false);

        // Act
        System.out.println("▶️ Exécution: appel de zombieRepository.deleteById(999)");
        boolean result = zombieRepository.deleteById(999);

        // Assert
        assertFalse(result);
        verify(zombieDAO).deleteById(999);

        System.out.println("✅ Succès: false retourné, indiquant que le zombie n'a pas été trouvé");
    }
}