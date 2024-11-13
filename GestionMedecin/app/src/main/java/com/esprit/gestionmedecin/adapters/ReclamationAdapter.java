package com.esprit.gestionmedecin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esprit.gestionmedecin.R;
import com.esprit.gestionmedecin.models.Reclamation;

import java.util.List;

public class ReclamationAdapter extends RecyclerView.Adapter<ReclamationAdapter.ReclamationViewHolder> {

    private List<Reclamation> reclamationList;
    private final OnDeleteClickListener deleteClickListener;

    public ReclamationAdapter(List<Reclamation> reclamationList, OnDeleteClickListener onDeleteClickListener) {
        this.reclamationList = reclamationList;
        this.deleteClickListener = onDeleteClickListener;
    }

    // Méthode pour mettre à jour la liste de réclamations
    public void updateReclamationList(List<Reclamation> newReclamationList) {
        this.reclamationList = newReclamationList;
        notifyDataSetChanged();
    }

    // Interface pour gérer le clic sur le bouton de suppression
    public interface OnDeleteClickListener {
        void onDeleteClick(Reclamation reclamation);
    }

    @NonNull
    @Override
    public ReclamationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reclamation, parent, false);
        return new ReclamationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReclamationViewHolder holder, int position) {
        Reclamation reclamation = reclamationList.get(position);
        holder.bind(reclamation);
    }

    @Override
    public int getItemCount() {
        return reclamationList != null ? reclamationList.size() : 0;
    }

    // ViewHolder pour afficher les réclamations
    public class ReclamationViewHolder extends RecyclerView.ViewHolder {

        private final TextView reclamationTextView;
        private final Button deleteButton;

        public ReclamationViewHolder(@NonNull View itemView) {
            super(itemView);
            reclamationTextView = itemView.findViewById(R.id.myreclamation);
            deleteButton = itemView.findViewById(R.id.btndelete);

            // Configurer le clic sur le bouton de suppression
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && deleteClickListener != null) {
                        Reclamation reclamationToDelete = reclamationList.get(position);
                        deleteClickListener.onDeleteClick(reclamationToDelete);

                        // Supprimer l'élément de la liste et rafraîchir l'affichage
                        reclamationList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, reclamationList.size());
                    }
                }
            });
        }

        public void bind(Reclamation reclamation) {
            reclamationTextView.setText(reclamation.getReclamationText());
        }
    }
}
