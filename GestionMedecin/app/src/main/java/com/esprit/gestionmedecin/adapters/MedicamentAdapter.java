package com.esprit.gestionmedecin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esprit.gestionmedecin.R;
import com.esprit.gestionmedecin.models.Medicament;
import java.util.List;

public class MedicamentAdapter extends RecyclerView.Adapter<MedicamentAdapter.MedicamentViewHolder> {
    private Context context;
    private List<Medicament> medicamentList;

    public MedicamentAdapter(Context context, List<Medicament> medicamentList) {
        this.context = context;
        this.medicamentList = medicamentList;
    }

    @NonNull
    @Override
    public MedicamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicament, parent, false);
        return new MedicamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentViewHolder holder, int position) {
        Medicament medicament = medicamentList.get(position);
        holder.medicamentNom.setText(medicament.getNom());
        holder.medicamentDate.setText("Date de Prise: " + medicament.getDatePrise());
        holder.medicamentDose.setText("Dose: " + medicament.getDose());
    }

    @Override
    public int getItemCount() {
        return medicamentList.size();
    }

    static class MedicamentViewHolder extends RecyclerView.ViewHolder {
        TextView medicamentNom, medicamentDate, medicamentDose;

        public MedicamentViewHolder(@NonNull View itemView) {
            super(itemView);
            medicamentNom = itemView.findViewById(R.id.nomTextView);
            medicamentDate = itemView.findViewById(R.id.datePriseTextView);
            medicamentDose = itemView.findViewById(R.id.doseTextView);
        }
    }
}
