package com.oxyl.coursepfback.persistance.mapper;

import com.oxyl.coursepfback.persistance.entity.PlantEntity;
import com.oxyl.coursepfback.core.model.Plant;

/**
 * Mapper pour convertir entre PlantEntity et Plant.
 */
public class PlantEntityMapper {

    /**
     * Convertit une entité PlantEntity en modèle Plant.
     */
    public static Plant toModel(PlantEntity entity) {
        if (entity == null) {
            return null;
        }

        Plant model = new Plant();
        model.setIdPlante(entity.getIdPlante());
        model.setNom(entity.getNom());
        model.setPointDeVie(entity.getPointDeVie());
        model.setAttaqueParSeconde(entity.getAttaqueParSeconde());
        model.setDegatAttaque(entity.getDegatAttaque());
        model.setCout(entity.getCout());
        model.setSoleilParSeconde(entity.getSoleilParSeconde());
        model.setEffet(entity.getEffet());
        model.setCheminImage(entity.getCheminImage());

        return model;
    }

    /**
     * Convertit un modèle Plant en entité PlantEntity.
     */
    public static PlantEntity toEntity(Plant model) {
        if (model == null) {
            return null;
        }

        PlantEntity entity = new PlantEntity();
        entity.setIdPlante(model.getIdPlante());
        entity.setNom(model.getNom());
        entity.setPointDeVie(model.getPointDeVie());
        entity.setAttaqueParSeconde(model.getAttaqueParSeconde());
        entity.setDegatAttaque(model.getDegatAttaque());
        entity.setCout(model.getCout());
        entity.setSoleilParSeconde(model.getSoleilParSeconde());
        entity.setEffet(model.getEffet());
        entity.setCheminImage(model.getCheminImage());

        return entity;
    }
}