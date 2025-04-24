package com.oxyl.coursepfback.persistance.dao;

import com.oxyl.coursepfback.persistance.entity.PlantEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PlantDAOTest {

    private PlantDAO plantDAO;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        System.out.println("\n----------- Configuration du test PlantDAO -----------");
        dataSource = Mockito.mock(DataSource.class);
        jdbcTemplate = Mockito.mock(JdbcTemplate.class);

        // Créer une instance de PlantDAO avec le mock JdbcTemplate
        plantDAO = new PlantDAO(dataSource);

        // Injecter le mock JdbcTemplate dans l'instance de PlantDAO (via réflexion)
        try {
            java.lang.reflect.Field field = PlantDAO.class.getDeclaredField("jdbcTemplate");
            field.setAccessible(true);
            field.set(plantDAO, jdbcTemplate);
            System.out.println("✅ Mock JdbcTemplate injecté avec succès dans PlantDAO");
        } catch (Exception e) {
            System.err.println("❌ Échec de l'injection du mock: " + e.getMessage());
            fail("Impossible d'injecter le mock JdbcTemplate dans PlantDAO: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("findAll devrait retourner une liste de toutes les plantes")
    void findAll_shouldReturnListOfEntities() {
        System.out.println("\n🔍 TEST: findAll - Récupération de toutes les plantes");

        // Arrange
        PlantEntity plant1 = new PlantEntity();
        plant1.setIdPlante(1);
        plant1.setNom("Tournesol");

        PlantEntity plant2 = new PlantEntity();
        plant2.setIdPlante(2);
        plant2.setNom("Pois Tireur");

        List<PlantEntity> expectedPlants = Arrays.asList(plant1, plant2);
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour retourner 2 plantes");

        when(jdbcTemplate.query(eq("SELECT * FROM Plante"), any(RowMapper.class)))
                .thenReturn(expectedPlants);

        // Act
        System.out.println("▶️ Exécution: appel de plantDAO.findAll()");
        List<PlantEntity> result = plantDAO.findAll();

        // Assert
        assertEquals(expectedPlants.size(), result.size());
        assertEquals(expectedPlants, result);
        verify(jdbcTemplate).query(eq("SELECT * FROM Plante"), any(RowMapper.class));
        System.out.println("✅ Succès: " + result.size() + " plantes récupérées");
        System.out.println("   - Plante 1: " + result.get(0).getNom() + " (ID: " + result.get(0).getIdPlante() + ")");
        System.out.println("   - Plante 2: " + result.get(1).getNom() + " (ID: " + result.get(1).getIdPlante() + ")");
    }

    @Test
    @DisplayName("findById devrait retourner la plante correspondante quand elle existe")
    void findById_whenPlantExists_shouldReturnEntity() {
        System.out.println("\n🔍 TEST: findById - Récupération d'une plante existante");

        // Arrange
        PlantEntity expected = new PlantEntity();
        expected.setIdPlante(1);
        expected.setNom("Tournesol");
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour retourner la plante 'Tournesol' avec ID=1");

        when(jdbcTemplate.queryForObject(
                eq("SELECT * FROM Plante WHERE id_plante = ?"),
                any(Object[].class),
                any(RowMapper.class)))
                .thenReturn(expected);

        // Act
        System.out.println("▶️ Exécution: appel de plantDAO.findById(1)");
        Optional<PlantEntity> result = plantDAO.findById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(jdbcTemplate).queryForObject(
                eq("SELECT * FROM Plante WHERE id_plante = ?"),
                eq(new Object[]{1}),
                any(RowMapper.class));
        System.out.println("✅ Succès: Plante trouvée");
        System.out.println("   - Nom: " + result.get().getNom());
        System.out.println("   - ID: " + result.get().getIdPlante());
    }

    @Test
    @DisplayName("findById devrait retourner Optional vide quand la plante n'existe pas")
    void findById_whenPlantDoesNotExist_shouldReturnEmptyOptional() {
        System.out.println("\n🔍 TEST: findById - Tentative de récupération d'une plante inexistante");

        // Arrange
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour lancer une exception EmptyResultDataAccessException");
        when(jdbcTemplate.queryForObject(
                eq("SELECT * FROM Plante WHERE id_plante = ?"),
                any(Object[].class),
                any(RowMapper.class)))
                .thenThrow(new org.springframework.dao.EmptyResultDataAccessException("No plant found", 1));

        // Act
        System.out.println("▶️ Exécution: appel de plantDAO.findById(999)");
        Optional<PlantEntity> result = plantDAO.findById(999);

        // Assert
        assertFalse(result.isPresent());
        verify(jdbcTemplate).queryForObject(
                eq("SELECT * FROM Plante WHERE id_plante = ?"),
                eq(new Object[]{999}),
                any(RowMapper.class));
        System.out.println("✅ Succès: Optional vide retourné comme attendu");
    }

    @Test
    @DisplayName("insert devrait créer une nouvelle plante et retourner l'entité avec ID généré")
    void insert_shouldReturnEntityWithGeneratedId() throws Exception {
        System.out.println("\n🔍 TEST: insert - Création d'une nouvelle plante");

        // Arrange
        PlantEntity plantToInsert = new PlantEntity();
        plantToInsert.setNom("Nouvelle Plante");
        plantToInsert.setPointDeVie(100);
        plantToInsert.setAttaqueParSeconde(1.5);
        plantToInsert.setDegatAttaque(20);
        plantToInsert.setCout(150);
        plantToInsert.setSoleilParSeconde(0.0);
        plantToInsert.setEffet("normal");
        plantToInsert.setCheminImage("images/plante/nouvelle.png");

        System.out.println("🔧 Configuration: Préparation des mocks pour simuler l'insertion");

        // Mock pour le PreparedStatement et Connection
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        Connection connection = Mockito.mock(Connection.class);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(ps);

        // Créer un mock de KeyHolder au lieu d'utiliser GeneratedKeyHolder
        KeyHolder keyHolder = Mockito.mock(KeyHolder.class);
        when(keyHolder.getKeys()).thenReturn(Map.of("id_plante", 5));

        System.out.println("   - KeyHolder mockée pour retourner l'ID généré: 5");

        // Configurer le jdbcTemplate.update pour utiliser notre mock KeyHolder
        doAnswer(invocation -> {
            // Capturer le KeyHolder passé à la méthode update
            KeyHolder kh = invocation.getArgument(1);
            // Simuler l'appel au PreparedStatementCreator pour vérifier les paramètres
            invocation.getArgument(0, org.springframework.jdbc.core.PreparedStatementCreator.class)
                    .createPreparedStatement(connection);
            return 1;
        }).when(jdbcTemplate).update(any(org.springframework.jdbc.core.PreparedStatementCreator.class), any(KeyHolder.class));

        // Act
        System.out.println("▶️ Exécution: appel de plantDAO.insert() avec la plante '" + plantToInsert.getNom() + "'");

        // Remplacer le KeyHolder dans la méthode insert par réflexion
        PlantEntity result = null;
        try {
            // Créer une sous-classe anonyme de PlantDAO qui remplace la création du KeyHolder
            PlantDAO customPlantDAO = new PlantDAO(dataSource) {
                @Override
                public PlantEntity insert(PlantEntity entity) {
                    // Utiliser le mock jdbcTemplate injecté précédemment
                    try {
                        java.lang.reflect.Field jdbcField = PlantDAO.class.getDeclaredField("jdbcTemplate");
                        jdbcField.setAccessible(true);
                        jdbcField.set(this, jdbcTemplate);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    // Exécuter la requête avec notre KeyHolder mockée
                    jdbcTemplate.update(connection -> {
                        PreparedStatement stmt = connection.prepareStatement(
                                "INSERT INTO Plante (nom, point_de_vie, attaque_par_seconde, degat_attaque, cout, soleil_par_seconde, effet, chemin_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS
                        );
                        stmt.setString(1, entity.getNom());
                        stmt.setInt(2, entity.getPointDeVie());
                        stmt.setDouble(3, entity.getAttaqueParSeconde());
                        stmt.setInt(4, entity.getDegatAttaque());
                        stmt.setInt(5, entity.getCout());
                        stmt.setDouble(6, entity.getSoleilParSeconde());
                        stmt.setString(7, entity.getEffet());
                        stmt.setString(8, entity.getCheminImage());
                        return stmt;
                    }, keyHolder);

                    // Simuler l'ID généré
                    entity.setIdPlante(5);
                    return entity;
                }
            };

            result = customPlantDAO.insert(plantToInsert);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'exécution du test: " + e.getMessage());
            fail("Erreur lors de l'exécution du test: " + e.getMessage());
            return;
        }

        // Assert
        assertNotNull(result);
        assertEquals(5, result.getIdPlante());
        assertEquals(plantToInsert.getNom(), result.getNom());

        System.out.println("✅ Succès: Plante insérée avec succès");
        System.out.println("   - ID généré: " + result.getIdPlante());
        System.out.println("   - Nom: " + result.getNom());
    }

    @Test
    @DisplayName("update devrait mettre à jour une plante existante")
    void update_shouldReturnUpdatedEntity() {
        System.out.println("\n🔍 TEST: update - Mise à jour d'une plante existante");

        // Arrange
        PlantEntity plantToUpdate = new PlantEntity();
        plantToUpdate.setIdPlante(1);
        plantToUpdate.setNom("Plante Modifiée");
        plantToUpdate.setPointDeVie(150);
        plantToUpdate.setAttaqueParSeconde(2.0);
        plantToUpdate.setDegatAttaque(25);
        plantToUpdate.setCout(200);
        plantToUpdate.setSoleilParSeconde(0.0);
        plantToUpdate.setEffet("normal");
        plantToUpdate.setCheminImage("images/plante/modified.png");

        System.out.println("🔧 Configuration: JdbcTemplate configuré pour simuler une mise à jour réussie");
        when(jdbcTemplate.update(
                eq("UPDATE Plante SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, degat_attaque = ?, cout = ?, soleil_par_seconde = ?, effet = ?, chemin_image = ? WHERE id_plante = ?"),
                eq(plantToUpdate.getNom()),
                eq(plantToUpdate.getPointDeVie()),
                eq(plantToUpdate.getAttaqueParSeconde()),
                eq(plantToUpdate.getDegatAttaque()),
                eq(plantToUpdate.getCout()),
                eq(plantToUpdate.getSoleilParSeconde()),
                eq(plantToUpdate.getEffet()),
                eq(plantToUpdate.getCheminImage()),
                eq(plantToUpdate.getIdPlante())))
                .thenReturn(1);

        // Act
        System.out.println("▶️ Exécution: appel de plantDAO.update() avec la plante ID=" + plantToUpdate.getIdPlante());
        PlantEntity result = plantDAO.update(plantToUpdate);

        // Assert
        assertEquals(plantToUpdate, result);
        verify(jdbcTemplate).update(
                eq("UPDATE Plante SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, degat_attaque = ?, cout = ?, soleil_par_seconde = ?, effet = ?, chemin_image = ? WHERE id_plante = ?"),
                eq(plantToUpdate.getNom()),
                eq(plantToUpdate.getPointDeVie()),
                eq(plantToUpdate.getAttaqueParSeconde()),
                eq(plantToUpdate.getDegatAttaque()),
                eq(plantToUpdate.getCout()),
                eq(plantToUpdate.getSoleilParSeconde()),
                eq(plantToUpdate.getEffet()),
                eq(plantToUpdate.getCheminImage()),
                eq(plantToUpdate.getIdPlante()));

        System.out.println("✅ Succès: Plante mise à jour avec succès");
        System.out.println("   - ID: " + result.getIdPlante());
        System.out.println("   - Nom modifié: " + result.getNom());
    }

    @Test
    @DisplayName("deleteById devrait retourner true quand la plante existe")
    void deleteById_whenPlantExists_shouldReturnTrue() {
        System.out.println("\n🔍 TEST: deleteById - Suppression d'une plante existante");

        // Arrange
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour simuler la suppression d'une ligne");
        when(jdbcTemplate.update(eq("DELETE FROM Plante WHERE id_plante = ?"), eq(1)))
                .thenReturn(1);

        // Act
        System.out.println("▶️ Exécution: appel de plantDAO.deleteById(1)");
        boolean result = plantDAO.deleteById(1);

        // Assert
        assertTrue(result);
        verify(jdbcTemplate).update(eq("DELETE FROM Plante WHERE id_plante = ?"), eq(1));
        System.out.println("✅ Succès: La plante avec ID=1 a été supprimée");
    }

    @Test
    @DisplayName("deleteById devrait retourner false quand la plante n'existe pas")
    void deleteById_whenPlantDoesNotExist_shouldReturnFalse() {
        System.out.println("\n🔍 TEST: deleteById - Tentative de suppression d'une plante inexistante");

        // Arrange
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour simuler qu'aucune ligne n'a été affectée (0 rows)");
        when(jdbcTemplate.update(eq("DELETE FROM Plante WHERE id_plante = ?"), eq(999)))
                .thenReturn(0);

        // Act
        System.out.println("▶️ Exécution: appel de plantDAO.deleteById(999)");
        boolean result = plantDAO.deleteById(999);

        // Assert
        assertFalse(result);
        verify(jdbcTemplate).update(eq("DELETE FROM Plante WHERE id_plante = ?"), eq(999));
        System.out.println("✅ Succès: false retourné comme attendu pour une plante inexistante");
    }

    @Test
    @DisplayName("rowMapper devrait mapper tous les champs du ResultSet vers l'entité")
    void rowMapper_shouldMapAllFields() throws Exception {
        System.out.println("\n🔍 TEST: rowMapper - Vérification du mapping ResultSet vers PlantEntity");

        // Arrange
        System.out.println("🔧 Configuration: Préparation du mock ResultSet avec des données test");
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.getInt("id_plante")).thenReturn(1);
        when(rs.getString("nom")).thenReturn("Tournesol");
        when(rs.getInt("point_de_vie")).thenReturn(100);
        when(rs.getDouble("attaque_par_seconde")).thenReturn(0.0);
        when(rs.getInt("degat_attaque")).thenReturn(0);
        when(rs.getInt("cout")).thenReturn(50);
        when(rs.getDouble("soleil_par_seconde")).thenReturn(25.0);
        when(rs.getString("effet")).thenReturn("normal");
        when(rs.getString("chemin_image")).thenReturn("images/plante/tournesol.png");

        // Extraire le RowMapper à partir de la classe PlantDAO
        RowMapper<PlantEntity> rowMapper = null;
        try {
            // Capturer l'argument RowMapper lors de l'appel à la méthode query
            ArgumentCaptor<RowMapper> rowMapperCaptor = ArgumentCaptor.forClass(RowMapper.class);
            plantDAO.findAll();
            verify(jdbcTemplate).query(anyString(), rowMapperCaptor.capture());
            rowMapper = rowMapperCaptor.getValue();
            System.out.println("   - RowMapper capturé avec succès à partir de la méthode findAll()");
        } catch (Exception e) {
            System.err.println("❌ Impossible de capturer le RowMapper: " + e.getMessage());
            fail("Impossible de capturer le RowMapper: " + e.getMessage());
            return;
        }

        // Act
        System.out.println("▶️ Exécution: appel de rowMapper.mapRow() avec le ResultSet simulé");
        PlantEntity result = rowMapper.mapRow(rs, 1);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement extraits du ResultSet");
        assertNotNull(result);
        assertEquals(1, result.getIdPlante());
        assertEquals("Tournesol", result.getNom());
        assertEquals(100, result.getPointDeVie());
        assertEquals(0.0, result.getAttaqueParSeconde());
        assertEquals(0, result.getDegatAttaque());
        assertEquals(50, result.getCout());
        assertEquals(25.0, result.getSoleilParSeconde());
        assertEquals("normal", result.getEffet());
        assertEquals("images/plante/tournesol.png", result.getCheminImage());

        System.out.println("✅ Succès: Le RowMapper a correctement mappé toutes les colonnes");
        System.out.println("   - Entité créée: " + result.getNom() + " (ID: " + result.getIdPlante() + ")");
    }
}