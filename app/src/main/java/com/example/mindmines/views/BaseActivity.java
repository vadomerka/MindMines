package com.example.mindmines.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mindmines.R;
import com.example.mindmines.models.UserStatus;
import com.example.mindmines.services.UserStatusManager;
import com.example.mindmines.views.habit.HabitsView;
import com.example.mindmines.views.observers.UserStatusObserver;
import com.example.mindmines.views.user.FriendsView;
import com.example.mindmines.views.user.ProfileView;

public abstract class BaseActivity extends AppCompatActivity implements UserStatusObserver {
    protected TextView levelView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        View childView = getLayoutInflater().inflate(getContentLayoutId(), null);
        FrameLayout container = findViewById(R.id.content_container);
        container.addView(childView);

        initNavigation();
    }

    @SuppressLint("DefaultLocale")
    public void updateUserStatus() {
        runOnUiThread(() -> {
            UserStatus status = UserStatusManager.getStatus();
            levelView.setText(String.format("Уровень: %d; Опыт: %d/%d",
                    status.getLevel(), status.getExperience(), status.getMaxExperience()));
        });
    }

    protected abstract int getContentLayoutId();

    protected abstract Context getCurrentContext();

    private void initNavigation() {
        levelView = findViewById(R.id.navigation_title_view);

        findViewById(R.id.bottom_navigation_bar1).setOnClickListener(v -> openView(ProfileView.class));
//        findViewById(R.id.bottom_navigation_bar2).setOnClickListener(v -> openView(AiChatView.class));
        findViewById(R.id.bottom_navigation_bar3).setOnClickListener(v -> openView(HabitsView.class));
//        findViewById(R.id.bottom_navigation_bar4).setOnClickListener(v -> openView(ExpeditionsView.class));
        findViewById(R.id.bottom_navigation_bar5).setOnClickListener(v -> openView(FriendsView.class));
    }

    public void openView(Class<?> activity) {
        Intent myIntent = new Intent(getCurrentContext(), activity);
        getCurrentContext().startActivity(myIntent);
        finish();
    }
}