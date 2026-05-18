package com.example.mindmines.views.user;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.infrastructure.UserController;
import com.example.mindmines.models.user.UserDTO;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.views.BaseFragment;
import com.example.mindmines.views.adapters.UserCardAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FriendsView extends BaseFragment {
    private final List<UserDTO> friends;
    private AuthManager auth;
    private LinearLayout leaderBoardLayout;
    private UserCardAdapter listAdapter;
    private RecyclerView listView;
    private ProgressBar loadingIndicator;
    private TextView exceptionView;

    public FriendsView() {
        super(R.layout.friends_leader_board);
        friends = new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = new AuthManager(requireContext());

        leaderBoardLayout = requireActivity().findViewById(R.id.leader_board_column_names);
        listView = requireActivity().findViewById(R.id.item_list_view);
        listView.setLayoutManager(new LinearLayoutManager(requireContext()));
        listAdapter = new UserCardAdapter(friends, this);
        listView.setAdapter(listAdapter);
        loadingIndicator = requireActivity().findViewById(R.id.loading_indicator);
        exceptionView = requireActivity().findViewById(R.id.exception_text_view);

        loadFriends();
    }

    public void loadFriends() {
        leaderBoardLayout.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.VISIBLE);
        exceptionView.setVisibility(View.GONE);
        UserController.getInstance(requireContext()).getFriends(this, auth.getUserId());
    }

    public void updateFriends(List<UserDTO> f) {
        requireActivity().runOnUiThread(() -> {
            leaderBoardLayout.setVisibility(View.VISIBLE);
            loadingIndicator.setVisibility(View.GONE);
            friends.clear();
            friends.addAll(f);
            listAdapter.notifyItemRangeInserted(0, f.size());
        });
    }

    public void handleException(Exception ex) {
        if (ex instanceof IOException) {
            showException("Произошла ошибка подключения");
        } else if (ex instanceof JSONException) {
            showException("Произошла ошибка при десериализации запроса");
        }
    }

    protected void showException(String message) {
        requireActivity().runOnUiThread(() -> {
            loadingIndicator.setVisibility(View.GONE);
            exceptionView.setVisibility(View.VISIBLE);
            exceptionView.setText(message);
        });
    }
}
