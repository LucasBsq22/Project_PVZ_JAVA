# Plants vs Zombies - V1

Ce projet est une application front-end React pour un jeu de type Plants vs Zombies. Il permet de tester les endpoints d'une API backend, gérer les entités du jeu (plantes, zombies, maps) et jouer au jeu.

## Prérequis

- Node.js v16+
- npm ou yarn
- Backend Java SpringBoot fonctionnel sur `http://localhost:8080/CoursEpfBack`

## Installation

```bash
# Se placer dans un terminal dans le dossier du fichier zip décompressé et lancer les commandes suivantes :

# Installer les dépendances
npm install
# ou
yarn install
```

## Démarrage

```bash
# Démarrer l'application en mode développement
npm run dev
# ou
yarn dev
```

L'application sera accessible à l'adresse `http://localhost:5173`.

## Structure et fonctionnement de l'application

L'application comporte trois sections principales :

1. **Endpoints** : Page de test des API
2. **Editeur** : Interface pour créer/modifier les entités du jeu
3. **Game** : Le jeu lui-même

## ⚠️ IMPORTANT : Ordre de validation des endpoints ⚠️

Avant d'utiliser l'éditeur ou le jeu, vous **DEVEZ** d'abord valider tous les endpoints dans cet ordre :

1. Validez d'abord les endpoints de validation du format des données :
   - `/plantes/validation`
   - `/zombies/validation`
   - `/maps/validation`

2. Une fois ces validations réussies, testez les autres endpoints :
   - Endpoints GET pour récupérer les données
   - Endpoints POST pour créer les entités
   - Endpoints PUT pour modifier les entités
   - Endpoints DELETE pour supprimer les entités

Si vous ne validez pas d'abord les formats de données, l'application risque de ne pas fonctionner correctement.

## Backend DTO attendus

L'application attend les structures de données suivantes du backend :

### Plante
```json
{
  "id_plante": number,
  "nom": string,
  "point_de_vie": number,
  "attaque_par_seconde": number,
  "degat_attaque": number,
  "cout": number,
  "soleil_par_seconde": number,
  "effet": string,
  "chemin_image": string
}
```

### Zombie
```json
{
  "id_zombie": number,
  "nom": string,
  "point_de_vie": number,
  "attaque_par_seconde": number,
  "degat_attaque": number,
  "vitesse_de_deplacement": number,
  "chemin_image": string,
  "id_map": number | null
}
```

### Map
```json
{
  "id_map": number,
  "ligne": number,
  "colonne": number,
  "chemin_image": string
}
```

## Endpoints attendus

L'application communique avec les endpoints suivants :

### Plantes
- `GET /plantes` - Liste toutes les plantes
- `GET /plantes/{id}` - Récupère une plante par son ID
- `POST /plantes` - Crée une nouvelle plante
- `PUT /plantes/{id}` - Modifie une plante existante
- `DELETE /plantes/{id}` - Supprime une plante

### Zombies
- `GET /zombies` - Liste tous les zombies
- `GET /zombies/{id}` - Récupère un zombie par son ID
- `POST /zombies` - Crée un nouveau zombie
- `PUT /zombies/{id}` - Modifie un zombie existant
- `DELETE /zombies/{id}` - Supprime un zombie

### Maps
- `GET /maps` - Liste toutes les maps
- `GET /maps/{id}` - Récupère une map par son ID
- `POST /maps` - Crée une nouvelle map
- `PUT /maps/{id}` - Modifie une map existante
- `DELETE /maps/{id}` - Supprime une map

## Configuration du backend

### Configuration CORS

Pour que le frontend puisse communiquer avec le backend, vous devez ajouter la configuration CORS suivante dans votre application SpringBoot :

```java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("http://localhost:5173")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
}

@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/images/**")
            .addResourceLocations("/images/");
}
```

### Gestion des images

Pour que les images soient accessibles, vous devez créer un dossier `images` dans le répertoire `webapp` de votre projet backend avec la structure suivante :

```
webapp/
└── images/
    ├── plante/
    │   ├── tournesol.png
    │   ├── poistireur.png
    │   └── ...
    ├── zombie/
    │   ├── zombie.png
    │   ├── conehead.png
    │   └── ...
    └── map/
        └── gazon.png
```

## Réinitialisation de la base de données

Après avoir testé les endpoints, il est recommandé de réinitialiser la base de données avec le script SQL suivant :

```sql
-- Sélectionner la base de données
USE pvz;

-- Supprimer les tables si elles existent déjà (dans l'ordre pour respecter les contraintes)
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
    ('Zombie de base', 100, 0.80, 10, 0.50, 'images/zombie/zombie.png', 1),
    ('Zombie Cone', 200, 0.80, 10, 0.45, 'images/zombie/conehead.png', 1),
    ('Zombie Seau', 300, 0.70, 10, 0.40, 'images/zombie/buckethead.png', 1),
    ('Runner Zombie', 80, 1.00, 8, 0.70, 'images/zombie/runner.png', 2),
    ('Football Zombie', 250, 0.90, 12, 0.60, 'images/zombie/football.png', 3);

-- Création de l'utilisateur s'il n'existe pas déjà
CREATE USER IF NOT EXISTS 'userEPF'@'%' IDENTIFIED BY 'EPF';

-- Attribution de tous les privilèges à l'utilisateur sur la base de données pvz
GRANT ALL PRIVILEGES ON pvz.* TO 'userEPF'@'%';

-- Application des changements de privilèges
FLUSH PRIVILEGES;
```

## Dépannage

Si vous rencontrez des problèmes avec l'affichage des images :
1. Vérifiez que le dossier `images` est correctement placé dans `webapp`
2. Vérifiez que la configuration CORS est correctement implémentée
3. Assurez-vous que les chemins d'accès aux images dans la base de données correspondent à la structure du dossier `images`

## Workflow recommandé

1. Démarrer le backend
2. Démarrer le frontend
3. Aller sur la page "Endpoints" et tester tous les endpoints de validation
4. Tester les autres endpoints
5. Une fois tous les tests réussis, vous pouvez utiliser l'éditeur et jouer au jeu

## Problème de configuration ? Contactez-nous :
Si vous rencontrez des difficultés avec la configuration du front ou autre, n'hésitez pas à nous contacter :

- Wassim Bayoub (wbayoub@oxyl.fr)
- Sébastien Latronche (slatronche@oxyl.fr)
- Anatole Durre (adurre@oxyl.fr)
- Arthur Pairaud (apairaud@oxyl.fr)