package com.esprit.gestionmedecin.ui.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esprit.gestionmedecin.R;
import database.DatabaseHelper;  // Assurez-vous d'importer la classe DatabaseHelper

public class ReclamationFragment extends Fragment {

    private EditText etReclamation;
    private Button btnSubmitReclamation;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reclamations, container, false);

        // Initialisation des éléments de l'interface
        etReclamation = view.findViewById(R.id.et_reclamation);
        btnSubmitReclamation = view.findViewById(R.id.btn_submit_reclamation);

        // Crée une instance de DatabaseHelper
        databaseHelper = new DatabaseHelper(getContext());

        // Action lors du clic sur le bouton
        btnSubmitReclamation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reclamationText = etReclamation.getText().toString().trim(); // Enlever les espaces inutiles

                if (reclamationText.isEmpty()) {
                    // Affichage d'un message d'erreur si le champ est vide
                    Toast.makeText(getContext(), "Veuillez entrer une réclamation", Toast.LENGTH_SHORT).show();
                } else {
                    // Ajouter la réclamation à la base de données et vérifier le résultat
                    boolean isSuccess = databaseHelper.addReclamation(reclamationText);

                    if (isSuccess) {
                        // Afficher un message de succès si l'insertion a réussi
                        Toast.makeText(getContext(), "Réclamation soumise avec succès", Toast.LENGTH_SHORT).show();
                    } else {
                        // Afficher un message d'erreur si l'insertion a échoué
                        Toast.makeText(getContext(), "Erreur lors de l'ajout de la réclamation. Veuillez réessayer.", Toast.LENGTH_SHORT).show();
                    }

                    // Réinitialiser le champ de texte
                    etReclamation.setText("");
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        // Libérer la ressource de la base de données lorsque la vue est détruite
        if (databaseHelper != null) {
            databaseHelper.close();  // Fermer la base de données pour éviter les fuites de mémoire
        }
        super.onDestroyView();
    }
}
