package com.oxyl.coursepfback.persistance.mapper;

import com.oxyl.coursepfback.core.model.Plant;
import com.oxyl.coursepfback.persistance.entity.PlantEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlantEntityMapperTest {

    @Test
    @DisplayName("Entity to Model")
    void toModel_shouldMapAllFields() {
        System.out.println("\nTEST 1 : toModel - Conversion d'une entité PlantEntity vers un modèle Plant");

        // Arrange
        System.out.println("🔧 Préparation: Création d'une entité PlantEntity de test");
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
        System.out.println("   - ID: " + entity.getIdPlante());
        System.out.println("   - Nom: " + entity.getNom());

        // Act
        System.out.println("▶️ Exécution: Appel de PlantEntityMapper.toModel()");
        Plant model = PlantEntityMapper.toModel(entity);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement mappés");
        assertNotNull(model, "Le modèle ne devrait pas être null");
        assertEquals(entity.getIdPlante(), model.getIdPlante(), "L'ID devrait être mappé correctement");
        assertEquals(entity.getNom(), model.getNom(), "Le nom devrait être mappé correctement");
        assertEquals(entity.getPointDeVie(), model.getPointDeVie(), "Les points de vie devraient être mappés correctement");
        assertEquals(entity.getAttaqueParSeconde(), model.getAttaqueParSeconde(), "L'attaque par seconde devrait être mappée correctement");
        assertEquals(entity.getDegatAttaque(), model.getDegatAttaque(), "Les dégâts d'attaque devraient être mappés correctement");
        assertEquals(entity.getCout(), model.getCout(), "Le coût devrait être mappé correctement");
        assertEquals(entity.getSoleilParSeconde(), model.getSoleilParSeconde(), "Le soleil par seconde devrait être mappé correctement");
        assertEquals(entity.getEffet(), model.getEffet(), "L'effet devrait être mappé correctement");
        assertEquals(entity.getCheminImage(), model.getCheminImage(), "Le chemin d'image devrait être mappé correctement");

        System.out.println("✅ Succès: Tous les champs ont été correctement mappés");
        System.out.println("   - Modèle créé avec ID: " + model.getIdPlante() + " et nom: " + model.getNom());
    }

    @Test
    @DisplayName("Model to Entity")
    void toEntity_shouldMapAllFields() {
        System.out.println("\nTEST 2 : toEntity - Conversion d'un modèle Plant vers une entité PlantEntity");

        // Arrange
        System.out.println("🔧 Préparation: Création d'un modèle Plant de test");
        Plant model = new Plant();
        model.setIdPlante(1);
        model.setNom("Tournesol");
        model.setPointDeVie(100);
        model.setAttaqueParSeconde(0.0);
        model.setDegatAttaque(0);
        model.setCout(50);
        model.setSoleilParSeconde(25.0);
        model.setEffet("normal");
        model.setCheminImage("images/plante/tournesol.png");
        System.out.println("   - ID: " + model.getIdPlante());
        System.out.println("   - Nom: " + model.getNom());

        // Act
        System.out.println("▶️ Exécution: Appel de PlantEntityMapper.toEntity()");
        PlantEntity entity = PlantEntityMapper.toEntity(model);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement mappés");
        assertNotNull(entity, "L'entité ne devrait pas être null");
        assertEquals(model.getIdPlante(), entity.getIdPlante(), "L'ID devrait être mappé correctement");
        assertEquals(model.getNom(), entity.getNom(), "Le nom devrait être mappé correctement");
        assertEquals(model.getPointDeVie(), entity.getPointDeVie(), "Les points de vie devraient être mappés correctement");
        assertEquals(model.getAttaqueParSeconde(), entity.getAttaqueParSeconde(), "L'attaque par seconde devrait être mappée correctement");
        assertEquals(model.getDegatAttaque(), entity.getDegatAttaque(), "Les dégâts d'attaque devraient être mappés correctement");
        assertEquals(model.getCout(), entity.getCout(), "Le coût devrait être mappé correctement");
        assertEquals(model.getSoleilParSeconde(), entity.getSoleilParSeconde(), "Le soleil par seconde devrait être mappé correctement");
        assertEquals(model.getEffet(), entity.getEffet(), "L'effet devrait être mappé correctement");
        assertEquals(model.getCheminImage(), entity.getCheminImage(), "Le chemin d'image devrait être mappé correctement");

        System.out.println("✅ Succès: Tous les champs ont été correctement mappés");
        System.out.println("   - Entité créée avec ID: " + entity.getIdPlante() + " et nom: " + entity.getNom());
    }

    @Test
    @DisplayName("Entity null to Model => null")
    void toModel_withNullEntity_shouldReturnNull() {
        System.out.println("\nTEST 3 : toModel avec null - Vérification du comportement avec une entité null");

        // Act
        System.out.println("▶️ Exécution: Appel de PlantEntityMapper.toModel(null)");
        Plant result = PlantEntityMapper.toModel(null);

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
        System.out.println("▶️ Exécution: Appel de PlantEntityMapper.toEntity(null)");
        PlantEntity result = PlantEntityMapper.toEntity(null);

        // Assert
        System.out.println("🔎 Vérification: Le résultat devrait être null");
        assertNull(result, "Le résultat devrait être null quand le modèle est null");

        System.out.println("✅ Succès: null retourné comme attendu");
    }
}