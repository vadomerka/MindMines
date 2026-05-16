package com.example.mindmines.views.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
    private AuthManager auth;
    private List<UserDTO> friends;
    private UserCardAdapter listAdapter;
    private RecyclerView listView;

    public FriendsView() {
        super(R.layout.friends_leader_board);
        friends = new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = new AuthManager(requireContext());

        listView = requireActivity().findViewById(R.id.item_list_view);
        listView.setLayoutManager(new LinearLayoutManager(requireContext()));
        listAdapter = new UserCardAdapter(friends, this);
        listView.setAdapter(listAdapter);

        loadFriends();
    }

    public void updateFriends(List<UserDTO> f) {
        requireActivity().runOnUiThread(() -> {
            friends.clear();
            friends.addAll(f);
            listAdapter.notifyItemRangeInserted(0, f.size());
        });
    }

    public void loadFriends() {
        UserController.getInstance(requireContext()).getFriends(this, auth.getUserId());
    }

    public void handleException(Exception ex) {
        if (ex instanceof IOException) {
            Toast.makeText(requireContext(), "Произошла ошибка подключения", Toast.LENGTH_SHORT).show();
        } else if (ex instanceof JSONException) {
            Toast.makeText(requireContext(), "Произошла ошибка при десериализации запроса", Toast.LENGTH_SHORT).show();
        }
    }
}
