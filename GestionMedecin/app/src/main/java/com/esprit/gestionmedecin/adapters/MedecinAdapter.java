package com.esprit.gestionmedecin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esprit.gestionmedecin.R;
import com.esprit.gestionmedecin.models.Medecin;

import java.util.List;

public class MedecinAdapter extends RecyclerView.Adapter<MedecinAdapter.MedecinViewHolder> {

    private List<Medecin> medecinList;

    public MedecinAdapter(Context context, List<Medecin> medecinList) {
        this.medecinList = medecinList;
    }

    @NonNull
    @Override
    public MedecinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medecin, parent, false);
        return new MedecinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedecinViewHolder holder, int position) {
        Medecin medecin = medecinList.get(position);
        holder.nom.setText(medecin.getNom());
        holder.specialite.setText(medecin.getSpecialite());
        holder.num.setText(medecin.getNum());

        // Ajouter une action pour le bouton "Rendez-vous"
        holder.btnRendezVous.setOnClickListener(v -> {
            // Logique pour prendre un rendez-vous (par exemple, ouvrir un nouveau fragment ou une activité)
        });
    }

    @Override
    public int getItemCount() {
        return medecinList.size();
    }

    public static class MedecinViewHolder extends RecyclerView.ViewHolder {

        TextView nom, specialite, num;
        Button btnRendezVous;

        public MedecinViewHolder(@NonNull View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.medecinNom);
            specialite = itemView.findViewById(R.id.medecinSpecialite);
            num = itemView.findViewById(R.id.medecinNum);
            btnRendezVous = itemView.findViewById(R.id.btnRendezVous);
        }
    }
}
