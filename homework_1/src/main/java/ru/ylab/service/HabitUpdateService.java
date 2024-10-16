package ru.ylab.service;

import lombok.AllArgsConstructor;
import ru.ylab.dto.HabitDto;
import ru.ylab.repository.HabitRepository;

@AllArgsConstructor
public class HabitUpdateService {
    private HabitDto habit;

    public void updateTitle(String title) {
        HabitRepository habitRepository = new HabitRepository();
        habitRepository.updateHabitTitle(habit.getId(), title);
    }

    public void updateText(String text) {
        HabitRepository habitRepository = new HabitRepository();
        habitRepository.updateHabitText(habit.getId(), text);
    }

    public void updateFrequency(String frequency) {
        HabitRepository habitRepository = new HabitRepository();
        habitRepository.updateHabitFrequency(habit.getId(), frequency);
    }
}
