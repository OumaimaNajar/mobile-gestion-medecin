package com.esprit.gestionmedecin.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
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

        // Ajout de l'écouteur pour le CheckBox
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                // Gérer les interactions, par exemple vérifier le clic sur le CheckBox
                View view = rv.findChildViewUnder(e.getX(), e.getY());
                if (view != null && rv.getChildAdapterPosition(view) != RecyclerView.NO_POSITION) {
                    CheckBox prisCheckBox = view.findViewById(R.id.prisCheckBox);
                    if (prisCheckBox != null) {
                        boolean isChecked = prisCheckBox.isChecked();
                        int position = rv.getChildAdapterPosition(view);
                        Medicament medicament = medicamentList.get(position);

                        // Mise à jour de l'état du médicament selon l'état du CheckBox
                        updateMedicamentStatus(medicament, isChecked);
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {}

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });

        return view;
    }
    // Méthode pour mettre à jour l'état du médicament
    private void updateMedicamentStatus(Medicament medicament, boolean isChecked) {
        // Si isChecked est vrai, le médicament est pris (true), sinon il est non pris (false)
        medicament.setPris(isChecked); // On passe directement le booléen
        databaseHelper.updateMedicamentStatus(medicament); // Cette méthode doit être implémentée dans DatabaseHelper
    }

            public void updateMedicamentList(List<Medicament> newMedicamentList) {
        if (historiqueAdapter != null) {
            medicamentList = newMedicamentList;
            historiqueAdapter.notifyDataSetChanged(); // Notifie l'adaptateur pour rafraîchir la vue
        }
    }


}
