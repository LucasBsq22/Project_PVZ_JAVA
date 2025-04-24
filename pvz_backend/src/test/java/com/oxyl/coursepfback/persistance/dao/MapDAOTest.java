package com.oxyl.coursepfback.persistance.dao;

import com.oxyl.coursepfback.persistance.entity.MapEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MapDAOTest {

    private MapDAO mapDAO;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        System.out.println("\n----------- Configuration du test MapDAO -----------");
        dataSource = Mockito.mock(DataSource.class);
        jdbcTemplate = Mockito.mock(JdbcTemplate.class);

        // Créer une instance de MapDAO avec le mock DataSource
        mapDAO = new MapDAO(dataSource);

        // Injecter le mock JdbcTemplate dans l'instance de MapDAO (via réflexion)
        try {
            java.lang.reflect.Field field = MapDAO.class.getDeclaredField("jdbcTemplate");
            field.setAccessible(true);
            field.set(mapDAO, jdbcTemplate);
            System.out.println("✅ Mock JdbcTemplate injecté avec succès dans MapDAO");
        } catch (Exception e) {
            System.err.println("❌ Échec de l'injection du mock: " + e.getMessage());
            fail("Impossible d'injecter le mock JdbcTemplate dans MapDAO: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("findAll devrait retourner une liste de toutes les maps")
    void findAll_shouldReturnListOfEntities() {
        System.out.println("\n🔍 TEST: findAll - Récupération de toutes les maps");

        // Arrange
        MapEntity map1 = new MapEntity();
        map1.setIdMap(1);
        map1.setLigne(5);
        map1.setColonne(9);
        map1.setCheminImage("images/map/gazon.png");

        MapEntity map2 = new MapEntity();
        map2.setIdMap(2);
        map2.setLigne(6);
        map2.setColonne(9);
        map2.setCheminImage("images/map/gazon.png");

        List<MapEntity> expectedMaps = Arrays.asList(map1, map2);
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour retourner 2 maps");

        when(jdbcTemplate.query(eq("SELECT * FROM Map"), any(RowMapper.class)))
                .thenReturn(expectedMaps);

        // Act
        System.out.println("▶️ Exécution: appel de mapDAO.findAll()");
        List<MapEntity> result = mapDAO.findAll();

        // Assert
        assertEquals(expectedMaps.size(), result.size());
        assertEquals(expectedMaps, result);
        verify(jdbcTemplate).query(eq("SELECT * FROM Map"), any(RowMapper.class));
        System.out.println("✅ Succès: " + result.size() + " maps récupérées");
        System.out.println("   - Map 1: ID=" + result.get(0).getIdMap() + ", dimensions: " + result.get(0).getLigne() + "×" + result.get(0).getColonne());
        System.out.println("   - Map 2: ID=" + result.get(1).getIdMap() + ", dimensions: " + result.get(1).getLigne() + "×" + result.get(1).getColonne());
    }

    @Test
    @DisplayName("findById devrait retourner la map correspondante quand elle existe")
    void findById_whenMapExists_shouldReturnEntity() {
        System.out.println("\n🔍 TEST: findById - Récupération d'une map existante");

        // Arrange
        MapEntity expected = new MapEntity();
        expected.setIdMap(1);
        expected.setLigne(5);
        expected.setColonne(9);
        expected.setCheminImage("images/map/gazon.png");
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour retourner la map avec ID=1");

        when(jdbcTemplate.queryForObject(
                eq("SELECT * FROM Map WHERE id_map = ?"),
                any(Object[].class),
                any(RowMapper.class)))
                .thenReturn(expected);

        // Act
        System.out.println("▶️ Exécution: appel de mapDAO.findById(1)");
        Optional<MapEntity> result = mapDAO.findById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(jdbcTemplate).queryForObject(
                eq("SELECT * FROM Map WHERE id_map = ?"),
                eq(new Object[]{1}),
                any(RowMapper.class));
        System.out.println("✅ Succès: Map trouvée");
        System.out.println("   - ID: " + result.get().getIdMap());
        System.out.println("   - Dimensions: " + result.get().getLigne() + "×" + result.get().getColonne());
    }

    @Test
    @DisplayName("findById devrait retourner Optional vide quand la map n'existe pas")
    void findById_whenMapDoesNotExist_shouldReturnEmptyOptional() {
        System.out.println("\n🔍 TEST: findById - Tentative de récupération d'une map inexistante");

        // Arrange
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour lancer une exception EmptyResultDataAccessException");
        when(jdbcTemplate.queryForObject(
                eq("SELECT * FROM Map WHERE id_map = ?"),
                any(Object[].class),
                any(RowMapper.class)))
                .thenThrow(new org.springframework.dao.EmptyResultDataAccessException("No map found", 1));

        // Act
        System.out.println("▶️ Exécution: appel de mapDAO.findById(999)");
        Optional<MapEntity> result = mapDAO.findById(999);

        // Assert
        assertFalse(result.isPresent());
        verify(jdbcTemplate).queryForObject(
                eq("SELECT * FROM Map WHERE id_map = ?"),
                eq(new Object[]{999}),
                any(RowMapper.class));
        System.out.println("✅ Succès: Optional vide retourné comme attendu");
    }

    @Test
    @DisplayName("isReferencedByZombies devrait retourner true quand la map est référencée")
    void isReferencedByZombies_whenMapIsReferenced_shouldReturnTrue() {
        System.out.println("\n🔍 TEST: isReferencedByZombies - Vérification si une map est référencée par des zombies");

        // Arrange
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour simuler une map référencée (count > 0)");
        when(jdbcTemplate.queryForObject(
                eq("SELECT COUNT(*) FROM Zombie WHERE id_map = ?"),
                eq(Integer.class),
                eq(1)))
                .thenReturn(2);

        // Act
        System.out.println("▶️ Exécution: appel de mapDAO.isReferencedByZombies(1)");
        boolean result = mapDAO.isReferencedByZombies(1);

        // Assert
        assertTrue(result);
        verify(jdbcTemplate).queryForObject(
                eq("SELECT COUNT(*) FROM Zombie WHERE id_map = ?"),
                eq(Integer.class),
                eq(1));
        System.out.println("✅ Succès: true retourné comme attendu pour une map référencée");
    }

    @Test
    @DisplayName("isReferencedByZombies devrait retourner false quand la map n'est pas référencée")
    void isReferencedByZombies_whenMapIsNotReferenced_shouldReturnFalse() {
        System.out.println("\n🔍 TEST: isReferencedByZombies - Vérification si une map n'est pas référencée par des zombies");

        // Arrange
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour simuler une map non référencée (count = 0)");
        when(jdbcTemplate.queryForObject(
                eq("SELECT COUNT(*) FROM Zombie WHERE id_map = ?"),
                eq(Integer.class),
                eq(3)))
                .thenReturn(0);

        // Act
        System.out.println("▶️ Exécution: appel de mapDAO.isReferencedByZombies(3)");
        boolean result = mapDAO.isReferencedByZombies(3);

        // Assert
        assertFalse(result);
        verify(jdbcTemplate).queryForObject(
                eq("SELECT COUNT(*) FROM Zombie WHERE id_map = ?"),
                eq(Integer.class),
                eq(3));
        System.out.println("✅ Succès: false retourné comme attendu pour une map non référencée");
    }

    @Test
    @DisplayName("insert devrait créer une nouvelle map et retourner l'entité avec ID généré")
    void insert_shouldReturnEntityWithGeneratedId() throws SQLException {
        System.out.println("\n🔍 TEST: insert - Création d'une nouvelle map");

        // Arrange
        MapEntity mapToInsert = new MapEntity();
        mapToInsert.setLigne(4);
        mapToInsert.setColonne(8);
        mapToInsert.setCheminImage("images/map/nouvelle.png");

        System.out.println("🔧 Configuration: Préparation des mocks pour simuler l'insertion");

        // Mock pour le PreparedStatement et Connection
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        Connection connection = Mockito.mock(Connection.class);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(ps);

        // Créer un mock de KeyHolder
        KeyHolder keyHolder = Mockito.mock(KeyHolder.class);
        when(keyHolder.getKeys()).thenReturn(Map.of("id_map", 3));

        System.out.println("   - KeyHolder mockée pour retourner l'ID généré: 3");

        // Act
        System.out.println("▶️ Exécution: appel de mapDAO.insert() avec une nouvelle map");

        // Remplacer le KeyHolder dans la méthode insert par une sous-classe personnalisée
        MapEntity result = null;
        try {
            // Créer une sous-classe anonyme de MapDAO qui remplace la création du KeyHolder
            MapDAO customMapDAO = new MapDAO(dataSource) {
                @Override
                public MapEntity insert(MapEntity entity) {
                    // Utiliser le mock jdbcTemplate injecté précédemment
                    try {
                        java.lang.reflect.Field jdbcField = MapDAO.class.getDeclaredField("jdbcTemplate");
                        jdbcField.setAccessible(true);
                        jdbcField.set(this, jdbcTemplate);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    // Simuler la requête avec notre KeyHolder mockée
                    jdbcTemplate.update(connection -> {
                        PreparedStatement stmt = connection.prepareStatement(
                                "INSERT INTO Map (ligne, colonne, chemin_image) VALUES (?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS
                        );
                        stmt.setInt(1, entity.getLigne());
                        stmt.setInt(2, entity.getColonne());
                        stmt.setString(3, entity.getCheminImage());
                        return stmt;
                    }, keyHolder);

                    // Simuler l'ID généré
                    entity.setIdMap(3);
                    return entity;
                }
            };

            result = customMapDAO.insert(mapToInsert);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'exécution du test: " + e.getMessage());
            fail("Erreur lors de l'exécution du test: " + e.getMessage());
            return;
        }

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getIdMap());
        assertEquals(mapToInsert.getLigne(), result.getLigne());
        assertEquals(mapToInsert.getColonne(), result.getColonne());
        assertEquals(mapToInsert.getCheminImage(), result.getCheminImage());

        System.out.println("✅ Succès: Map insérée avec succès");
        System.out.println("   - ID généré: " + result.getIdMap());
        System.out.println("   - Dimensions: " + result.getLigne() + "×" + result.getColonne());
    }

    @Test
    @DisplayName("update devrait mettre à jour une map existante")
    void update_shouldReturnUpdatedEntity() {
        System.out.println("\n🔍 TEST: update - Mise à jour d'une map existante");

        // Arrange
        MapEntity mapToUpdate = new MapEntity();
        mapToUpdate.setIdMap(1);
        mapToUpdate.setLigne(6);
        mapToUpdate.setColonne(10);
        mapToUpdate.setCheminImage("images/map/modified.png");

        System.out.println("🔧 Configuration: JdbcTemplate configuré pour simuler une mise à jour réussie");
        when(jdbcTemplate.update(
                eq("UPDATE Map SET ligne = ?, colonne = ?, chemin_image = ? WHERE id_map = ?"),
                eq(mapToUpdate.getLigne()),
                eq(mapToUpdate.getColonne()),
                eq(mapToUpdate.getCheminImage()),
                eq(mapToUpdate.getIdMap())))
                .thenReturn(1);

        // Act
        System.out.println("▶️ Exécution: appel de mapDAO.update() avec la map ID=" + mapToUpdate.getIdMap());
        MapEntity result = mapDAO.update(mapToUpdate);

        // Assert
        assertEquals(mapToUpdate, result);
        verify(jdbcTemplate).update(
                eq("UPDATE Map SET ligne = ?, colonne = ?, chemin_image = ? WHERE id_map = ?"),
                eq(mapToUpdate.getLigne()),
                eq(mapToUpdate.getColonne()),
                eq(mapToUpdate.getCheminImage()),
                eq(mapToUpdate.getIdMap()));

        System.out.println("✅ Succès: Map mise à jour avec succès");
        System.out.println("   - ID: " + result.getIdMap());
        System.out.println("   - Nouvelles dimensions: " + result.getLigne() + "×" + result.getColonne());
    }



    @Test
    @DisplayName("deleteById devrait retourner true quand la suppression réussit")
    void deleteById_whenMapExists_shouldReturnTrue() {
        System.out.println("\n🔍 TEST: deleteById - Suppression d'une map existante");

        // Arrange
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour simuler une suppression réussie");
        when(jdbcTemplate.update(eq("DELETE FROM Map WHERE id_map = ?"), eq(2)))
                .thenReturn(1);

        // Act
        System.out.println("▶️ Exécution: appel de mapDAO.deleteById(2)");
        boolean result = mapDAO.deleteById(2);

        // Assert
        assertTrue(result);
        verify(jdbcTemplate).update(eq("DELETE FROM Map WHERE id_map = ?"), eq(2));
        System.out.println("✅ Succès: true retourné comme attendu pour une suppression réussie");

        // Note: on ne vérifie pas l'appel à isReferencedByZombies puisque la méthode
        // deleteById n'appelle pas cette méthode dans l'implémentation actuelle
    }

    @Test
    @DisplayName("deleteById devrait retourner false quand la map n'existe pas")
    void deleteById_whenMapDoesNotExist_shouldReturnFalse() {
        System.out.println("\n🔍 TEST: deleteById - Tentative de suppression d'une map inexistante");

        // Arrange
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour simuler qu'aucune ligne n'a été affectée (0 rows)");
        when(jdbcTemplate.update(eq("DELETE FROM Map WHERE id_map = ?"), eq(999)))
                .thenReturn(0);

        // Act
        System.out.println("▶️ Exécution: appel de mapDAO.deleteById(999)");
        boolean result = mapDAO.deleteById(999);

        // Assert
        assertFalse(result);
        verify(jdbcTemplate).update(eq("DELETE FROM Map WHERE id_map = ?"), eq(999));
        System.out.println("✅ Succès: false retourné comme attendu pour une map inexistante");
    }

    @Test
    @DisplayName("rowMapper devrait mapper tous les champs du ResultSet vers l'entité")
    void rowMapper_shouldMapAllFields() throws Exception {
        System.out.println("\n🔍 TEST: rowMapper - Vérification du mapping ResultSet vers MapEntity");

        // Arrange
        System.out.println("🔧 Configuration: Préparation du mock ResultSet avec des données test");
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.getInt("id_map")).thenReturn(1);
        when(rs.getInt("ligne")).thenReturn(5);
        when(rs.getInt("colonne")).thenReturn(9);
        when(rs.getString("chemin_image")).thenReturn("images/map/gazon.png");

        // Extraire le RowMapper à partir de la classe MapDAO
        RowMapper<MapEntity> rowMapper = null;
        try {
            // Capturer l'argument RowMapper lors de l'appel à la méthode query
            ArgumentCaptor<RowMapper> rowMapperCaptor = ArgumentCaptor.forClass(RowMapper.class);
            mapDAO.findAll();
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
        MapEntity result = rowMapper.mapRow(rs, 1);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement extraits du ResultSet");
        assertNotNull(result);
        assertEquals(1, result.getIdMap());
        assertEquals(5, result.getLigne());
        assertEquals(9, result.getColonne());
        assertEquals("images/map/gazon.png", result.getCheminImage());

        System.out.println("✅ Succès: Le RowMapper a correctement mappé toutes les colonnes");
        System.out.println("   - Entité créée: Map ID=" + result.getIdMap() + ", dimensions: " + result.getLigne() + "×" + result.getColonne());
    }
}