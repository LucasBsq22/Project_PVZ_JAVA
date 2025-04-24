package com.oxyl.coursepfback.api.mapper;

import com.oxyl.coursepfback.api.dto.PlantDTO;
import com.oxyl.coursepfback.core.model.Plant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlantDTOMapperTest {

    @Test
    @DisplayName("Model to DTO")
    void toDTO_shouldMapAllFields() {
        System.out.println("\nTEST 1 : toDTO - Conversion d'un modèle Plant vers un DTO PlantDTO");

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
        System.out.println("▶️ Exécution: Appel de PlantDTOMapper.toDTO()");
        PlantDTO dto = PlantDTOMapper.toDTO(model);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement mappés");
        assertNotNull(dto, "Le DTO ne devrait pas être null");
        assertEquals(model.getIdPlante(), dto.getId_plante(), "L'ID devrait être mappé correctement");
        assertEquals(model.getNom(), dto.getNom(), "Le nom devrait être mappé correctement");
        assertEquals(model.getPointDeVie(), dto.getPoint_de_vie(), "Les points de vie devraient être mappés correctement");
        assertEquals(model.getAttaqueParSeconde(), dto.getAttaque_par_seconde(), "L'attaque par seconde devrait être mappée correctement");
        assertEquals(model.getDegatAttaque(), dto.getDegat_attaque(), "Les dégâts d'attaque devraient être mappés correctement");
        assertEquals(model.getCout(), dto.getCout(), "Le coût devrait être mappé correctement");
        assertEquals(model.getSoleilParSeconde(), dto.getSoleil_par_seconde(), "Le soleil par seconde devrait être mappé correctement");
        assertEquals(model.getEffet(), dto.getEffet(), "L'effet devrait être mappé correctement");
        assertEquals(model.getCheminImage(), dto.getChemin_image(), "Le chemin d'image devrait être mappé correctement");

        System.out.println("✅ Succès: Tous les champs ont été correctement mappés");
        System.out.println("   - DTO créé avec ID: " + dto.getId_plante() + " et nom: " + dto.getNom());
    }

    @Test
    @DisplayName("DTO to Model")
    void toModel_shouldMapAllFields() {
        System.out.println("\nTEST 2 : toModel - Conversion d'un DTO PlantDTO vers un modèle Plant");

        // Arrange
        System.out.println("🔧 Préparation: Création d'un DTO PlantDTO de test");
        PlantDTO dto = new PlantDTO();
        dto.setId_plante(1);
        dto.setNom("Tournesol");
        dto.setPoint_de_vie(100);
        dto.setAttaque_par_seconde(0.0);
        dto.setDegat_attaque(0);
        dto.setCout(50);
        dto.setSoleil_par_seconde(25.0);
        dto.setEffet("normal");
        dto.setChemin_image("images/plante/tournesol.png");
        System.out.println("   - ID: " + dto.getId_plante());
        System.out.println("   - Nom: " + dto.getNom());

        // Act
        System.out.println("▶️ Exécution: Appel de PlantDTOMapper.toModel()");
        Plant model = PlantDTOMapper.toModel(dto);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement mappés");
        assertNotNull(model, "Le modèle ne devrait pas être null");
        assertEquals(dto.getId_plante(), model.getIdPlante(), "L'ID devrait être mappé correctement");
        assertEquals(dto.getNom(), model.getNom(), "Le nom devrait être mappé correctement");
        assertEquals(dto.getPoint_de_vie(), model.getPointDeVie(), "Les points de vie devraient être mappés correctement");
        assertEquals(dto.getAttaque_par_seconde(), model.getAttaqueParSeconde(), "L'attaque par seconde devrait être mappée correctement");
        assertEquals(dto.getDegat_attaque(), model.getDegatAttaque(), "Les dégâts d'attaque devraient être mappés correctement");
        assertEquals(dto.getCout(), model.getCout(), "Le coût devrait être mappé correctement");
        assertEquals(dto.getSoleil_par_seconde(), model.getSoleilParSeconde(), "Le soleil par seconde devrait être mappé correctement");
        assertEquals(dto.getEffet(), model.getEffet(), "L'effet devrait être mappé correctement");
        assertEquals(dto.getChemin_image(), model.getCheminImage(), "Le chemin d'image devrait être mappé correctement");

        System.out.println("✅ Succès: Tous les champs ont été correctement mappés");
        System.out.println("   - Modèle créé avec ID: " + model.getIdPlante() + " et nom: " + model.getNom());
    }

    @Test
    @DisplayName("Model null to DTO => null")
    void toDTO_withNullModel_shouldReturnNull() {
        System.out.println("\nTEST 3 : toDTO avec null - Vérification du comportement avec un modèle null");

        // Act
        System.out.println("▶️ Exécution: Appel de PlantDTOMapper.toDTO(null)");
        PlantDTO result = PlantDTOMapper.toDTO(null);

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
        System.out.println("▶️ Exécution: Appel de PlantDTOMapper.toModel(null)");
        Plant result = PlantDTOMapper.toModel(null);

        // Assert
        System.out.println("🔎 Vérification: Le résultat devrait être null");
        assertNull(result, "Le résultat devrait être null quand le DTO est null");

        System.out.println("✅ Succès: null retourné comme attendu");
    }
}