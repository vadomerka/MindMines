package com.example.mindmines.views;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.Habit;
import com.example.mindmines.services.HabitCheckerService;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private final List<Habit> items;

    public CardAdapter(List<Habit> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_item_card, parent, false);
        System.out.println("onCreateViewHolder");
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        System.out.println(position);
        Habit h = items.get(position);
        holder.titleTextView.setText(h.getTitle());
        holder.descTextView.setText(h.getDescription());
        holder.checkBtn.setOnClickListener(v -> HabitCheckerService.buttonCheck((Button) v));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descTextView;
        Button changeBtn;
        Button checkBtn;

        CardViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.habit_title_card_view);
            descTextView = itemView.findViewById(R.id.habit_desc_card_view);
            changeBtn = itemView.findViewById(R.id.habit_card_change_btn);
            checkBtn = itemView.findViewById(R.id.habit_card_check_btn);
        }
    }
}
