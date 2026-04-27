package com.example.mindmines.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mindmines.R;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.checkers.HabitCurrentCheckerService;
import com.example.mindmines.services.checkers.HabitSyncCheckerService;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.views.assistant.AssistantView;
import com.example.mindmines.views.game.PartyView;
import com.example.mindmines.views.habit.HabitsView;
import com.example.mindmines.services.observers.UserStatusObserver;
import com.example.mindmines.views.user.FriendsView;
import com.example.mindmines.views.user.ProfileView;

import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {
    protected TextView levelView;
    protected final UserStatusObserver usProxy = this::updateUserStatus;

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
    protected void updateUserStatus(List<UserStatus> upd) {
        Log.d("Debug BasicActivity updateUserStatus", "updateUserStatus: ");
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
        findViewById(R.id.bottom_navigation_bar2).setOnClickListener(v -> openView(AssistantView.class));
        findViewById(R.id.bottom_navigation_bar3).setOnClickListener(v -> openView(HabitsView.class));
        findViewById(R.id.bottom_navigation_bar4).setOnClickListener(v -> openView(PartyView.class));
        findViewById(R.id.bottom_navigation_bar5).setOnClickListener(v -> openView(FriendsView.class));
    }

    public void openView(Class<?> activity) {
        Intent myIntent = new Intent(getCurrentContext(), activity);
        getCurrentContext().startActivity(myIntent);
        finish();
    }

    protected void loadDebugTools() {
        LinearLayout layout = findViewById(R.id.top_navigation_debugTools_layout);
        layout.setVisibility(View.VISIBLE);

        Button updBut = findViewById(R.id.update_nextDeadline_button);
        updBut.setOnClickListener(v -> HabitSyncCheckerService.allHabitsCheck(this));
        Button resetBut = findViewById(R.id.reset_userStatus_button);
        resetBut.setOnClickListener(v -> UserStatusManager.resetStatus());
        CheckBox autoCheck = findViewById(R.id.autoCheck_button);
        autoCheck.setChecked(HabitCurrentCheckerService.getDebug());
        autoCheck.setOnClickListener(v -> {
            boolean prev = autoCheck.isChecked();
            HabitCurrentCheckerService.setDebug(prev);
            HabitSyncCheckerService.setDebug(prev);
        });
    }
}