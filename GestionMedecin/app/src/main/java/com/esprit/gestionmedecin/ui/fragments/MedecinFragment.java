package com.esprit.gestionmedecin.ui.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esprit.gestionmedecin.R;
import com.esprit.gestionmedecin.adapters.MedecinAdapter;
import com.esprit.gestionmedecin.models.Medecin;
import database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MedecinFragment extends Fragment {

    private RecyclerView recyclerView;
    private MedecinAdapter medecinAdapter;
    private List<Medecin> medecins;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate le layout du fragment
        View view = inflater.inflate(R.layout.fragment_medecin, container, false);

        // Initialisation du RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialisation de la liste des médecins
        medecins = new ArrayList<>();

        // Initialisation de l'adapter avec une liste vide
        medecinAdapter = new MedecinAdapter(getContext(), medecins);
        recyclerView.setAdapter(medecinAdapter);

        // Récupération des médecins à partir de la base de données
        loadMedecins();

        return view;
    }

    private void loadMedecins() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        Cursor cursor = null;

        try {
            cursor = dbHelper.getAllMedecins();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
                    String specialite = cursor.getString(cursor.getColumnIndexOrThrow("specialite"));
                    String numero = cursor.getString(cursor.getColumnIndexOrThrow("numero"));

                    Medecin medecin = new Medecin(id, nom, specialite, numero);
                    medecins.add(medecin);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // Notifiez l'adaptateur de la mise à jour de la liste
        medecinAdapter.notifyDataSetChanged();
    }


}
