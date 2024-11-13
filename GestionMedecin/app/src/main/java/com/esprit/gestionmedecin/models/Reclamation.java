package com.esprit.gestionmedecin.models;

import androidx.annotation.NonNull;

public class Reclamation {

    private int id;
    private String reclamationText;

    // Constructeur sans argument
    public Reclamation() {
    }

    // Constructeur avec tous les attributs
    public Reclamation(int id, String reclamationText) {
        this.id = id;
        this.reclamationText = reclamationText;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReclamationText() {
        return reclamationText;
    }

    public void setReclamationText(String reclamationText) {
        this.reclamationText = reclamationText;
    }

    // Méthode pour afficher les informations de la réclamation
    @NonNull
    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", reclamationText='" + reclamationText + '\'' +
                '}';
    }
}
