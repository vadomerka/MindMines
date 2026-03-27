package com.example.mindmines.infrastructure;

import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.dto.HabitDTO;
import com.example.mindmines.requests.HabitRequestSender;
import com.example.mindmines.services.managers.HabitManager;

public class HabitController {
    public static Habit add(HabitDTO hDTO) {
        try {
            Habit result = HabitRequestSender.add(hDTO);
            HabitManager.add(result);
            return result;
        } catch (Exception e) {
            System.out.println("Произошла ошибка при добавлении привычки.");
        }
        return null;
    }

    public static void update(Habit h) {
        // TODO: здесь можно сделать сохранение изменений на сервере.
        HabitManager.update(h);
    }
}
