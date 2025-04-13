package com.oxyl.coursepfback.persistance.mapper;

import com.oxyl.coursepfback.persistance.entity.ZombieEntity;
import com.oxyl.coursepfback.core.model.Zombie;

/**
 * Mapper pour convertir entre ZombieEntity et Zombie.
 */
public class ZombieEntityMapper {

    /**
     * Convertit une entité ZombieEntity en modèle Zombie.
     */
    public static Zombie toModel(ZombieEntity entity) {
        if (entity == null) {
            return null;
        }

        Zombie model = new Zombie();
        model.setIdZombie(entity.getIdZombie());
        model.setNom(entity.getNom());
        model.setPointDeVie(entity.getPointDeVie());
        model.setAttaqueParSeconde(entity.getAttaqueParSeconde());
        model.setDegatAttaque(entity.getDegatAttaque());
        model.setVitesseDeDeplacement(entity.getVitesseDeDeplacement());
        model.setCheminImage(entity.getCheminImage());
        model.setIdMap(entity.getIdMap());

        return model;
    }

    /**
     * Convertit un modèle Zombie en entité ZombieEntity.
     */
    public static ZombieEntity toEntity(Zombie model) {
        if (model == null) {
            return null;
        }

        ZombieEntity entity = new ZombieEntity();
        entity.setIdZombie(model.getIdZombie());
        entity.setNom(model.getNom());
        entity.setPointDeVie(model.getPointDeVie());
        entity.setAttaqueParSeconde(model.getAttaqueParSeconde());
        entity.setDegatAttaque(model.getDegatAttaque());
        entity.setVitesseDeDeplacement(model.getVitesseDeDeplacement());
        entity.setCheminImage(model.getCheminImage());
        entity.setIdMap(model.getIdMap());

        return entity;
    }
}