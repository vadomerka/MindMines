package com.example.mindmines.views.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mindmines.MainActivity;
import com.example.mindmines.R;
import com.example.mindmines.requests.ServerProperties;

public class ServerUrlChangeView extends AppCompatActivity {
    protected EditText urlInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_url_view);

        urlInput = findViewById(R.id.server_url_input);

        Button saveBtn = findViewById(R.id.save_url_button);

        saveBtn.setOnClickListener(v -> saveUrl());
    }

    private void saveUrl() {
        ServerProperties.getInstance().SERVER_URL = urlInput.getText().toString();

        Intent myIntent = new Intent(ServerUrlChangeView.this, MainActivity.class);
        ServerUrlChangeView.this.startActivity(myIntent);
        finish();
    }
}