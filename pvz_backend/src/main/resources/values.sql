-- Sélectionner la base de données (assurez-vous qu'elle existe)
USE pvz;

-- Peuplement de la table "map"
INSERT INTO Map (ligne, colonne, chemin_image) VALUES
    (5, 9, 'images/map/gazon.png'),
    (6, 9, 'images/map/gazon.png'),
    (4, 8, 'images/map/gazon.png');

-- Peuplement de la table "plante"
INSERT INTO Plante (nom, point_de_vie, attaque_par_seconde, degat_attaque, cout, soleil_par_seconde, effet, chemin_image) VALUES
    ('Tournesol', 100, 0.00, 0, 50, 25.00, 'normal', 'images/plante/tournesol.png'),
    ('Pois Tireur', 150, 1.50, 20, 100, 0.00, 'normal', 'images/plante/poistireur.png'),
    ('Double Pisto P', 150, 1.50, 40, 200, 0.00, 'normal', 'images/plante/doublepois.png'),
    ('Glace Pois', 120, 1.00, 10, 175, 0.00, 'slow low', 'images/plante/glacepois.png'),
    ('Noix', 300, 0.00, 0, 50, 0.00, 'normal', 'images/plante/noix.png');

-- Peuplement de la table "zombie"
INSERT INTO Zombie (nom, point_de_vie, attaque_par_seconde, degat_attaque, vitesse_de_deplacement, chemin_image, id_map) VALUES
    ('Zombie de base', 100, 0.80, 10, 0.50, 'images/zombie/zombie.png', 1), -- Commence sur la ligne 4 (chemin)
    ('Zombie Cone', 200, 0.80, 10, 0.45, 'images/zombie/conehead.png', 1),
    ('Zombie Seau', 300, 0.70, 10, 0.40, 'images/zombie/buckethead.png', 1),
    ('Runner Zombie', 80, 1.00, 8, 0.70, 'images/zombie/runner.png', 2),
    ('Football Zombie', 250, 0.90, 12, 0.60, 'images/zombie/football.png', 3);