package com.oxyl.coursepfback.persistance.mapper;

import com.oxyl.coursepfback.persistance.entity.MapEntity;
import com.oxyl.coursepfback.core.model.Map;

/**
 * Mapper pour convertir entre MapEntity et Map
 */
public class MapEntityMapper {

    /**
     * Convertit une entité MapEntity en modèle Map
     */
    public static Map toModel(MapEntity entity) {
        if (entity == null) {
            return null;
        }

        Map model = new Map();
        model.setIdMap(entity.getIdMap());
        model.setLigne(entity.getLigne());
        model.setColonne(entity.getColonne());
        model.setCheminImage(entity.getCheminImage());

        return model;
    }

    /**
     * Convertit un modèle Map en entité MapEntity
     */
    public static MapEntity toEntity(Map model) {
        if (model == null) {
            return null;
        }

        MapEntity entity = new MapEntity();
        entity.setIdMap(model.getIdMap());
        entity.setLigne(model.getLigne());
        entity.setColonne(model.getColonne());
        entity.setCheminImage(model.getCheminImage());

        return entity;
    }
}