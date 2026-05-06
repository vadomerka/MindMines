package com.example.mindmines.infrastructure;

import android.content.Context;

import com.example.mindmines.db.datasync.CharDataSynchronizer;
import com.example.mindmines.db.datasync.DataSynchronizerManager;
import com.example.mindmines.db.datasync.ExpeditionCharDataSynchronizer;
import com.example.mindmines.db.datasync.ExpeditionDataSynchronizer;
import com.example.mindmines.db.datasync.HabitDataSynchronizer;
import com.example.mindmines.db.datasync.UserStatusSynchronizer;
import com.example.mindmines.models.habits.Habit;
import com.example.mindmines.models.habits.HabitDTO;
import com.example.mindmines.requests.HabitRequestSender;
import com.example.mindmines.services.managers.HabitManager;
import com.example.mindmines.services.managers.UserStatusManager;

public class HabitController {
    private static HabitController instance = null;
    private final Context context;

    private HabitController(Context context) {
        this.context = context;
    }

    public static HabitController getInstance(Context context) {
        if (instance == null) {
            instance = new HabitController(context);
        }
        return instance;
    }

    public Habit add(HabitDTO hDTO) {
        try {
            Habit result = HabitRequestSender.post(hDTO);
            HabitManager.add(result);
//            DataSynchronizerManager.getInstance(instance.context).saveToDB();
            return result;
        } catch (Exception e) {
            System.out.println("Произошла ошибка при добавлении привычки.");
        }
        return null;
    }

    public Habit change(Integer habitId, HabitDTO hDTO) {
        try {
            Habit result = HabitRequestSender.put(habitId, hDTO);
            HabitManager.update(result);
            return result;
        } catch (Exception e) {
            System.out.println("Произошла ошибка при изменении привычки.");
        }
        return null;
    }

    public void delete(Integer habitId) {
        try {
            boolean result = HabitRequestSender.delete(habitId);
            if (result == true) {
                HabitManager.delete(habitId);
//                DataSynchronizerManager.getInstance(instance.context).saveToDB();
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при удалении привычки.");
        }
    }

    public void update(Habit h) {
        HabitManager.update(h);
//        DataSynchronizerManager.getInstance(instance.context).saveToDB();
    }
}
