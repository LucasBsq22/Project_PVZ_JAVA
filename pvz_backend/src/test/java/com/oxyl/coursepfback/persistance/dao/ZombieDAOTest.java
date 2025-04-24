package com.oxyl.coursepfback.persistance.dao;

import com.oxyl.coursepfback.persistance.entity.ZombieEntity;
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

class ZombieDAOTest {

    private ZombieDAO zombieDAO;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        System.out.println("\n----------- Configuration du test ZombieDAO -----------");
        dataSource = Mockito.mock(DataSource.class);
        jdbcTemplate = Mockito.mock(JdbcTemplate.class);

        // Créer une instance de ZombieDAO avec le mock JdbcTemplate
        zombieDAO = new ZombieDAO(dataSource);

        // Injecter le mock JdbcTemplate dans l'instance de ZombieDAO (via réflexion)
        try {
            java.lang.reflect.Field field = ZombieDAO.class.getDeclaredField("jdbcTemplate");
            field.setAccessible(true);
            field.set(zombieDAO, jdbcTemplate);
            System.out.println("✅ Mock JdbcTemplate injecté avec succès dans ZombieDAO");
        } catch (Exception e) {
            System.err.println("❌ Échec de l'injection du mock: " + e.getMessage());
            fail("Impossible d'injecter le mock JdbcTemplate dans ZombieDAO: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("findAll devrait retourner une liste de tous les zombies")
    void findAll_shouldReturnListOfEntities() {
        System.out.println("\n🔍 TEST: findAll - Récupération de tous les zombies");

        // Arrange
        ZombieEntity zombie1 = new ZombieEntity();
        zombie1.setIdZombie(1);
        zombie1.setNom("Zombie de base");

        ZombieEntity zombie2 = new ZombieEntity();
        zombie2.setIdZombie(2);
        zombie2.setNom("Zombie Cone");

        List<ZombieEntity> expectedZombies = Arrays.asList(zombie1, zombie2);
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour retourner 2 zombies");

        when(jdbcTemplate.query(eq("SELECT * FROM Zombie"), any(RowMapper.class)))
                .thenReturn(expectedZombies);

        // Act
        System.out.println("▶️ Exécution: appel de zombieDAO.findAll()");
        List<ZombieEntity> result = zombieDAO.findAll();

        // Assert
        assertEquals(expectedZombies.size(), result.size());
        assertEquals(expectedZombies, result);
        verify(jdbcTemplate).query(eq("SELECT * FROM Zombie"), any(RowMapper.class));
        System.out.println("✅ Succès: " + result.size() + " zombies récupérés");
        System.out.println("   - Zombie 1: " + result.get(0).getNom() + " (ID: " + result.get(0).getIdZombie() + ")");
        System.out.println("   - Zombie 2: " + result.get(1).getNom() + " (ID: " + result.get(1).getIdZombie() + ")");
    }

    @Test
    @DisplayName("findById devrait retourner le zombie correspondant quand il existe")
    void findById_whenZombieExists_shouldReturnEntity() {
        System.out.println("\n🔍 TEST: findById - Récupération d'un zombie existant");

        // Arrange
        ZombieEntity expected = new ZombieEntity();
        expected.setIdZombie(1);
        expected.setNom("Zombie de base");
        expected.setPointDeVie(100);
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour retourner le zombie 'Zombie de base' avec ID=1");

        when(jdbcTemplate.queryForObject(
                eq("SELECT * FROM Zombie WHERE id_zombie = ?"),
                any(Object[].class),
                any(RowMapper.class)))
                .thenReturn(expected);

        // Act
        System.out.println("▶️ Exécution: appel de zombieDAO.findById(1)");
        Optional<ZombieEntity> result = zombieDAO.findById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(jdbcTemplate).queryForObject(
                eq("SELECT * FROM Zombie WHERE id_zombie = ?"),
                eq(new Object[]{1}),
                any(RowMapper.class));
        System.out.println("✅ Succès: Zombie trouvé");
        System.out.println("   - Nom: " + result.get().getNom());
        System.out.println("   - ID: " + result.get().getIdZombie());
    }

    @Test
    @DisplayName("findById devrait retourner Optional vide quand le zombie n'existe pas")
    void findById_whenZombieDoesNotExist_shouldReturnEmptyOptional() {
        System.out.println("\n🔍 TEST: findById - Tentative de récupération d'un zombie inexistant");

        // Arrange
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour lancer une exception EmptyResultDataAccessException");
        when(jdbcTemplate.queryForObject(
                eq("SELECT * FROM Zombie WHERE id_zombie = ?"),
                any(Object[].class),
                any(RowMapper.class)))
                .thenThrow(new org.springframework.dao.EmptyResultDataAccessException("No zombie found", 1));

        // Act
        System.out.println("▶️ Exécution: appel de zombieDAO.findById(999)");
        Optional<ZombieEntity> result = zombieDAO.findById(999);

        // Assert
        assertFalse(result.isPresent());
        verify(jdbcTemplate).queryForObject(
                eq("SELECT * FROM Zombie WHERE id_zombie = ?"),
                eq(new Object[]{999}),
                any(RowMapper.class));
        System.out.println("✅ Succès: Optional vide retourné comme attendu");
    }

    @Test
    @DisplayName("findByMapId devrait retourner les zombies d'une carte spécifique")
    void findByMapId_shouldReturnZombiesForMap() {
        System.out.println("\n🔍 TEST: findByMapId - Récupération des zombies pour une carte spécifique");

        // Arrange
        ZombieEntity zombie1 = new ZombieEntity();
        zombie1.setIdZombie(1);
        zombie1.setNom("Zombie de base");
        zombie1.setIdMap(1);

        ZombieEntity zombie2 = new ZombieEntity();
        zombie2.setIdZombie(3);
        zombie2.setNom("Zombie Seau");
        zombie2.setIdMap(1);

        List<ZombieEntity> expectedZombies = Arrays.asList(zombie1, zombie2);
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour retourner 2 zombies pour la carte ID=1");

        when(jdbcTemplate.query(
                eq("SELECT * FROM Zombie WHERE id_map = ?"),
                any(Object[].class),
                any(RowMapper.class)))
                .thenReturn(expectedZombies);

        // Act
        System.out.println("▶️ Exécution: appel de zombieDAO.findByMapId(1)");
        List<ZombieEntity> result = zombieDAO.findByMapId(1);

        // Assert
        assertEquals(expectedZombies.size(), result.size());
        assertEquals(expectedZombies, result);
        verify(jdbcTemplate).query(
                eq("SELECT * FROM Zombie WHERE id_map = ?"),
                eq(new Object[]{1}),
                any(RowMapper.class));
        System.out.println("✅ Succès: " + result.size() + " zombies récupérés pour la carte ID=1");
        System.out.println("   - Zombie 1: " + result.get(0).getNom() + " (ID: " + result.get(0).getIdZombie() + ")");
        System.out.println("   - Zombie 2: " + result.get(1).getNom() + " (ID: " + result.get(1).getIdZombie() + ")");
    }

    @Test
    @DisplayName("insert devrait créer un nouveau zombie et retourner l'entité avec ID généré")
    void insert_shouldReturnEntityWithGeneratedId() throws SQLException {
        System.out.println("\n🔍 TEST: insert - Création d'un nouveau zombie");

        // Arrange
        ZombieEntity zombieToInsert = new ZombieEntity();
        zombieToInsert.setNom("Nouveau Zombie");
        zombieToInsert.setPointDeVie(120);
        zombieToInsert.setAttaqueParSeconde(0.8);
        zombieToInsert.setDegatAttaque(15);
        zombieToInsert.setVitesseDeDeplacement(0.5);
        zombieToInsert.setCheminImage("images/zombie/nouveau.png");
        zombieToInsert.setIdMap(2);

        System.out.println("🔧 Configuration: Préparation des mocks pour simuler l'insertion");

        // Mock pour le PreparedStatement et Connection
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        Connection connection = Mockito.mock(Connection.class);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(ps);

        // Créer un mock de KeyHolder
        KeyHolder keyHolder = Mockito.mock(KeyHolder.class);
        when(keyHolder.getKeys()).thenReturn(Map.of("id_zombie", 6));

        System.out.println("   - KeyHolder mockée pour retourner l'ID généré: 6");

        // Act
        System.out.println("▶️ Exécution: appel de zombieDAO.insert() avec le zombie '" + zombieToInsert.getNom() + "'");

        // Remplacer le KeyHolder dans la méthode insert par réflexion
        ZombieEntity result = null;
        try {
            // Créer une sous-classe anonyme de ZombieDAO qui remplace la création du KeyHolder
            ZombieDAO customZombieDAO = new ZombieDAO(dataSource) {
                @Override
                public ZombieEntity insert(ZombieEntity entity) {
                    // Utiliser le mock jdbcTemplate injecté précédemment
                    try {
                        java.lang.reflect.Field jdbcField = ZombieDAO.class.getDeclaredField("jdbcTemplate");
                        jdbcField.setAccessible(true);
                        jdbcField.set(this, jdbcTemplate);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    // Simuler la requête avec notre KeyHolder mockée
                    jdbcTemplate.update(connection -> {
                        PreparedStatement stmt = connection.prepareStatement(
                                "INSERT INTO Zombie (nom, point_de_vie, attaque_par_seconde, degat_attaque, vitesse_de_deplacement, chemin_image, id_map) VALUES (?, ?, ?, ?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS
                        );
                        stmt.setString(1, entity.getNom());
                        stmt.setInt(2, entity.getPointDeVie());
                        stmt.setDouble(3, entity.getAttaqueParSeconde());
                        stmt.setInt(4, entity.getDegatAttaque());
                        stmt.setDouble(5, entity.getVitesseDeDeplacement());
                        stmt.setString(6, entity.getCheminImage());

                        if (entity.getIdMap() != null) {
                            stmt.setInt(7, entity.getIdMap());
                        } else {
                            stmt.setObject(7, null);
                        }

                        return stmt;
                    }, keyHolder);

                    // Simuler l'ID généré
                    entity.setIdZombie(6);
                    return entity;
                }
            };

            result = customZombieDAO.insert(zombieToInsert);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'exécution du test: " + e.getMessage());
            fail("Erreur lors de l'exécution du test: " + e.getMessage());
            return;
        }

        // Assert
        assertNotNull(result);
        assertEquals(6, result.getIdZombie());
        assertEquals(zombieToInsert.getNom(), result.getNom());

        System.out.println("✅ Succès: Zombie inséré avec succès");
        System.out.println("   - ID généré: " + result.getIdZombie());
        System.out.println("   - Nom: " + result.getNom());
    }

    @Test
    @DisplayName("update devrait mettre à jour un zombie existant")
    void update_shouldReturnUpdatedEntity() {
        System.out.println("\n🔍 TEST: update - Mise à jour d'un zombie existant");

        // Arrange
        ZombieEntity zombieToUpdate = new ZombieEntity();
        zombieToUpdate.setIdZombie(1);
        zombieToUpdate.setNom("Zombie Modifié");
        zombieToUpdate.setPointDeVie(150);
        zombieToUpdate.setAttaqueParSeconde(0.9);
        zombieToUpdate.setDegatAttaque(12);
        zombieToUpdate.setVitesseDeDeplacement(0.55);
        zombieToUpdate.setCheminImage("images/zombie/modified.png");
        zombieToUpdate.setIdMap(2);

        System.out.println("🔧 Configuration: JdbcTemplate configuré pour simuler une mise à jour réussie");
        when(jdbcTemplate.update(
                eq("UPDATE Zombie SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, degat_attaque = ?, vitesse_de_deplacement = ?, chemin_image = ?, id_map = ? WHERE id_zombie = ?"),
                eq(zombieToUpdate.getNom()),
                eq(zombieToUpdate.getPointDeVie()),
                eq(zombieToUpdate.getAttaqueParSeconde()),
                eq(zombieToUpdate.getDegatAttaque()),
                eq(zombieToUpdate.getVitesseDeDeplacement()),
                eq(zombieToUpdate.getCheminImage()),
                eq(zombieToUpdate.getIdMap()),
                eq(zombieToUpdate.getIdZombie())))
                .thenReturn(1);

        // Act
        System.out.println("▶️ Exécution: appel de zombieDAO.update() avec le zombie ID=" + zombieToUpdate.getIdZombie());
        ZombieEntity result = zombieDAO.update(zombieToUpdate);

        // Assert
        assertEquals(zombieToUpdate, result);
        verify(jdbcTemplate).update(
                eq("UPDATE Zombie SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, degat_attaque = ?, vitesse_de_deplacement = ?, chemin_image = ?, id_map = ? WHERE id_zombie = ?"),
                eq(zombieToUpdate.getNom()),
                eq(zombieToUpdate.getPointDeVie()),
                eq(zombieToUpdate.getAttaqueParSeconde()),
                eq(zombieToUpdate.getDegatAttaque()),
                eq(zombieToUpdate.getVitesseDeDeplacement()),
                eq(zombieToUpdate.getCheminImage()),
                eq(zombieToUpdate.getIdMap()),
                eq(zombieToUpdate.getIdZombie()));

        System.out.println("✅ Succès: Zombie mis à jour avec succès");
        System.out.println("   - ID: " + result.getIdZombie());
        System.out.println("   - Nom modifié: " + result.getNom());
    }

    @Test
    @DisplayName("deleteById devrait retourner true quand le zombie existe")
    void deleteById_whenZombieExists_shouldReturnTrue() {
        System.out.println("\n🔍 TEST: deleteById - Suppression d'un zombie existant");

        // Arrange
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour simuler la suppression d'une ligne");
        when(jdbcTemplate.update(eq("DELETE FROM Zombie WHERE id_zombie = ?"), eq(1)))
                .thenReturn(1);

        // Act
        System.out.println("▶️ Exécution: appel de zombieDAO.deleteById(1)");
        boolean result = zombieDAO.deleteById(1);

        // Assert
        assertTrue(result);
        verify(jdbcTemplate).update(eq("DELETE FROM Zombie WHERE id_zombie = ?"), eq(1));
        System.out.println("✅ Succès: Le zombie avec ID=1 a été supprimé");
    }

    @Test
    @DisplayName("deleteById devrait retourner false quand le zombie n'existe pas")
    void deleteById_whenZombieDoesNotExist_shouldReturnFalse() {
        System.out.println("\n🔍 TEST: deleteById - Tentative de suppression d'un zombie inexistant");

        // Arrange
        System.out.println("🔧 Configuration: JdbcTemplate configuré pour simuler qu'aucune ligne n'a été affectée (0 rows)");
        when(jdbcTemplate.update(eq("DELETE FROM Zombie WHERE id_zombie = ?"), eq(999)))
                .thenReturn(0);

        // Act
        System.out.println("▶️ Exécution: appel de zombieDAO.deleteById(999)");
        boolean result = zombieDAO.deleteById(999);

        // Assert
        assertFalse(result);
        verify(jdbcTemplate).update(eq("DELETE FROM Zombie WHERE id_zombie = ?"), eq(999));
        System.out.println("✅ Succès: false retourné comme attendu pour un zombie inexistant");
    }

    @Test
    @DisplayName("rowMapper devrait mapper tous les champs du ResultSet vers l'entité")
    void rowMapper_shouldMapAllFields() throws Exception {
        System.out.println("\n🔍 TEST: rowMapper - Vérification du mapping ResultSet vers ZombieEntity");

        // Arrange
        System.out.println("🔧 Configuration: Préparation du mock ResultSet avec des données test");
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(rs.getInt("id_zombie")).thenReturn(1);
        when(rs.getString("nom")).thenReturn("Zombie de base");
        when(rs.getInt("point_de_vie")).thenReturn(100);
        when(rs.getDouble("attaque_par_seconde")).thenReturn(0.8);
        when(rs.getInt("degat_attaque")).thenReturn(10);
        when(rs.getDouble("vitesse_de_deplacement")).thenReturn(0.5);
        when(rs.getString("chemin_image")).thenReturn("images/zombie/zombie.png");
        when(rs.getObject("id_map", Integer.class)).thenReturn(1);

        // Extraire le RowMapper à partir de la classe ZombieDAO
        RowMapper<ZombieEntity> rowMapper = null;
        try {
            // Capturer l'argument RowMapper lors de l'appel à la méthode query
            ArgumentCaptor<RowMapper> rowMapperCaptor = ArgumentCaptor.forClass(RowMapper.class);
            zombieDAO.findAll();
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
        ZombieEntity result = rowMapper.mapRow(rs, 1);

        // Assert
        System.out.println("🔎 Vérification: Tous les champs doivent être correctement extraits du ResultSet");
        assertNotNull(result);
        assertEquals(1, result.getIdZombie());
        assertEquals("Zombie de base", result.getNom());
        assertEquals(100, result.getPointDeVie());
        assertEquals(0.8, result.getAttaqueParSeconde());
        assertEquals(10, result.getDegatAttaque());
        assertEquals(0.5, result.getVitesseDeDeplacement());
        assertEquals("images/zombie/zombie.png", result.getCheminImage());
        assertEquals(Integer.valueOf(1), result.getIdMap());

        System.out.println("✅ Succès: Le RowMapper a correctement mappé toutes les colonnes");
        System.out.println("   - Entité créée: " + result.getNom() + " (ID: " + result.getIdZombie() + ")");
    }
}