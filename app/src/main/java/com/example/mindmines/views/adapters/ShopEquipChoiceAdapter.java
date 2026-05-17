package com.example.mindmines.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.game.equipment.types.Equipment;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ShopEquipChoiceAdapter extends RecyclerView.Adapter<ShopEquipChoiceAdapter.ViewHolder> {
    private final List<Equipment> options;
    private final OnSelectionChangeListener selectionListener;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private final int selectedColor;
    private final int unselectedColor;

    public ShopEquipChoiceAdapter(List<Equipment> options, OnSelectionChangeListener selectionListener,
                                  int selectedColor, int unselectedColor) {
        this.options = options;
        this.selectionListener = selectionListener;
        this.selectedColor = selectedColor;
        this.unselectedColor = unselectedColor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.equipment_shop_dialog_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Equipment equipment = options.get(position);
        int iconId = Integer.parseInt(equipment.getImage());
        holder.button.setIcon(ContextCompat.getDrawable(holder.itemView.getContext(), iconId));
        boolean selected = selectedPosition == position;
        holder.button.setBackgroundColor(selected ? selectedColor : unselectedColor);

        holder.button.setOnClickListener(v -> {
            int old = selectedPosition;
            int pos = holder.getBindingAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;
            selectedPosition = pos;
            if (old != RecyclerView.NO_POSITION) {
                notifyItemChanged(old);
            }
            notifyItemChanged(selectedPosition);
            if (selectionListener != null) {
                selectionListener.onSelectionChanged(options.get(selectedPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public Equipment getSelectedEquipment() {
        if (selectedPosition != RecyclerView.NO_POSITION && selectedPosition < options.size()) {
            return options.get(selectedPosition);
        }
        return null;
    }

    public interface OnSelectionChangeListener {
        void onSelectionChanged(Equipment selected);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final MaterialButton button;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.shop_equipment_item_button);
        }
    }
}
