package com.example.mindmines.views.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.checkers.HabitCurrentCheckerService;
import com.example.mindmines.views.habit.HabitsView;

import java.util.ArrayList;
import java.util.List;

public class HabitCardAdapter extends RecyclerView.Adapter<HabitCardAdapter.CardViewHolder> {
    private final List<Habit> items;
    private final HabitsView activity;
    private List<CardViewHolder> cardViews = new ArrayList<>();

    public HabitCardAdapter(List<Habit> items, HabitsView activity) {
        this.items = items;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_item_card, parent, false);
        return new CardViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Habit h = items.get(position);

        holder.hId = h.getHabitId();
        holder.titleTextView.setText(h.getTitle());
        holder.descTextView.setText(h.getDescription());
        holder.checkBtn.setOnClickListener(v -> HabitCurrentCheckerService.buttonStatusCheck((Button) v));  // buttonUpdate
        holder.checkBtn.setTag(h);
        holder.changeBtn.setOnClickListener(v -> activity.openHabitChangeView(h.getHabitId()));

        holder.streakTextView.setText(h.getStreakNumber().toString());
        holder.penaltyTextView.setText(h.getPenaltyNumber().toString());

        HabitCurrentCheckerService.buttonViewUpdate(holder.checkBtn);
        cardViews.add(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull CardViewHolder holder) {
        super.onViewAttachedToWindow(holder);
//        System.out.println(holder.titleTextView.getText().toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<CardViewHolder> getCardViews() { return cardViews; }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public int hId = 0;
        public TextView titleTextView;
        public TextView descTextView;
        public Button changeBtn;
        public Button checkBtn;
        public TextView streakTextView;
        public TextView penaltyTextView;

        CardViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.habit_title_card_view);
            descTextView = itemView.findViewById(R.id.habit_desc_card_view);
            changeBtn = itemView.findViewById(R.id.habit_card_change_btn);
            checkBtn = itemView.findViewById(R.id.habit_card_check_btn);
            streakTextView = itemView.findViewById(R.id.habit_streak_textView);
            penaltyTextView = itemView.findViewById(R.id.habit_penalty_textView);
        }
    }
}
