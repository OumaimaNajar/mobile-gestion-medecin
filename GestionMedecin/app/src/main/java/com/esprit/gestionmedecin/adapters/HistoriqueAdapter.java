package com.esprit.gestionmedecin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.esprit.gestionmedecin.R;
import com.esprit.gestionmedecin.models.Medicament;

import org.jetbrains.annotations.NotNull;

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

        // Mettre à jour l'état de la CheckBox en fonction de l'état 'pris'
        holder.prisCheckBox.setChecked(medicament.isPris());

        // Définir le listener pour le bouton de suppression
        holder.btnRemove.setOnClickListener(v -> {
            deleteMedicament(medicament, position);
        });


        // Définir le listener pour le CheckBox "Pris"
        holder.prisCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Mettre à jour l'état de "pris" dans l'objet Medicament
            medicament.setPris(isChecked);

            // Optionnel : vous pouvez aussi mettre à jour la base de données ici
            updateMedicamentInDatabase(medicament);

            // Afficher un message de confirmation
            String status = isChecked ? "Pris" : "Non pris";
            Toast.makeText(context, medicament.getNom() + " a été marqué comme " + status, Toast.LENGTH_SHORT).show();

            // Mise à jour de l'élément de la liste et notification de l'adaptateur
            notifyItemChanged(position);  // Cela informe l'adaptateur qu'une modification a été effectuée à cette position

        });
    }

    private void deleteMedicament(Medicament medicament, int position) {
        try {
            // Suppression dans la base de données
            // DatabaseHelper databaseHelper = new DatabaseHelper(context);
            // databaseHelper.deleteMedicament(medicament);  // Suppression dans la base de données

            // Suppression dans la liste et notification de RecyclerView
            medicamentList.remove(position);
            notifyItemRemoved(position);

            Toast.makeText(context, "Médicament supprimé", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Erreur lors de la suppression du médicament", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public int getItemCount() {
        return medicamentList.size();
    }

    private void updateMedicamentInDatabase(Medicament medicament) {
        try {
            // Exemple de mise à jour de la base de données (remplacez avec votre logique)
            // DatabaseHelper databaseHelper = new DatabaseHelper(context);
            // databaseHelper.updateMedicament(medicament);  // Mettre à jour dans la base de données

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
        Button btnRemove;


        public MedicamentViewHolder(@NotNull View itemView) {
            super(itemView);

            nomTextView = itemView.findViewById(R.id.nomTextView);
            datePriseTextView = itemView.findViewById(R.id.datePriseTextView);
            doseTextView = itemView.findViewById(R.id.doseTextView);
            prisCheckBox = itemView.findViewById(R.id.prisCheckBox);
            btnRemove = itemView.findViewById(R.id.btnRemove);


        }
    }


    // Méthode pour ajouter un médicament
    public void addMedicament(Medicament medicament) {
        medicamentList.add(medicament);
        notifyItemInserted(medicamentList.size() - 1);  // Notifie que l'élément a été ajouté à la fin de la liste
    }



}
