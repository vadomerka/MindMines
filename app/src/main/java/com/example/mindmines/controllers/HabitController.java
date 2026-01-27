package com.example.mindmines.controllers;

import com.example.mindmines.models.Habit;
import com.example.mindmines.models.dto.HabitDTO;
import com.example.mindmines.requests.HabitRequestSender;
import com.example.mindmines.services.HabitAdderService;

public class HabitController {
    public static void add(HabitDTO hDTO) {
        try {
            Habit result = HabitRequestSender.add(hDTO);
            HabitAdderService.add(result);
        } catch (Exception e) {
            System.out.println("Произошла ошибка при добавлении привычки.");
        }

    }
}
