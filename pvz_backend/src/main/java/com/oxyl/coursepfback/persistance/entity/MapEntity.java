package com.oxyl.coursepfback.persistance.entity;

/**
 * Entité qui représente une ligne de la table Map dans la base de données.
 */
public class MapEntity {
    private Integer idMap;
    private Integer ligne;
    private Integer colonne;
    private String cheminImage;

    // Constructeur par défaut
    public MapEntity() {
    }

    // Getters et Setters
    public Integer getIdMap() {
        return idMap;
    }

    public void setIdMap(Integer idMap) {
        this.idMap = idMap;
    }

    public Integer getLigne() {
        return ligne;
    }

    public void setLigne(Integer ligne) {
        this.ligne = ligne;
    }

    public Integer getColonne() {
        return colonne;
    }

    public void setColonne(Integer colonne) {
        this.colonne = colonne;
    }

    public String getCheminImage() {
        return cheminImage;
    }

    public void setCheminImage(String cheminImage) {
        this.cheminImage = cheminImage;
    }
}