package com.esprit.gestionmedecin.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.esprit.gestionmedecin.R;
import com.esprit.gestionmedecin.adapters.HistoriqueAdapter;
import com.esprit.gestionmedecin.models.Medicament;
import java.util.ArrayList;
import java.util.List;
import database.DatabaseHelper;

public class HistoriqueFragment extends Fragment {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private HistoriqueAdapter historiqueAdapter;
    private List<Medicament> medicamentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historique, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewHistorique); // Assurez-vous que l'ID est correct
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseHelper = new DatabaseHelper(getContext());

        // Récupérer la liste des médicaments depuis la base de données
        medicamentList = databaseHelper.getAllMedicaments(); // Cette méthode doit être implémentée dans DatabaseHelper

        if (medicamentList == null) {
            medicamentList = new ArrayList<>();
            Log.e("HistoriqueFragment", "Aucun médicament trouvé.");
        } else {
            Log.d("HistoriqueFragment", "Liste des médicaments récupérée.");
        }

        if (recyclerView != null) {
            historiqueAdapter = new HistoriqueAdapter(getContext(), medicamentList);
            recyclerView.setAdapter(historiqueAdapter);
        } else {
            Log.e("HistoriqueFragment", "RecyclerView est null");
        }

        return view;
    }

    public void updateMedicamentList(List<Medicament> newMedicamentList) {
        if (historiqueAdapter != null) {
            medicamentList = newMedicamentList;
            historiqueAdapter.notifyDataSetChanged(); // Notifier l'adaptateur que la liste a été mise à jour
        }
    }
}
