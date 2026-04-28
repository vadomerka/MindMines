package com.example.mindmines.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mindmines.R;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.checkers.HabitCurrentCheckerService;
import com.example.mindmines.services.checkers.HabitSyncCheckerService;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.observers.UserStatusObserver;

import java.util.List;
import java.util.Objects;

public abstract class BaseFragment extends Fragment {
    protected TextView levelView;
    protected final UserStatusObserver usProxy = this::updateUserStatus;

    public BaseFragment(int layout) {
        super(layout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        levelView = requireActivity().findViewById(R.id.navigation_title_view);
        Log.d("Debug baseFragment", "onViewCreated: " + levelView);
        updateUserStatus(null);
    }

    @SuppressLint("DefaultLocale")
    protected void updateUserStatus(List<UserStatus> upd) {
        Log.d("Debug BasicActivity updateUserStatus", "updateUserStatus: ");
        requireActivity().runOnUiThread(() -> {
            UserStatus status = UserStatusManager.getStatus();
            levelView.setText(String.format("Уровень: %d; Опыт: %d/%d",
                    status.getLevel(), status.getExperience(), status.getMaxExperience()));
        });
    }

    protected void loadDebugTools() {
        LinearLayout layout = requireView().findViewById(R.id.top_navigation_debugTools_layout);
        layout.setVisibility(View.VISIBLE);

        Button updBut = requireView().findViewById(R.id.update_nextDeadline_button);
        updBut.setOnClickListener(v -> HabitSyncCheckerService.allHabitsCheck(requireContext()));
        Button resetBut = requireView().findViewById(R.id.reset_userStatus_button);
        resetBut.setOnClickListener(v -> UserStatusManager.resetStatus());
        CheckBox autoCheck = requireView().findViewById(R.id.autoCheck_button);
        autoCheck.setChecked(HabitCurrentCheckerService.getDebug());
        autoCheck.setOnClickListener(v -> {
            boolean prev = autoCheck.isChecked();
            HabitCurrentCheckerService.setDebug(prev);
            HabitSyncCheckerService.setDebug(prev);
        });
    }
}