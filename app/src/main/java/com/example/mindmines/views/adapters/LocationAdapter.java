package com.example.mindmines.views.adapters;

import android.content.Context;
import android.content.res.Resources;
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
import com.example.mindmines.services.managers.ExpeditionLocationManager;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private final Context context;
    private final Resources resources;
    private final List<ExpeditionLocation> locations;
    private final OnLocationClickListener listener;
    private final List<View> holders;
    private final ExpeditionLocationManager elm;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public LocationAdapter(Context context, Resources resources, List<ExpeditionLocation> locations, OnLocationClickListener listener) {
        this.context = context;
        this.resources = resources;
        this.locations = locations;
        this.listener = listener;
        this.holders = new ArrayList<>();
        elm = ExpeditionLocationManager.getInstance(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expedition_create_dialog_location_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpeditionLocation expeditionLocation = locations.get(position);

        holder.imageView.setImageResource(Integer.parseInt(expeditionLocation.getImage()));
        holder.titleView.setText(expeditionLocation.getName());
        String requirement = "Минимальный уровень персонажа: " + expeditionLocation.getLevel();
        holder.infoView.setText(requirement);
        holders.add(holder.itemView);

        if (elm.isAvailable(expeditionLocation)) {
            holder.itemView.setSelected(selectedPosition == position);
            holder.itemView.setOnClickListener(v -> {
                resetColors();
                v.setBackgroundColor(Color.GREEN);

                selectedPosition = holder.getBindingAdapterPosition();

                if (listener != null) {
                    listener.onLocationClick(expeditionLocation, selectedPosition);
                }
            });
        } else {
            holder.itemView.setSelected(false);
            holder.itemView.setOnClickListener(v -> {
                holder.infoView.setBackgroundColor(resources.getColor(R.color.error_message_color, context.getTheme()));
            });
        }
    }

    private void resetColors() {
        for (View v : holders) {
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

    public interface OnLocationClickListener {
        void onLocationClick(ExpeditionLocation expeditionLocation, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;
        TextView infoView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.location_image);
            titleView = itemView.findViewById(R.id.location_name);
            infoView = itemView.findViewById(R.id.location_requirements);
        }
    }
}