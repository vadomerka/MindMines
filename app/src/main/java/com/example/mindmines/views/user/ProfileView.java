package com.example.mindmines.views.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        TextView tv = requireView().findViewById(R.id.navigation_title_view);
        tv.setText("Ваш профиль:");
        Button btn = requireView().findViewById(R.id.logout_btn);
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
        // TODO: change to auth token and id;
        String email = auth.getAuthToken();
        TextView profileTitle = requireView().findViewById(R.id.user_info_view);
        profileTitle.setText(email);

        UserStatus status = UserStatusManager.getStatus();
        TextView levelValue = requireView().findViewById(R.id.level_value_view);
        TextView expValue = requireView().findViewById(R.id.exp_value_view);
        TextView expUntilValue = requireView().findViewById(R.id.exp_until_next_value_view);
        levelValue.setText(status.getLevel().toString());
        expValue.setText(status.getExperience().toString());
        expUntilValue.setText(String.valueOf(status.getMaxExperience() - status.getExperience()));
    }
}
