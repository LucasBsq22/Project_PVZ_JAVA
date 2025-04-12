// dans com.oxyl.coursepfback.api.mapper.ZombieDTOMapper.java
package com.oxyl.coursepfback.api.mapper;

import com.oxyl.coursepfback.api.dto.ZombieDTO;
import com.oxyl.coursepfback.core.model.Zombie;

public class ZombieDTOMapper {

    public static ZombieDTO toDTO(Zombie zombie) {
        if (zombie == null) {
            return null;
        }

        ZombieDTO dto = new ZombieDTO();
        dto.setId_zombie(zombie.getIdZombie());
        dto.setNom(zombie.getNom());
        dto.setPoint_de_vie(zombie.getPointDeVie());
        dto.setAttaque_par_seconde(zombie.getAttaqueParSeconde());
        dto.setDegat_attaque(zombie.getDegatAttaque());
        dto.setVitesse_de_deplacement(zombie.getVitesseDeDeplacement());
        dto.setChemin_image(zombie.getCheminImage());
        dto.setId_map(zombie.getIdMap());

        return dto;
    }

    public static Zombie toModel(ZombieDTO dto) {
        if (dto == null) {
            return null;
        }

        Zombie zombie = new Zombie();
        zombie.setIdZombie(dto.getId_zombie());
        zombie.setNom(dto.getNom());
        zombie.setPointDeVie(dto.getPoint_de_vie());
        zombie.setAttaqueParSeconde(dto.getAttaque_par_seconde());
        zombie.setDegatAttaque(dto.getDegat_attaque());
        zombie.setVitesseDeDeplacement(dto.getVitesse_de_deplacement());
        zombie.setCheminImage(dto.getChemin_image());
        zombie.setIdMap(dto.getId_map());

        return zombie;
    }
}