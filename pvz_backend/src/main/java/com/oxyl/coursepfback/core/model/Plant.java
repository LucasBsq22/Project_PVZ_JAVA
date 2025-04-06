package com.oxyl.coursepfback.core.model;

public class Plant {
    private Integer idPlante; // Changé de idPlant à idPlante
    private String nom;
    private Integer pointDeVie;
    private Double attaqueParSeconde;
    private Integer degatAttaque;
    private Integer cout;
    private Double soleilParSeconde;
    private String effet;
    private String cheminImage;

    // Constructeurs
    public Plant() {}

    public Plant(Integer idPlante, String nom, Integer pointDeVie, Double attaqueParSeconde,
                 Integer degatAttaque, Integer cout, Double soleilParSeconde, String effet,
                 String cheminImage) {
        this.idPlante = idPlante; // Modifié
        this.nom = nom;
        this.pointDeVie = pointDeVie;
        this.attaqueParSeconde = attaqueParSeconde;
        this.degatAttaque = degatAttaque;
        this.cout = cout;
        this.soleilParSeconde = soleilParSeconde;
        this.effet = effet;
        this.cheminImage = cheminImage;
    }

    // Getters et Setters modifiés
    public Integer getIdPlante() { // Changé de getIdPlant à getIdPlante
        return idPlante;
    }

    public void setIdPlante(Integer idPlante) { // Changé de setIdPlant à setIdPlante
        this.idPlante = idPlante;
    }

    // Le reste des getters et setters reste inchangé
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getPointDeVie() {
        return pointDeVie;
    }

    public void setPointDeVie(Integer pointDeVie) {
        this.pointDeVie = pointDeVie;
    }

    public Double getAttaqueParSeconde() {
        return attaqueParSeconde;
    }

    public void setAttaqueParSeconde(Double attaqueParSeconde) {
        this.attaqueParSeconde = attaqueParSeconde;
    }

    public Integer getDegatAttaque() {
        return degatAttaque;
    }

    public void setDegatAttaque(Integer degatAttaque) {
        this.degatAttaque = degatAttaque;
    }

    public Integer getCout() {
        return cout;
    }

    public void setCout(Integer cout) {
        this.cout = cout;
    }

    public Double getSoleilParSeconde() {
        return soleilParSeconde;
    }

    public void setSoleilParSeconde(Double soleilParSeconde) {
        this.soleilParSeconde = soleilParSeconde;
    }

    public String getEffet() {
        return effet;
    }

    public void setEffet(String effet) {
        this.effet = effet;
    }

    public String getCheminImage() {
        return cheminImage;
    }

    public void setCheminImage(String cheminImage) {
        this.cheminImage = cheminImage;
    }
}

