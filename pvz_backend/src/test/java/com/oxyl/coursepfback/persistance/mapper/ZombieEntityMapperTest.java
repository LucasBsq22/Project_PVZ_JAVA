package com.oxyl.coursepfback.persistance.mapper;

import com.oxyl.coursepfback.core.model.Zombie;
import com.oxyl.coursepfback.persistance.entity.ZombieEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZombieEntityMapperTest {

    @Test
    @DisplayName("Entity to Model")
    void toModel_shouldMapAllFields() {
        System.out.println("\nTEST 1 : toModel - Conversion d'une entité ZombieEntity vers un modèle Zombie");

        // Arrange
        System.out.println("🔧 Préparation: Création d'une entité ZombieEntity de test");
        ZombieEntity entity = new ZombieEntity();
        entity.setIdZombie(1);
        entity.setNom("Zombie de base");
        entity.setPointDeVie(100);
        entity.setAttaqueParSeconde(0.8);
        entity.setDegatAttaque(10);
        entity.setVitesseDeDeplacement(0.5);
        entity.setCheminImage("images/zombie/zombie.png");
        entity.setIdMap(1);
        System.out.println("   - ID: " + entity.getIdZombie());
        System.out.println("   - Nom: " + entity.getNom());

        // Act
        System.out.println("▶️ Exécution: Appel de ZombieEntityMapper.toModel()");
        Zombie model = ZombieEntityMapper.toModel(entity);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement mappés");
        assertNotNull(model, "Le modèle ne devrait pas être null");
        assertEquals(entity.getIdZombie(), model.getIdZombie(), "L'ID devrait être mappé correctement");
        assertEquals(entity.getNom(), model.getNom(), "Le nom devrait être mappé correctement");
        assertEquals(entity.getPointDeVie(), model.getPointDeVie(), "Les points de vie devraient être mappés correctement");
        assertEquals(entity.getAttaqueParSeconde(), model.getAttaqueParSeconde(), "L'attaque par seconde devrait être mappée correctement");
        assertEquals(entity.getDegatAttaque(), model.getDegatAttaque(), "Les dégâts d'attaque devraient être mappés correctement");
        assertEquals(entity.getVitesseDeDeplacement(), model.getVitesseDeDeplacement(), "La vitesse de déplacement devrait être mappée correctement");
        assertEquals(entity.getCheminImage(), model.getCheminImage(), "Le chemin d'image devrait être mappé correctement");
        assertEquals(entity.getIdMap(), model.getIdMap(), "L'ID de map devrait être mappé correctement");

        System.out.println("✅ Succès: Tous les champs ont été correctement mappés");
        System.out.println("   - Modèle créé avec ID: " + model.getIdZombie() + " et nom: " + model.getNom());
    }

    @Test
    @DisplayName("Model to Entity")
    void toEntity_shouldMapAllFields() {
        System.out.println("\nTEST 2 : toEntity - Conversion d'un modèle Zombie vers une entité ZombieEntity");

        // Arrange
        System.out.println("🔧 Préparation: Création d'un modèle Zombie de test");
        Zombie model = new Zombie();
        model.setIdZombie(1);
        model.setNom("Zombie de base");
        model.setPointDeVie(100);
        model.setAttaqueParSeconde(0.8);
        model.setDegatAttaque(10);
        model.setVitesseDeDeplacement(0.5);
        model.setCheminImage("images/zombie/zombie.png");
        model.setIdMap(1);
        System.out.println("   - ID: " + model.getIdZombie());
        System.out.println("   - Nom: " + model.getNom());

        // Act
        System.out.println("▶️ Exécution: Appel de ZombieEntityMapper.toEntity()");
        ZombieEntity entity = ZombieEntityMapper.toEntity(model);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement mappés");
        assertNotNull(entity, "L'entité ne devrait pas être null");
        assertEquals(model.getIdZombie(), entity.getIdZombie(), "L'ID devrait être mappé correctement");
        assertEquals(model.getNom(), entity.getNom(), "Le nom devrait être mappé correctement");
        assertEquals(model.getPointDeVie(), entity.getPointDeVie(), "Les points de vie devraient être mappés correctement");
        assertEquals(model.getAttaqueParSeconde(), entity.getAttaqueParSeconde(), "L'attaque par seconde devrait être mappée correctement");
        assertEquals(model.getDegatAttaque(), entity.getDegatAttaque(), "Les dégâts d'attaque devraient être mappés correctement");
        assertEquals(model.getVitesseDeDeplacement(), entity.getVitesseDeDeplacement(), "La vitesse de déplacement devrait être mappée correctement");
        assertEquals(model.getCheminImage(), entity.getCheminImage(), "Le chemin d'image devrait être mappé correctement");
        assertEquals(model.getIdMap(), entity.getIdMap(), "L'ID de map devrait être mappé correctement");

        System.out.println("✅ Succès: Tous les champs ont été correctement mappés");
        System.out.println("   - Entité créée avec ID: " + entity.getIdZombie() + " et nom: " + entity.getNom());
    }

    @Test
    @DisplayName("Entity null to Model => null")
    void toModel_withNullEntity_shouldReturnNull() {
        System.out.println("\nTEST 3 : toModel avec null - Vérification du comportement avec une entité null");

        // Act
        System.out.println("▶️ Exécution: Appel de ZombieEntityMapper.toModel(null)");
        Zombie result = ZombieEntityMapper.toModel(null);

        // Assert
        System.out.println("🔎 Vérification: Le résultat devrait être null");
        assertNull(result, "Le résultat devrait être null quand l'entité est null");

        System.out.println("✅ Succès: null retourné comme attendu");
    }

    @Test
    @DisplayName("Model null to Entity => null")
    void toEntity_withNullModel_shouldReturnNull() {
        System.out.println("\nTEST 4 : toEntity avec null - Vérification du comportement avec un modèle null");

        // Act
        System.out.println("▶️ Exécution: Appel de ZombieEntityMapper.toEntity(null)");
        ZombieEntity result = ZombieEntityMapper.toEntity(null);

        // Assert
        System.out.println("🔎 Vérification: Le résultat devrait être null");
        assertNull(result, "Le résultat devrait être null quand le modèle est null");

        System.out.println("✅ Succès: null retourné comme attendu");
    }
}