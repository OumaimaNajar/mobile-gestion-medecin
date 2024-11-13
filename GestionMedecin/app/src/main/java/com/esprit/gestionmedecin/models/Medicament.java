package com.esprit.gestionmedecin.models;

public class Medicament {
    private String nom;         // Nom du médicament
    private String datePrise;   // Date de prise du médicament
    private String dose;        // Dose prise
    private boolean pris;       // Indique si le médicament a été pris
    // Constructeur
    public Medicament(String nom, String datePrise, String dose, boolean pris) {
        this.nom = nom;
        this.datePrise = datePrise;
        this.dose = dose;
        this.pris = pris;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getDatePrise() {
        return datePrise;
    }

    public String getDose() {
        return dose;
    }
    public boolean isPris() { return pris; }


    // Setter pour 'pris'
    public void setPris(boolean pris) {
        this.pris = pris;
    }


}
