package com.esprit.gestionmedecin.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.esprit.gestionmedecin.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.esprit.gestionmedecin.R;
import database.DatabaseHelper ;


public class AjoutMedicamentFragment extends Fragment {
    private EditText nomEditText, datePriseEditText, doseEditText;
    private Button saveButton;
    private DatabaseHelper databaseHelper;

    public AjoutMedicamentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajoutmedicaments, container, false);

        // Initialisation des champs d'entrée
        nomEditText = view.findViewById(R.id.et_nom_medicament);
        datePriseEditText = view.findViewById(R.id.et_date_prise);
        doseEditText = view.findViewById(R.id.et_dose);
        saveButton = view.findViewById(R.id.btn_save);

        // Initialisation de DatabaseHelper
        databaseHelper = new DatabaseHelper(getContext());

        // Configuration de l'action du bouton pour sauvegarder le médicament
        saveButton.setOnClickListener(v -> {
            String nom = nomEditText.getText().toString();
            String datePrise = datePriseEditText.getText().toString();
            String dose = doseEditText.getText().toString();

            // Appel à la méthode addMedicament pour ajouter le médicament avec pris par défaut à "non pris"
            boolean isAdded = databaseHelper.addMedicament(nom, datePrise, dose);

            if (isAdded) {
                Toast.makeText(getContext(), "Médicament ajouté avec succès", Toast.LENGTH_SHORT).show();
                // Réinitialiser les champs après l'ajout
                nomEditText.setText("");
                datePriseEditText.setText("");
                doseEditText.setText("");
            } else {
                Toast.makeText(getContext(), "Erreur lors de l'ajout du médicament", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
