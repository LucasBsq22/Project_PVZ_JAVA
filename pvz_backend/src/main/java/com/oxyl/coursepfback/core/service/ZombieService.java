package com.oxyl.coursepfback.core.service;

import com.oxyl.coursepfback.core.model.Zombie;
import com.oxyl.coursepfback.persistance.repository.ZombieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des zombies
 */
@Service
public class ZombieService {

    private final ZombieRepository zombieRepository;

    public ZombieService(ZombieRepository zombieRepository) {
        this.zombieRepository = zombieRepository;
    }

    /**
     * Récupère tous les zombies
     */
    public List<Zombie> getAllZombies() {
        return zombieRepository.findAll();
    }

    /**
     * Récupère un zombie par son id
     */
    public Optional<Zombie> getZombieById(int id) {
        return zombieRepository.findById(id);
    }

    /**
     * Récupère les zombies par id de map.
     */
    public List<Zombie> getZombiesByMapId(int mapId) {
        return zombieRepository.findByMapId(mapId);
    }

    /**
     * Sauvegarde un zombie (création ou mise à jour)
     */
    public Zombie saveZombie(Zombie zombie) {
        return zombieRepository.save(zombie);
    }

    /**
     * Supprime un zombie par son id
     */
    public boolean deleteZombie(int id) {
        return zombieRepository.deleteById(id);
    }

    /**
     * Valide le format d'un zombie
     */
    public boolean validateZombieFormat(Zombie zombie) {
        return zombie != null
                && zombie.getNom() != null && !zombie.getNom().isEmpty()
                && zombie.getPointDeVie() != null && zombie.getPointDeVie() > 0
                && zombie.getAttaqueParSeconde() != null && zombie.getAttaqueParSeconde() >= 0
                && zombie.getDegatAttaque() != null && zombie.getDegatAttaque() >= 0
                && zombie.getVitesseDeDeplacement() != null && zombie.getVitesseDeDeplacement() > 0
                && zombie.getCheminImage() != null && !zombie.getCheminImage().isEmpty();
                //idMap peut être null, donc pas de validation
    }
}