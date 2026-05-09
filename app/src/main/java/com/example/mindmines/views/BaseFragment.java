package com.example.mindmines.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mindmines.R;
import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.checkers.HabitCurrentCheckerService;
import com.example.mindmines.services.checkers.HabitSyncCheckerService;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.observers.UserStatusObserver;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public abstract class BaseFragment extends Fragment {
    protected TextView levelValueView;
    protected ProgressBar expProgressBar;
    protected TextView coinValueView;
    protected MaterialButton backButton;
    protected final UserStatusObserver usProxy = this::updateUserStatus;

    public BaseFragment(int layout) {
        super(layout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout topNav = requireActivity().findViewById(R.id.top_navigation_title_layout);
        topNav.setVisibility(View.VISIBLE);
        levelValueView = requireActivity().findViewById(R.id.navigation_level_view_text_value);
        expProgressBar = requireActivity().findViewById(R.id.experience_progress_bar);
        coinValueView = requireActivity().findViewById(R.id.coin_value_view);

        backButton = requireActivity().findViewById(R.id.navigation_back_button);
        backButton.setOnClickListener(v -> returnBack());

        setBackButtonVisible(false);
        updateUserStatus(null);
    }

    protected void setBackButtonVisible(boolean isVisible) {
        if (backButton == null) {
            return;
        }
        backButton.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    protected void returnBack() {}

    @SuppressLint("DefaultLocale")
    protected void updateUserStatus(List<UserStatus> upd) {
        requireActivity().runOnUiThread(() -> {
            UserStatus status = UserStatusManager.getInstance(requireContext()).getStatus();
            if (status == null) status = new UserStatus(new AuthManager(requireContext()).getUserId());

            Log.d("Debug updateUserStatus", "updateUserStatus: " + status.getExperience() + " " + status.getMaxExperience());

            levelValueView.setText(String.valueOf(status.getLevel()));
            expProgressBar.setMax(status.getMaxExperience().intValue());
            expProgressBar.setProgress(status.getExperience().intValue());
            coinValueView.setText(String.valueOf(status.getCoins()));
        });
    }

    protected void loadDebugTools() {
        LinearLayout layout = requireView().findViewById(R.id.top_navigation_debugTools_layout);
        layout.setVisibility(View.VISIBLE);

        Button updBut = requireView().findViewById(R.id.update_nextDeadline_button);
        updBut.setOnClickListener(v -> HabitSyncCheckerService.allHabitsCheck(requireContext()));
        Button resetBut = requireView().findViewById(R.id.reset_userStatus_button);
        resetBut.setOnClickListener(v -> UserStatusManager.getInstance(requireContext()).resetStatus());
        CheckBox autoCheck = requireView().findViewById(R.id.autoCheck_button);
        autoCheck.setChecked(HabitCurrentCheckerService.getDebug());
        autoCheck.setOnClickListener(v -> {
            boolean prev = autoCheck.isChecked();
            HabitCurrentCheckerService.setDebug(prev);
            HabitSyncCheckerService.setDebug(prev);
        });
    }
}