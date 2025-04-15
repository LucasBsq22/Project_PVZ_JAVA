package com.oxyl.coursepfback.api.mapper;

import com.oxyl.coursepfback.api.dto.MapDTO;
import com.oxyl.coursepfback.core.model.Map;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper pour convertir entre MapDTO et Map.
 */
public class MapDTOMapper {

    /**
     * Convertit un modèle Map en DTO MapDTO.
     */
    public static MapDTO toDTO(Map model) {
        if (model == null) {
            return null;
        }

        MapDTO dto = new MapDTO();
        dto.setId_map(model.getIdMap());
        dto.setLigne(model.getLigne());
        dto.setColonne(model.getColonne());
        dto.setChemin_image(model.getCheminImage());

        return dto;
    }

    /**
     * Convertit un DTO MapDTO en modèle Map.
     */
    public static Map toModel(MapDTO dto) {
        if (dto == null) {
            return null;
        }

        Map model = new Map();
        model.setIdMap(dto.getId_map());
        model.setLigne(dto.getLigne());
        model.setColonne(dto.getColonne());
        model.setCheminImage(dto.getChemin_image());

        return model;
    }

    /**
     * Convertit une liste de modèles Map en liste de DTOs MapDTO.
     */
    public static List<MapDTO> toDTOList(List<Map> models) {
        return models.stream()
                .map(MapDTOMapper::toDTO)
                .collect(Collectors.toList());
    }
}