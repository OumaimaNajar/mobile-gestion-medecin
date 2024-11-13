package com.esprit.gestionmedecin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.esprit.gestionmedecin.R;
import com.esprit.gestionmedecin.models.Medicament;
import java.util.List;

public class HistoriqueAdapter extends RecyclerView.Adapter<HistoriqueAdapter.MedicamentViewHolder> {

    private Context context;
    private List<Medicament> medicamentList;

    // Constructeur de l'adaptateur
    public HistoriqueAdapter(Context context, List<Medicament> medicamentList) {
        this.context = context;
        this.medicamentList = medicamentList;
    }

    @Override
    public MedicamentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicament, parent, false);
        return new MedicamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicamentViewHolder holder, int position) {
        Medicament medicament = medicamentList.get(position);

        // Lier les données au ViewHolder
        holder.nomTextView.setText(medicament.getNom());
        holder.datePriseTextView.setText(medicament.getDatePrise());
        holder.doseTextView.setText(medicament.getDose());

        // Définir l'état du CheckBox selon le statut "pris" du médicament
        holder.prisCheckBox.setChecked(medicament.isPris());

        // Gérer l'événement de changement de l'état du CheckBox
        holder.prisCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            medicament.setPris(isChecked);
            updateMedicamentInDatabase(medicament);
        });
    }

    @Override
    public int getItemCount() {
        return medicamentList.size();
    }

    private void updateMedicamentInDatabase(Medicament medicament) {
        try {
            // DatabaseHelper databaseHelper = new DatabaseHelper(context);
            // databaseHelper.updateMedicament(medicament);  // Mise à jour dans la base de données

            Toast.makeText(context, "Médicament mis à jour", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Erreur lors de la mise à jour du médicament", Toast.LENGTH_SHORT).show();
        }
    }

    public static class MedicamentViewHolder extends RecyclerView.ViewHolder {
        TextView nomTextView;
        TextView datePriseTextView;
        TextView doseTextView;
        CheckBox prisCheckBox;

        public MedicamentViewHolder(View itemView) {
            super(itemView);
            nomTextView = itemView.findViewById(R.id.nomTextView);
            datePriseTextView = itemView.findViewById(R.id.datePriseTextView);
            doseTextView = itemView.findViewById(R.id.doseTextView);
            prisCheckBox = itemView.findViewById(R.id.prisCheckBox);
        }
    }
}
