package com.example.mindmines.views.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.dto.UserDTO;
import com.example.mindmines.views.user.FriendsView;

import java.util.ArrayList;
import java.util.List;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.UserCardViewHolder> {
    private final List<UserDTO> items;
    private final FriendsView activity;
    private List<UserCardViewHolder> cardViews = new ArrayList<>();

    public UserCardAdapter(List<UserDTO> items, FriendsView activity) {
        this.items = items;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_card, parent, false);
        return new UserCardViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(UserCardViewHolder holder, int position) {
        UserDTO dto = items.get(position);

        holder.userId = dto.userID;
        holder.nameView.setText(String.valueOf(position + 1));
        holder.nameView.setText(dto.name);
        holder.levelView.setText(String.valueOf(dto.level));
        cardViews.add(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull UserCardViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class UserCardViewHolder extends RecyclerView.ViewHolder {
        public Integer userId = 0;
        public TextView rankView;
        public TextView nameView;
        public TextView levelView;

        UserCardViewHolder(View itemView) {
            super(itemView);

            rankView = itemView.findViewById(R.id.user_rank_value_view);
            nameView = itemView.findViewById(R.id.user_name_value_view);
            levelView = itemView.findViewById(R.id.user_level_value_view);
        }
    }
}
