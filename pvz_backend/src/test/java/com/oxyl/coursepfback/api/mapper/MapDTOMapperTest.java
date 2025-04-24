package com.oxyl.coursepfback.api.mapper;

import com.oxyl.coursepfback.api.dto.MapDTO;
import com.oxyl.coursepfback.core.model.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDTOMapperTest {

    @Test
    @DisplayName("Model to DTO")
    void toDTO_shouldMapAllFields() {
        System.out.println("\nTEST 1 : toDTO - Conversion d'un modèle Map vers un DTO MapDTO");

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
        System.out.println("▶️ Exécution: Appel de MapDTOMapper.toDTO()");
        MapDTO dto = MapDTOMapper.toDTO(model);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement mappés");
        assertNotNull(dto, "Le DTO ne devrait pas être null");
        assertEquals(model.getIdMap(), dto.getId_map(), "L'ID devrait être mappé correctement");
        assertEquals(model.getLigne(), dto.getLigne(), "Le nombre de lignes devrait être mappé correctement");
        assertEquals(model.getColonne(), dto.getColonne(), "Le nombre de colonnes devrait être mappé correctement");
        assertEquals(model.getCheminImage(), dto.getChemin_image(), "Le chemin d'image devrait être mappé correctement");

        System.out.println("✅ Succès: Tous les champs ont été correctement mappés");
        System.out.println("   - DTO créé avec ID: " + dto.getId_map() + ", dimensions: " + dto.getLigne() + "×" + dto.getColonne());
    }

    @Test
    @DisplayName("DTO to Model")
    void toModel_shouldMapAllFields() {
        System.out.println("\nTEST 2 : toModel - Conversion d'un DTO MapDTO vers un modèle Map");

        // Arrange
        System.out.println("🔧 Préparation: Création d'un DTO MapDTO de test");
        MapDTO dto = new MapDTO();
        dto.setId_map(1);
        dto.setLigne(5);
        dto.setColonne(9);
        dto.setChemin_image("images/map/gazon.png");
        System.out.println("   - ID: " + dto.getId_map());
        System.out.println("   - Lignes: " + dto.getLigne() + ", Colonnes: " + dto.getColonne());

        // Act
        System.out.println("▶️ Exécution: Appel de MapDTOMapper.toModel()");
        Map model = MapDTOMapper.toModel(dto);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement mappés");
        assertNotNull(model, "Le modèle ne devrait pas être null");
        assertEquals(dto.getId_map(), model.getIdMap(), "L'ID devrait être mappé correctement");
        assertEquals(dto.getLigne(), model.getLigne(), "Le nombre de lignes devrait être mappé correctement");
        assertEquals(dto.getColonne(), model.getColonne(), "Le nombre de colonnes devrait être mappé correctement");
        assertEquals(dto.getChemin_image(), model.getCheminImage(), "Le chemin d'image devrait être mappé correctement");

        System.out.println("✅ Succès: Tous les champs ont été correctement mappés");
        System.out.println("   - Modèle créé avec ID: " + model.getIdMap() + ", dimensions: " + model.getLigne() + "×" + model.getColonne());
    }

    @Test
    @DisplayName("Model null to DTO => null")
    void toDTO_withNullModel_shouldReturnNull() {
        System.out.println("\nTEST 3 : toDTO avec null - Vérification du comportement avec un modèle null");

        // Act
        System.out.println("▶️ Exécution: Appel de MapDTOMapper.toDTO(null)");
        MapDTO result = MapDTOMapper.toDTO(null);

        // Assert
        System.out.println("🔎 Vérification: Le résultat devrait être null");
        assertNull(result, "Le résultat devrait être null quand le modèle est null");

        System.out.println("✅ Succès: null retourné comme attendu");
    }

    @Test
    @DisplayName("DTO null to Model => null")
    void toModel_withNullDTO_shouldReturnNull() {
        System.out.println("\nTEST 4 : toModel avec null - Vérification du comportement avec un DTO null");

        // Act
        System.out.println("▶️ Exécution: Appel de MapDTOMapper.toModel(null)");
        Map result = MapDTOMapper.toModel(null);

        // Assert
        System.out.println("🔎 Vérification: Le résultat devrait être null");
        assertNull(result, "Le résultat devrait être null quand le DTO est null");

        System.out.println("✅ Succès: null retourné comme attendu");
    }
}