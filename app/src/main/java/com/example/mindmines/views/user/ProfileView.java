package com.example.mindmines.views.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mindmines.MainActivity;
import com.example.mindmines.R;

import com.example.mindmines.models.user.UserStatus;
import com.example.mindmines.services.managers.UserStatusManager;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.views.BaseFragment;

public class ProfileView extends BaseFragment {
    private AuthManager auth;

    public ProfileView() {
        super(R.layout.user_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = new AuthManager(requireContext());

        LinearLayout topNav = requireActivity().findViewById(R.id.top_navigation_title_layout);
        topNav.setVisibility(View.INVISIBLE);

        Button btn = requireActivity().findViewById(R.id.logout_btn);
        btn.setOnClickListener(v -> logout());

        updateUserStatus();
    }

    private void logout() {
        auth.logout();
        Intent myIntent = new Intent(requireActivity(), LoginView.class);
        requireActivity().startActivity(myIntent);
        requireActivity().finish();
    }

    @SuppressLint("SetTextI18n")
    public void updateUserStatus() {
        String email = auth.getEmail();
        TextView profileTitle = requireActivity().findViewById(R.id.user_info_view);
        profileTitle.setText("Профиль: " + email);
        if (MainActivity.isDebug()) {
            profileTitle.setText("Почта: " + email + "\n"
                    + "Token: " + auth.getAuthToken() + "\n"
                    + "Id: " + auth.getUserId() + "\n"
            );
        }

        UserStatus status = UserStatusManager.getInstance(requireContext()).getStatus();
        TextView levelValue = requireActivity().findViewById(R.id.level_value_view);
        TextView expValue = requireActivity().findViewById(R.id.exp_value_view);
        TextView expUntilValue = requireActivity().findViewById(R.id.exp_until_next_value_view);
        levelValue.setText(status.getLevel().toString());
        expValue.setText(status.getExperience().toString());
        expUntilValue.setText(String.valueOf(status.getMaxExperience() - status.getExperience()));
    }
}
