package com.example.mindmines.requests;

import com.example.mindmines.models.Habit;
import com.example.mindmines.models.dto.HabitDTO;
import com.example.mindmines.services.factories.HabitFactory;

public class HabitRequestSender {
    public static Habit add(HabitDTO h) {
        // TODO: заменить на настоящую отправку данных серверу.
        return HabitFactory.createFromDTO(h);
    }
}
