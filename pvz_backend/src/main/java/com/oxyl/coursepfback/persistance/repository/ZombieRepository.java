package com.oxyl.coursepfback.persistance.repository;

import com.oxyl.coursepfback.core.model.Zombie;
import com.oxyl.coursepfback.persistance.dao.ZombieDAO;
import com.oxyl.coursepfback.persistance.entity.ZombieEntity;
import com.oxyl.coursepfback.persistance.mapper.ZombieEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Fait le lien entre le domaine métier et la couche d'accès aux données
 */
@Repository
public class ZombieRepository {

    private final ZombieDAO zombieDAO;

    public ZombieRepository(ZombieDAO zombieDAO) {
        this.zombieDAO = zombieDAO;
    }

    /**
     * Récupère tous les zombies
     */
    public List<Zombie> findAll() {
        List<ZombieEntity> entities = zombieDAO.findAll();
        return entities.stream()
                .map(ZombieEntityMapper::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un zombie par son id
     */
    public Optional<Zombie> findById(int id) {
        Optional<ZombieEntity> entityOpt = zombieDAO.findById(id);
        return entityOpt.map(ZombieEntityMapper::toModel);
    }

    /**
     * Récupère les zombies par id de map
     */
    public List<Zombie> findByMapId(int mapId) {
        List<ZombieEntity> entities = zombieDAO.findByMapId(mapId);
        return entities.stream()
                .map(ZombieEntityMapper::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Sauvegarde un zombie (création ou mise à jour)
     */
    public Zombie save(Zombie zombie) {
        ZombieEntity entity = ZombieEntityMapper.toEntity(zombie);
        ZombieEntity savedEntity;

        if (zombie.getIdZombie() == null) {
            savedEntity = zombieDAO.insert(entity);
        } else {
            savedEntity = zombieDAO.update(entity);
        }

        return ZombieEntityMapper.toModel(savedEntity);
    }

    /**
     * Supprime un zombie par son id
     */
    public boolean deleteById(int id) {
        return zombieDAO.deleteById(id);
    }
}