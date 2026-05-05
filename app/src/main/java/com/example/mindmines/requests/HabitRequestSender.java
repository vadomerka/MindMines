package com.example.mindmines.requests;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitDTO;
import com.example.mindmines.services.factories.HabitFactory;

public class HabitRequestSender {
    public static Habit post(HabitDTO h) {
        // TODO: заменить на настоящую отправку данных серверу.
        return HabitFactory.getInstance().createFromDTO(h);
    }

    public static Habit put(Integer hId, HabitDTO h) {
        // TODO: заменить на настоящую отправку данных серверу.
        return HabitFactory.getInstance().createFromDTO(hId, h);
    }

    public static boolean delete(Integer hId) {
        // TODO: заменить на настоящую отправку данных серверу.
        return true;
    }
}
