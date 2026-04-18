package com.example.mindmines.infrastructure;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitDTO;
import com.example.mindmines.requests.HabitRequestSender;
import com.example.mindmines.services.managers.HabitManager;

public class HabitController {
    public static Habit add(HabitDTO hDTO) {
        try {
            Habit result = HabitRequestSender.post(hDTO);
            HabitManager.add(result);
            return result;
        } catch (Exception e) {
            System.out.println("Произошла ошибка при добавлении привычки.");
        }
        return null;
    }

    public static Habit change(Integer habitId, HabitDTO hDTO) {
        try {
            Habit result = HabitRequestSender.put(habitId, hDTO);
            HabitManager.update(result);
            return result;
        } catch (Exception e) {
            System.out.println("Произошла ошибка при изменении привычки.");
        }
        return null;
    }

    public static void update(Habit h) {
        HabitManager.update(h);
    }
}
