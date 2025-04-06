package com.oxyl.coursepfback.api.mapper;

import com.oxyl.coursepfback.api.dto.PlantDTO;
import com.oxyl.coursepfback.core.model.Plant;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper pour convertir entre PlantDTO et Plant.
 */
public class PlantDTOMapper {

    /**
     * Convertit un modèle Plant en DTO PlantDTO.
     */
    public static PlantDTO toDTO(Plant model) {
        if (model == null) {
            return null;
        }

        PlantDTO dto = new PlantDTO();
        dto.setId_plante(model.getIdPlante());
        dto.setNom(model.getNom());
        dto.setPoint_de_vie(model.getPointDeVie());
        dto.setAttaque_par_seconde(model.getAttaqueParSeconde());
        dto.setDegat_attaque(model.getDegatAttaque());
        dto.setCout(model.getCout());
        dto.setSoleil_par_seconde(model.getSoleilParSeconde());
        dto.setEffet(model.getEffet());
        dto.setChemin_image(model.getCheminImage());

        return dto;
    }

    /**
     * Convertit un DTO PlantDTO en modèle Plant.
     */
    public static Plant toModel(PlantDTO dto) {
        if (dto == null) {
            return null;
        }

        Plant model = new Plant();
        model.setIdPlante(dto.getId_plante());
        model.setNom(dto.getNom());
        model.setPointDeVie(dto.getPoint_de_vie());
        model.setAttaqueParSeconde(dto.getAttaque_par_seconde());
        model.setDegatAttaque(dto.getDegat_attaque());
        model.setCout(dto.getCout());
        model.setSoleilParSeconde(dto.getSoleil_par_seconde());
        model.setEffet(dto.getEffet());
        model.setCheminImage(dto.getChemin_image());

        return model;
    }

    /**
     * Convertit une liste de modèles Plant en liste de DTOs PlantDTO.
     */
    public static List<PlantDTO> toDTOList(List<Plant> models) {
        return models.stream()
                .map(PlantDTOMapper::toDTO)
                .collect(Collectors.toList());
    }
}