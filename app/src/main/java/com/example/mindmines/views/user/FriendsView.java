package com.example.mindmines.views.user;

import android.os.Bundle;
import android.view.View;

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

import java.util.List;


public class FriendsView extends BaseFragment {
    private AuthManager auth;

    public FriendsView() {
        super(R.layout.friends_leader_board);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = new AuthManager(requireContext());

        RecyclerView listView = requireActivity().findViewById(R.id.item_list_view);
        listView.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<UserDTO> itemList = loadFriends();
        UserCardAdapter listAdapter = new UserCardAdapter(itemList, this);
        listView.setAdapter(listAdapter);
    }

    public List<UserDTO> loadFriends() {
        return UserController.getInstance(requireContext()).getFriends(auth.getUserId());
    }
}
