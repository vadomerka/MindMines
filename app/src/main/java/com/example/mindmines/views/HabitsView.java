package com.example.mindmines.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.Habit;
import com.example.mindmines.services.auth.AuthManager;
import com.example.mindmines.services.repositories.HabitRepository;

import java.util.List;
import java.util.stream.Collectors;

public class HabitsView extends AppCompatActivity {
    private AuthManager auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.habits_view);

        RecyclerView listView = findViewById(R.id.habits_list_view);
        listView.setLayoutManager(new LinearLayoutManager(this));

        List<String> itemList = loadItemList();
        CardAdapter cardAdapter = new CardAdapter(itemList);
        listView.setAdapter(cardAdapter);
    }

    private List<String> loadItemList() {
        // TODO: implement sort and filters.
        return HabitRepository.getAll().stream().map(Habit::getTitle).collect(Collectors.toList());
    }
}
