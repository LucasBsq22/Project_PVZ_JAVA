package com.oxyl.coursepfback.core.model;

public class Map {
    private Integer idMap;
    private Integer ligne;
    private Integer colonne;
    private String cheminImage;

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