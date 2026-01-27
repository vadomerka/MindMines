package com.example.mindmines.services.factories;

import com.example.mindmines.models.Habit;
import com.example.mindmines.models.dto.HabitDTO;
import com.example.mindmines.models.enums.HabitType;
import com.example.mindmines.services.repositories.HabitRepository;

import java.time.OffsetDateTime;
import java.util.OptionalInt;

public class HabitFactory {
    private static OptionalInt rm = HabitRepository.getAll().stream().mapToInt(Habit::getHabitId).max();
    private static int localId = rm.isPresent() ? rm.getAsInt() : 0;

    public static HabitDTO createDTO(Integer userId, String title, String desc, Float frequency, Integer priority, Integer difficulty, HabitType hType) {
        return new HabitDTO(userId, title, desc, frequency, priority, difficulty, hType);
    }

    public static HabitDTO createDTO(Integer userId) {
        return new HabitDTO(userId,
                "Название привычки",
                "Описание привычки",
                1.0f / 24,
                1,
                1,
                HabitType.GOOD);
    }

    public static Habit createFromDTO(HabitDTO dto) {
        return new Habit(
                ++localId,
                dto.getUserId(),
                dto.getTitle(),
                dto.getDescription(),
                OffsetDateTime.now(),
                dto.getCheckingFrequency(),
                dto.getPriority(),
                dto.getDifficulty(),
                dto.getType(),
                OffsetDateTime.MIN,
                0,
                0);
    }
}
