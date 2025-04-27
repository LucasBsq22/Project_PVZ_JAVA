# Plants vs Zombies - Backend

Ce projet est le backend d'un jeu de type Plants vs Zombies, développé en Java avec Spring MVC et une architecture en couches. Ce README documente l'implémentation du backend, son architecture et son fonctionnement.

## Structure du projet

Voici l'arborescence des fichiers du backend :

```
pvz_backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── oxyl/
│   │   │           └── coursepfback/
│   │   │               ├── api/
│   │   │               │   ├── controller/
│   │   │               │   │   ├── MapController.java
│   │   │               │   │   ├── PlantController.java
│   │   │               │   │   ├── TestController.java
│   │   │               │   │   └── ZombieController.java
│   │   │               │   ├── dto/
│   │   │               │   │   ├── MapDTO.java
│   │   │               │   │   ├── PlantDTO.java
│   │   │               │   │   └── ZombieDTO.java
│   │   │               │   └── mapper/
│   │   │               │       ├── MapDTOMapper.java
│   │   │               │       ├── PlantDTOMapper.java
│   │   │               │       └── ZombieDTOMapper.java
│   │   │               ├── config/
│   │   │               │   ├── DatabaseConfig.java
│   │   │               │   ├── WebAppInitializer.java
│   │   │               │   └── WebConfig.java
│   │   │               ├── core/
│   │   │               │   ├── model/
│   │   │               │   │   ├── Map.java
│   │   │               │   │   ├── Plant.java
│   │   │               │   │   └── Zombie.java
│   │   │               │   └── service/
│   │   │               │       ├── MapService.java
│   │   │               │       ├── PlantService.java
│   │   │               │       └── ZombieService.java
│   │   │               └── persistance/
│   │   │                   ├── dao/
│   │   │                   │   ├── MapDAO.java
│   │   │                   │   ├── PlantDAO.java
│   │   │                   │   └── ZombieDAO.java
│   │   │                   ├── entity/
│   │   │                   │   ├── MapEntity.java
│   │   │                   │   ├── PlantEntity.java
│   │   │                   │   └── ZombieEntity.java
│   │   │                   ├── mapper/
│   │   │                   │   ├── MapEntityMapper.java
│   │   │                   │   ├── PlantEntityMapper.java
│   │   │                   │   └── ZombieEntityMapper.java
│   │   │                   └── repository/
│   │   │                       ├── MapRepository.java
│   │   │                       ├── PlantRepository.java
│   │   │                       └── ZombieRepository.java
│   │   └── resources/
│   │       ├── database.properties
│   │       ├── init.sql
│   │       ├── logback.xml
│   │       └── values.sql
│   └── test/
│       └── java/
│           └── com/
│               └── oxyl/
│                   └── coursepfback/
│                       ├── api/
│                       │   ├── controller/
│                       │   │   ├── MapControllerTest.java
│                       │   │   ├── PlantControllerTest.java
│                       │   │   └── ZombieControllerTest.java
│                       │   └── mapper/
│                       │       ├── MapDTOMapperTest.java
│                       │       ├── PlantDTOMapperTest.java
│                       │       └── ZombieDTOMapperTest.java
│                       ├── core/
│                       │   └── service/
│                       │       ├── MapServiceTest.java
│                       │       ├── PlantServiceTest.java
│                       │       └── ZombieServiceTest.java
│                       └── persistance/
│                           ├── dao/
│                           │   ├── MapDAOTest.java
│                           │   ├── PlantDAOTest.java
│                           │   └── ZombieDAOTest.java
│                           ├── mapper/
│                           │   ├── MapEntityMapperTest.java
│                           │   ├── PlantEntityMapperTest.java
│                           │   └── ZombieEntityMapperTest.java
│                           └── repository/
│                               ├── MapRepositoryTest.java
│                               ├── PlantRepositoryTest.java
│                               └── ZombieRepositoryTest.java
└── webapp/
    └── images/
        ├── map/
        │   └── gazon.png
        ├── plante/
        │   ├── doublepois.png
        │   ├── glacepois.png
        │   ├── noix.png
        │   ├── poistireur.png
        │   └── tournesol.png
        └── zombie/
            ├── buckethead.png
            ├── conehead.png
            ├── football.png
            ├── runner.png
            └── zombie.png
```

## Architecture

Comme demandé dans les consignes le backend est organisé selon une architecture en couches :

### 1. Couche Persistance
Gère l'accès aux données et les interactions avec la base de données MySQL
- **DAO** : Implémente les requêtes SQL via JdbcTemplate
- **Entités** : Représente les objets de la base de données
- **Repositories** : Fait le lien entre les entités et les modèles
- **Mappers** : Convertit les entités en modèles et inversement
  
