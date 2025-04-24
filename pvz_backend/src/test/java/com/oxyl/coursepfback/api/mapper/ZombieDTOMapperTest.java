package com.oxyl.coursepfback.api.mapper;

import com.oxyl.coursepfback.api.dto.ZombieDTO;
import com.oxyl.coursepfback.core.model.Zombie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZombieDTOMapperTest {

    @Test
    @DisplayName("Model to DTO")
    void toDTO_shouldMapAllFields() {
        System.out.println("\nTEST 1 : toDTO - Conversion d'un modèle Zombie vers un DTO ZombieDTO");

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
        System.out.println("▶️ Exécution: Appel de ZombieDTOMapper.toDTO()");
        ZombieDTO dto = ZombieDTOMapper.toDTO(model);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement mappés");
        assertNotNull(dto, "Le DTO ne devrait pas être null");
        assertEquals(model.getIdZombie(), dto.getId_zombie(), "L'ID devrait être mappé correctement");
        assertEquals(model.getNom(), dto.getNom(), "Le nom devrait être mappé correctement");
        assertEquals(model.getPointDeVie(), dto.getPoint_de_vie(), "Les points de vie devraient être mappés correctement");
        assertEquals(model.getAttaqueParSeconde(), dto.getAttaque_par_seconde(), "L'attaque par seconde devrait être mappée correctement");
        assertEquals(model.getDegatAttaque(), dto.getDegat_attaque(), "Les dégâts d'attaque devraient être mappés correctement");
        assertEquals(model.getVitesseDeDeplacement(), dto.getVitesse_de_deplacement(), "La vitesse de déplacement devrait être mappée correctement");
        assertEquals(model.getCheminImage(), dto.getChemin_image(), "Le chemin d'image devrait être mappé correctement");
        assertEquals(model.getIdMap(), dto.getId_map(), "L'ID de map devrait être mappé correctement");

        System.out.println("✅ Succès: Tous les champs ont été correctement mappés");
        System.out.println("   - DTO créé avec ID: " + dto.getId_zombie() + " et nom: " + dto.getNom());
    }

    @Test
    @DisplayName("DTO to Model")
    void toModel_shouldMapAllFields() {
        System.out.println("\nTEST 2 : toModel - Conversion d'un DTO ZombieDTO vers un modèle Zombie");

        // Arrange
        System.out.println("🔧 Préparation: Création d'un DTO ZombieDTO de test");
        ZombieDTO dto = new ZombieDTO();
        dto.setId_zombie(1);
        dto.setNom("Zombie de base");
        dto.setPoint_de_vie(100);
        dto.setAttaque_par_seconde(0.8);
        dto.setDegat_attaque(10);
        dto.setVitesse_de_deplacement(0.5);
        dto.setChemin_image("images/zombie/zombie.png");
        dto.setId_map(1);
        System.out.println("   - ID: " + dto.getId_zombie());
        System.out.println("   - Nom: " + dto.getNom());

        // Act
        System.out.println("▶️ Exécution: Appel de ZombieDTOMapper.toModel()");
        Zombie model = ZombieDTOMapper.toModel(dto);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement mappés");
        assertNotNull(model, "Le modèle ne devrait pas être null");
        assertEquals(dto.getId_zombie(), model.getIdZombie(), "L'ID devrait être mappé correctement");
        assertEquals(dto.getNom(), model.getNom(), "Le nom devrait être mappé correctement");
        assertEquals(dto.getPoint_de_vie(), model.getPointDeVie(), "Les points de vie devraient être mappés correctement");
        assertEquals(dto.getAttaque_par_seconde(), model.getAttaqueParSeconde(), "L'attaque par seconde devrait être mappée correctement");
        assertEquals(dto.getDegat_attaque(), model.getDegatAttaque(), "Les dégâts d'attaque devraient être mappés correctement");
        assertEquals(dto.getVitesse_de_deplacement(), model.getVitesseDeDeplacement(), "La vitesse de déplacement devrait être mappée correctement");
        assertEquals(dto.getChemin_image(), model.getCheminImage(), "Le chemin d'image devrait être mappé correctement");
        assertEquals(dto.getId_map(), model.getIdMap(), "L'ID de map devrait être mappé correctement");

        System.out.println("✅ Succès: Tous les champs ont été correctement mappés");
        System.out.println("   - Modèle créé avec ID: " + model.getIdZombie() + " et nom: " + model.getNom());
    }

    @Test
    @DisplayName("Model null to DTO => null")
    void toDTO_withNullModel_shouldReturnNull() {
        System.out.println("\nTEST 3 : toDTO avec null - Vérification du comportement avec un modèle null");

        // Act
        System.out.println("▶️ Exécution: Appel de ZombieDTOMapper.toDTO(null)");
        ZombieDTO result = ZombieDTOMapper.toDTO(null);

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
        System.out.println("▶️ Exécution: Appel de ZombieDTOMapper.toModel(null)");
        Zombie result = ZombieDTOMapper.toModel(null);

        // Assert
        System.out.println("🔎 Vérification: Le résultat devrait être null");
        assertNull(result, "Le résultat devrait être null quand le DTO est null");

        System.out.println("✅ Succès: null retourné comme attendu");
    }
}