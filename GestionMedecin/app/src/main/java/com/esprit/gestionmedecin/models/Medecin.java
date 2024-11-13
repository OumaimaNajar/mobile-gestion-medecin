package com.esprit.gestionmedecin.models;

public class Medecin {
    private int id ;
    private String nom;
    private String specialite;
    private String numero;

    public Medecin(String nom, String specialite, String numero) {
        this.nom = nom;
        this.specialite = specialite;
        this.numero = numero;
    }

    public Medecin(String nom) {
        this.nom = nom;
    }

    public Medecin(int id, String nom, String specialite, String numero) {
        this.id = id ;
        this.nom = nom ;
        this.specialite = specialite ;
        this.numero = numero ;

    }

    public String getNom() {
        return nom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public String getTelephone() {
        return numero;
    }


    public String getNum() {
        return numero ;
    }
}
