package com.example.mindmines.views.user;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.infrastructure.UserController;
import com.example.mindmines.models.user.User;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.views.BaseActivity;
import com.example.mindmines.views.adapters.UserCardAdapter;

import java.util.List;

public class FriendsView extends BaseActivity {
    private AuthManager auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = new AuthManager(getApplicationContext());

        RecyclerView listView = findViewById(R.id.item_list_view);
        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<User> itemList = loadFriends();
        UserCardAdapter listAdapter = new UserCardAdapter(itemList, this);
        listView.setAdapter(listAdapter);

        TextView tv = findViewById(R.id.navigation_title_view);
        tv.setText("Топ игроков:");
        Button navButton = findViewById(R.id.bottom_navigation_bar5);
        navButton.setEnabled(false);
    }

    @Override
    protected int getContentLayoutId() { return R.layout.friends_leader_board; }

    @Override
    protected Context getCurrentContext() { return FriendsView.this; }

    public List<User> loadFriends() {
        return UserController.getFriends(auth.getUserId());
    }
}
