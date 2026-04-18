package com.example.mindmines.requests;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitDTO;
import com.example.mindmines.services.factories.HabitFactory;

public class HabitRequestSender {
    public static Habit post(HabitDTO h) {
        // TODO: заменить на настоящую отправку данных серверу.
        return HabitFactory.createFromDTO(h);
    }

    public static Habit put(Integer hId, HabitDTO h) {
        // TODO: заменить на настоящую отправку данных серверу.
        return HabitFactory.createFromDTO(hId, h);
    }
}
