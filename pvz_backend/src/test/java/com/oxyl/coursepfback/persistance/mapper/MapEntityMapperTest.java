package com.oxyl.coursepfback.persistance.mapper;

import com.oxyl.coursepfback.core.model.Map;
import com.oxyl.coursepfback.persistance.entity.MapEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapEntityMapperTest {

    @Test
    @DisplayName("Entity to Model")
    void toModel_shouldMapAllFields() {
        System.out.println("\nTEST 1 : toModel - Conversion d'une entité MapEntity vers un modèle Map");

        // Arrange
        System.out.println("🔧 Préparation: Création d'une entité MapEntity de test");
        MapEntity entity = new MapEntity();
        entity.setIdMap(1);
        entity.setLigne(5);
        entity.setColonne(9);
        entity.setCheminImage("images/map/gazon.png");
        System.out.println("   - ID: " + entity.getIdMap());
        System.out.println("   - Lignes: " + entity.getLigne() + ", Colonnes: " + entity.getColonne());

        // Act
        System.out.println("▶️ Exécution: Appel de MapEntityMapper.toModel()");
        Map model = MapEntityMapper.toModel(entity);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement mappés");
        assertNotNull(model, "Le modèle ne devrait pas être null");
        assertEquals(entity.getIdMap(), model.getIdMap(), "L'ID devrait être mappé correctement");
        assertEquals(entity.getLigne(), model.getLigne(), "Le nombre de lignes devrait être mappé correctement");
        assertEquals(entity.getColonne(), model.getColonne(), "Le nombre de colonnes devrait être mappé correctement");
        assertEquals(entity.getCheminImage(), model.getCheminImage(), "Le chemin d'image devrait être mappé correctement");

        System.out.println("✅ Succès: Tous les champs ont été correctement mappés");
        System.out.println("   - Modèle créé avec ID: " + model.getIdMap() + ", dimensions: " + model.getLigne() + "×" + model.getColonne());
    }

    @Test
    @DisplayName("Model to Entity")
    void toEntity_shouldMapAllFields() {
        System.out.println("\nTEST 2 : toEntity - Conversion d'un modèle Map vers une entité MapEntity");

        // Arrange
        System.out.println("🔧 Préparation: Création d'un modèle Map de test");
        Map model = new Map();
        model.setIdMap(1);
        model.setLigne(5);
        model.setColonne(9);
        model.setCheminImage("images/map/gazon.png");
        System.out.println("   - ID: " + model.getIdMap());
        System.out.println("   - Lignes: " + model.getLigne() + ", Colonnes: " + model.getColonne());

        // Act
        System.out.println("▶️ Exécution: Appel de MapEntityMapper.toEntity()");
        MapEntity entity = MapEntityMapper.toEntity(model);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement mappés");
        assertNotNull(entity, "L'entité ne devrait pas être null");
        assertEquals(model.getIdMap(), entity.getIdMap(), "L'ID devrait être mappé correctement");
        assertEquals(model.getLigne(), entity.getLigne(), "Le nombre de lignes devrait être mappé correctement");
        assertEquals(model.getColonne(), entity.getColonne(), "Le nombre de colonnes devrait être mappé correctement");
        assertEquals(model.getCheminImage(), entity.getCheminImage(), "Le chemin d'image devrait être mappé correctement");

        System.out.println("✅ Succès: Tous les champs ont été correctement mappés");
        System.out.println("   - Entité créée avec ID: " + entity.getIdMap() + ", dimensions: " + entity.getLigne() + "×" + entity.getColonne());
    }

    @Test
    @DisplayName("Entity null to Model => null")
    void toModel_withNullEntity_shouldReturnNull() {
        System.out.println("\nTEST 3 : toModel avec null - Vérification du comportement avec une entité null");

        // Act
        System.out.println("▶️ Exécution: Appel de MapEntityMapper.toModel(null)");
        Map result = MapEntityMapper.toModel(null);

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
        System.out.println("▶️ Exécution: Appel de MapEntityMapper.toEntity(null)");
        MapEntity result = MapEntityMapper.toEntity(null);

        // Assert
        System.out.println("🔎 Vérification: Le résultat devrait être null");
        assertNull(result, "Le résultat devrait être null quand le modèle est null");

        System.out.println("✅ Succès: null retourné comme attendu");
    }
}