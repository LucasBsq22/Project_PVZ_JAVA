-- Sélectionner la base de données (assurez-vous qu'elle existe)
USE pvz;

-- Supprimer les tables si elles existent déjà
DROP TABLE IF EXISTS Zombie;
DROP TABLE IF EXISTS Plante;
DROP TABLE IF EXISTS Map;

-- Créer la table "map"
CREATE TABLE Map (
    id_map INT AUTO_INCREMENT PRIMARY KEY,
    ligne INT UNSIGNED NOT NULL,
    colonne INT UNSIGNED NOT NULL,
    chemin_image VARCHAR(255) DEFAULT NULL
);

-- Créer la table "plante"
CREATE TABLE Plante (
    id_plante INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    point_de_vie INT UNSIGNED NOT NULL,
    attaque_par_seconde DECIMAL(5, 2) DEFAULT 0.00,
    degat_attaque INT UNSIGNED DEFAULT 0,
    cout INT UNSIGNED NOT NULL,
    soleil_par_seconde DECIMAL(5, 2) DEFAULT 0.00,
    effet ENUM('normal', 'slow low', 'slow medium', 'slow stop') DEFAULT 'normal',
    chemin_image VARCHAR(255) DEFAULT NULL
);

-- Créer la table "zombie"
CREATE TABLE Zombie (
    id_zombie INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    point_de_vie INT UNSIGNED NOT NULL,
    attaque_par_seconde DECIMAL(5, 2) DEFAULT 0.00,
    degat_attaque INT UNSIGNED NOT NULL,
    vitesse_de_deplacement DECIMAL(5, 2) DEFAULT 0.00,
    chemin_image VARCHAR(255) DEFAULT NULL,
    id_map INT,
    CONSTRAINT fk_zombie_map FOREIGN KEY (id_map) REFERENCES Map(id_map)
);