**Pour maintenir la cohérence des données** une map ne peut pas être supprimée si des zombies y sont associés. Le système vérifie cette contrainte via la méthode `isReferencedByZombies()` du `MapRepository` avant toute suppression.

### 2. Couche Core
Contient la logique métier de l'application
- **Modèles** : Définit les objets du domaine métier
- **Services** : Implémente la logique métier et utilise les repositories

### 3. Couche API
Gère les points d'entrée REST de l'application
- **Controllers** : Expose les endpoints REST
- **DTOs** : Définit les structures de données échangées avec le frontend
- **Mappers** : Convertit les modèles en DTOs et inversement

### 4. Tests
La structure des tests suit la même organisation que le code principal, avec des tests pour chaque couche et composant qui le nécessite :
- Tests des Controllers
- Tests des Mappers (DTO et Entity)
- Tests des Services
- Tests des DAO
- Tests des Repositories

## Entités

Le jeu gère trois entités principales :

### 1. Plante
Représente les plantes que le joueur peut placer
- Points de vie
- Attaque par seconde
- Dégâts d'attaque
- Coût (en soleil)
- Production de soleil par seconde
- Effet (normal, ralentissement, etc.)

### 2. Zombie
Représente les ennemis qui attaquent
- Points de vie
- Attaque par seconde
- Dégâts d'attaque
- Vitesse de déplacement
- Appartenance à une map (facultatif)

### 3. Map
Représente le terrain de jeu
- Nombre de lignes
- Nombre de colonnes
- Image de fond

## Implémentation des endpoints REST

Le backend expose les APIs suivantes :

### Plantes
- `GET /plantes` - Liste toutes les plantes
- `GET /plantes/{id}` - Récupère une plante par son ID
- `POST /plantes` - Crée une nouvelle plante
- `PUT /plantes/{id}` - Modifie une plante existante
- `DELETE /plantes/{id}` - Supprime une plante

### Zombies
- `GET /zombies` - Liste tous les zombies
- `GET /zombies/{id}` - Récupère un zombie par son ID
- `GET /zombies/map/{mapId}` - Récupère les zombies par ID de map (non utilisé par le frontend actuellement mais pourrait être utile pour une prochaine mise à jour)
- `POST /zombies` - Crée un nouveau zombie
- `PUT /zombies/{id}` - Modifie un zombie existant
- `DELETE /zombies/{id}` - Supprime un zombie

### Maps
- `GET /maps` - Liste toutes les maps
- `GET /maps/{id}` - Récupère une map par son ID
- `POST /maps` - Crée une nouvelle map
- `PUT /maps/{id}` - Modifie une map existante
- `DELETE /maps/{id}` - Supprime une map (échoue avec un code HTTP 409 CONFLICT si des zombies sont associés à cette map)

## Configuration

### Base de données
La configuration de la base de données est définie dans `DatabaseConfig.java`. Elle utilise MySQL avec les paramètres suivants :
- URL : `jdbc:mysql://localhost:3306/pvz`
- Driver : `com.mysql.cj.jdbc.Driver`
- Utilisateur & mot de passe configurés

### Web et CORS
La configuration web est définie dans `WebConfig.java`. Elle inclut :
- La configuration CORS pour permettre les requêtes depuis le frontend (`http://localhost:5173`)
- La configuration des gestionnaires de ressources pour les images

## Validation des données

Chaque service implémente des méthodes de validation pour s'assurer que les données respectent les contraintes du domaine :
- `PlantService.validatePlantFormat()`
- `ZombieService.validateZombieFormat()`
- `MapService.validateMapFormat()`

## Gestion des images

Les images sont stockées dans le répertoire `webapp/images/` avec la structure correspondant aux différents types d'entités (plante, zombie, map). Le chemin des images est stocké dans la base de données et est renvoyé au frontend via les DTOs.

## Script de redéploiement (redeploy.sh)

La création du script `redeploy.sh` m'a permis d'automatiser le processus de redéploiement de l'application. Il effectue les étapes suivantes :

```bash
#!/bin/bash

# Arrêt de Tomcat
$CATALINA_HOME/bin/shutdown.sh

# Compilation du projet avec Maven
mvn clean install

# Suppression de l'ancienne application
rm -f $CATALINA_HOME/webapps/CoursEpfBack.war
rm -rf $CATALINA_HOME/webapps/CoursEpfBack

# Copie du nouveau WAR
cp target/CoursEpfBack.war $CATALINA_HOME/webapps/

# Démarrage de Tomcat
$CATALINA_HOME/bin/startup.sh

echo "Redéploiement terminé!"
```

## Sécurité

- Les requêtes SQL utilisent des requêtes préparées pour éviter les injections SQL
- La configuration CORS est restreinte à l'origine du frontend uniquement

# Lucas Bosq MIN1
