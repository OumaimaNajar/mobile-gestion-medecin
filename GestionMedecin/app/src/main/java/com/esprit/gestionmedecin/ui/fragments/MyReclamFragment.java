package com.esprit.gestionmedecin.ui.fragments;

import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.esprit.gestionmedecin.R;
import com.esprit.gestionmedecin.adapters.ReclamationAdapter;
import com.esprit.gestionmedecin.models.Reclamation;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseHelper;

public class MyReclamFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReclamationAdapter reclamationAdapter;
    private List<Reclamation> reclamationList = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myreclam, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reclamationAdapter = new ReclamationAdapter(reclamationList, new ReclamationAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Reclamation reclamation) {
                // Code pour supprimer la réclamation de la base de données
                deleteReclamation(reclamation);

                // Mise à jour de la liste et rafraîchissement de l'adaptateur
                reclamationList.remove(reclamation);
                reclamationAdapter.updateReclamationList(reclamationList);
            }
        });
        recyclerView.setAdapter(reclamationAdapter);

        recyclerView.setAdapter(reclamationAdapter);

        // Initialiser la base de données
        databaseHelper = new DatabaseHelper(getContext());

        // Charger les réclamations
        loadReclamations();

        return view;
    }

    private void loadReclamations() {
        Cursor cursor = (Cursor) databaseHelper.getAllReclamations();

        // Vérification que le curseur n'est pas null avant de l'utiliser
        if (cursor != null && cursor.moveToFirst()) {
            reclamationList.clear();  // Effacer les anciennes réclamations
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));  // Récupérer l'ID de la réclamation
                String reclamationText = cursor.getString(cursor.getColumnIndexOrThrow("reclamation"));  // Récupérer le texte de la réclamation

                // Créer une instance de Reclamation et l'ajouter à la liste
                Reclamation reclamation = new Reclamation(id, reclamationText);
                reclamationList.add(reclamation);
            } while (cursor.moveToNext());  // Passer à la réclamation suivante

            // Notifier l'adaptateur que la liste a été mise à jour
            reclamationAdapter.notifyDataSetChanged();
            cursor.close();  // Fermer le curseur après usage
        } else {
            // Afficher un message si aucune réclamation n'est trouvée
            Toast.makeText(getContext(), "Aucune réclamation trouvée", Toast.LENGTH_SHORT).show();
        }
    }


    private void deleteReclamation(Reclamation reclamation) {
        int reclamationId = reclamation.getId();
        Log.d("DeleteReclamation", "Attempting to delete reclamation with ID: " + reclamationId);

        boolean deleted = databaseHelper.deleteReclamation(reclamationId);
        if (deleted) {
            reclamationList.remove(reclamation);
            reclamationAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "Réclamation supprimée", Toast.LENGTH_SHORT).show();
            loadReclamations();
        } else {
            Toast.makeText(getContext(), "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateReclamationList(List<Reclamation> newReclamationList) {
        this.reclamationList = newReclamationList;
        reclamationAdapter.notifyDataSetChanged();
    }




    @Override
    public void onDestroyView() {
        // Fermer la base de données si elle est ouverte
        if (databaseHelper != null) {
            databaseHelper.close();
        }
        super.onDestroyView();
    }
}
