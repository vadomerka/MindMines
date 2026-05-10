package com.example.mindmines.views.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.MainActivity;
import com.example.mindmines.R;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.services.checkers.HabitCurrentCheckerService;
import com.example.mindmines.views.habit.HabitsView;

import java.util.ArrayList;
import java.util.List;

public class HabitCardAdapter extends RecyclerView.Adapter<HabitCardAdapter.CardViewHolder> {
    private final List<Habit> items;
    private final HabitsView fragment;
    private final List<CardViewHolder> cardViews = new ArrayList<>();

    public HabitCardAdapter(List<Habit> items, HabitsView fragment) {
        this.items = items;
        this.fragment = fragment;
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

        holder.hId = h.getId();
        holder.titleTextView.setText(h.getTitle());
        holder.descTextView.setText(h.getDescription());
        holder.checkBtn.setOnClickListener(v ->
                HabitCurrentCheckerService.buttonStatusCheck((Button) v, fragment.requireContext().getApplicationContext()));
        holder.checkBtn.setTag(h);
        holder.changeBtn.setOnClickListener(v -> fragment.openHabitChangeView(h.getId()));
        holder.deleteBtn.setOnClickListener(v -> fragment.deleteHabit(h.getId()));

        if (MainActivity.isDebug()) {
            holder.debugHolder.setVisibility(View.VISIBLE);
        }
        holder.streakTextView.setText(h.getStreakNumber().toString());
        holder.penaltyTextView.setText(h.getPenaltyNumber().toString());

        HabitCurrentCheckerService.buttonViewUpdate(holder.checkBtn);
        cardViews.add(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull CardViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<CardViewHolder> getCardViews() {
        return cardViews;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public int hId = 0;
        public TextView titleTextView;
        public TextView descTextView;
        public Button deleteBtn;
        public Button changeBtn;
        public Button checkBtn;
        public LinearLayout debugHolder;
        public TextView streakTextView;
        public TextView penaltyTextView;

        CardViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.habit_title_card_view);
            descTextView = itemView.findViewById(R.id.habit_desc_card_view);
            deleteBtn = itemView.findViewById(R.id.habit_card_delete_btn);
            changeBtn = itemView.findViewById(R.id.habit_card_change_btn);
            checkBtn = itemView.findViewById(R.id.habit_card_check_btn);

            debugHolder = itemView.findViewById(R.id.habit_card_debug_layout);
            streakTextView = itemView.findViewById(R.id.habit_streak_textView);
            penaltyTextView = itemView.findViewById(R.id.habit_penalty_textView);
        }
    }
}
