package com.example.mindmines.views.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.game.expeditions.ExpeditionLocation;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private final List<ExpeditionLocation> locations;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private final OnLocationClickListener listener;
    private List<View> holders;

    public interface OnLocationClickListener {
        void onLocationClick(ExpeditionLocation expeditionLocation, int position);
    }

    public LocationAdapter(List<ExpeditionLocation> locations, OnLocationClickListener listener) {
        this.locations = locations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        holders = new ArrayList<>();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expedition_create_dialog_location_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpeditionLocation expeditionLocation = locations.get(position);
        holder.imageView.setImageResource(Integer.parseInt(expeditionLocation.getImage()));
        holder.textView.setText(expeditionLocation.getName());
        holders.add(holder.itemView);
        holder.itemView.setSelected(selectedPosition == position);
        holder.itemView.setOnClickListener(v -> {
            int previousSelected = selectedPosition;
            resetColors();
            v.setBackgroundColor(Color.GRAY);

            selectedPosition = holder.getBindingAdapterPosition();
            notifyItemChanged(previousSelected);
            notifyItemChanged(selectedPosition);
            if (listener != null) {
                listener.onLocationClick(expeditionLocation, selectedPosition);
            }
        });
    }

    private void resetColors() {
        for (View v: holders) {
            v.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public ExpeditionLocation getSelectedLocation() {
        if (selectedPosition != RecyclerView.NO_POSITION && selectedPosition < locations.size()) {
            return locations.get(selectedPosition);
        }
        return null;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.location_image);
            textView = itemView.findViewById(R.id.location_name);
        }
    }
}