package com.oxyl.coursepfback.api.dto;

import com.oxyl.coursepfback.core.model.Plant;

/**
 * DTO pour les plantes, utilisé pour la communication avec le frontend.
 */
public class PlantDTO {
    private Integer id_plante;
    private String nom;
    private Integer point_de_vie;
    private Double attaque_par_seconde;
    private Integer degat_attaque;
    private Integer cout;
    private Double soleil_par_seconde;
    private String effet;
    private String chemin_image;

    public PlantDTO() {
    }

    public PlantDTO(Plant plant) {
        this.id_plante = plant.getIdPlante();
        this.nom = plant.getNom();
        this.point_de_vie = plant.getPointDeVie();
        this.attaque_par_seconde = plant.getAttaqueParSeconde();
        this.degat_attaque = plant.getDegatAttaque();
        this.cout = plant.getCout();
        this.soleil_par_seconde = plant.getSoleilParSeconde();
        this.effet = plant.getEffet();
        this.chemin_image = plant.getCheminImage();
    }

    // Conversion de DTO vers Modèle
    public Plant toPlant() {
        Plant plant = new Plant();
        plant.setIdPlante(this.id_plante);
        plant.setNom(this.nom);
        plant.setPointDeVie(this.point_de_vie);
        plant.setAttaqueParSeconde(this.attaque_par_seconde);
        plant.setDegatAttaque(this.degat_attaque);
        plant.setCout(this.cout);
        plant.setSoleilParSeconde(this.soleil_par_seconde);
        plant.setEffet(this.effet);
        plant.setCheminImage(this.chemin_image);
        return plant;
    }

    // Getters et Setters
    public Integer getId_plante() {
        return id_plante;
    }

    public void setId_plante(Integer id_plante) {
        this.id_plante = id_plante;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getPoint_de_vie() {
        return point_de_vie;
    }

    public void setPoint_de_vie(Integer point_de_vie) {
        this.point_de_vie = point_de_vie;
    }

    public Double getAttaque_par_seconde() {
        return attaque_par_seconde;
    }

    public void setAttaque_par_seconde(Double attaque_par_seconde) {
        this.attaque_par_seconde = attaque_par_seconde;
    }

    public Integer getDegat_attaque() {
        return degat_attaque;
    }

    public void setDegat_attaque(Integer degat_attaque) {
        this.degat_attaque = degat_attaque;
    }

    public Integer getCout() {
        return cout;
    }

    public void setCout(Integer cout) {
        this.cout = cout;
    }

    public Double getSoleil_par_seconde() {
        return soleil_par_seconde;
    }

    public void setSoleil_par_seconde(Double soleil_par_seconde) {
        this.soleil_par_seconde = soleil_par_seconde;
    }

    public String getEffet() {
        return effet;
    }

    public void setEffet(String effet) {
        this.effet = effet;
    }

    public String getChemin_image() {
        return chemin_image;
    }

    public void setChemin_image(String chemin_image) {
        this.chemin_image = chemin_image;
    }
}