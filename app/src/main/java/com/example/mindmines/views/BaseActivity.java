package com.example.mindmines.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mindmines.R;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        View childView = getLayoutInflater().inflate(getContentLayoutId(), null);
        FrameLayout container = findViewById(R.id.content_container);
        container.addView(childView);

        initNavigation();
    }

    protected abstract int getContentLayoutId();

    private void initNavigation() {
        Button bottomButton = findViewById(R.id.bottom_navigation_bar1);
        bottomButton.setOnClickListener(v -> Log.d("Debug navigation", "initNavigation: bottom button1"));
        bottomButton = findViewById(R.id.bottom_navigation_bar2);
        bottomButton.setOnClickListener(v -> Log.d("Debug navigation", "initNavigation: bottom button2"));
        bottomButton = findViewById(R.id.bottom_navigation_bar3);
        bottomButton.setOnClickListener(v -> Log.d("Debug navigation", "initNavigation: bottom button3"));
        bottomButton = findViewById(R.id.bottom_navigation_bar4);
        bottomButton.setOnClickListener(v -> Log.d("Debug navigation", "initNavigation: bottom button4"));
        bottomButton = findViewById(R.id.bottom_navigation_bar5);
        bottomButton.setOnClickListener(v -> Log.d("Debug navigation", "initNavigation: bottom button5"));
    }
}