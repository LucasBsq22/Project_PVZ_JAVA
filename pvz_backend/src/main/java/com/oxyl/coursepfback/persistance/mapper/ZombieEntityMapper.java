// dans com.oxyl.coursepfback.persistance.mapper.ZombieEntityMapper.java
package com.oxyl.coursepfback.persistance.mapper;

import com.oxyl.coursepfback.core.model.Zombie;
import com.oxyl.coursepfback.persistance.entity.ZombieEntity;

public class ZombieEntityMapper {

    public static ZombieEntity toEntity(Zombie zombie) {
        if (zombie == null) {
            return null;
        }

        ZombieEntity entity = new ZombieEntity();
        entity.setIdZombie(zombie.getIdZombie());
        entity.setNom(zombie.getNom());
        entity.setPointDeVie(zombie.getPointDeVie());
        entity.setAttaqueParSeconde(zombie.getAttaqueParSeconde());
        entity.setDegatAttaque(zombie.getDegatAttaque());
        entity.setVitesseDeDeplacement(zombie.getVitesseDeDeplacement());
        entity.setCheminImage(zombie.getCheminImage());
        entity.setIdMap(zombie.getIdMap());

        return entity;
    }

    public static Zombie toModel(ZombieEntity entity) {
        if (entity == null) {
            return null;
        }

        Zombie zombie = new Zombie();
        zombie.setIdZombie(entity.getIdZombie());
        zombie.setNom(entity.getNom());
        zombie.setPointDeVie(entity.getPointDeVie());
        zombie.setAttaqueParSeconde(entity.getAttaqueParSeconde());
        zombie.setDegatAttaque(entity.getDegatAttaque());
        zombie.setVitesseDeDeplacement(entity.getVitesseDeDeplacement());
        zombie.setCheminImage(entity.getCheminImage());
        zombie.setIdMap(entity.getIdMap());

        return zombie;
    }
}