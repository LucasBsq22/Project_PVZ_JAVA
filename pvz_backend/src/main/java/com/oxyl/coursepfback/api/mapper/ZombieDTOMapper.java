package com.oxyl.coursepfback.api.mapper;

import com.oxyl.coursepfback.api.dto.ZombieDTO;
import com.oxyl.coursepfback.core.model.Zombie;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper pour convertir entre ZombieDTO et Zombie.
 */
public class ZombieDTOMapper {

    /**
     * Convertit un modèle Zombie en DTO ZombieDTO.
     */
    public static ZombieDTO toDTO(Zombie model) {
        if (model == null) {
            return null;
        }

        ZombieDTO dto = new ZombieDTO();
        dto.setId_zombie(model.getIdZombie());
        dto.setNom(model.getNom());
        dto.setPoint_de_vie(model.getPointDeVie());
        dto.setAttaque_par_seconde(model.getAttaqueParSeconde());
        dto.setDegat_attaque(model.getDegatAttaque());
        dto.setVitesse_de_deplacement(model.getVitesseDeDeplacement());
        dto.setChemin_image(model.getCheminImage());
        dto.setId_map(model.getIdMap());

        return dto;
    }

    /**
     * Convertit un DTO ZombieDTO en modèle Zombie.
     */
    public static Zombie toModel(ZombieDTO dto) {
        if (dto == null) {
            return null;
        }

        Zombie model = new Zombie();
        model.setIdZombie(dto.getId_zombie());
        model.setNom(dto.getNom());
        model.setPointDeVie(dto.getPoint_de_vie());
        model.setAttaqueParSeconde(dto.getAttaque_par_seconde());
        model.setDegatAttaque(dto.getDegat_attaque());
        model.setVitesseDeDeplacement(dto.getVitesse_de_deplacement());
        model.setCheminImage(dto.getChemin_image());
        model.setIdMap(dto.getId_map());

        return model;
    }

    /**
     * Convertit une liste de modèles Zombie en liste de DTOs ZombieDTO.
     */
    public static List<ZombieDTO> toDTOList(List<Zombie> models) {
        return models.stream()
                .map(ZombieDTOMapper::toDTO)
                .collect(Collectors.toList());
    }
}