package ru.ylab.service;

import ru.ylab.dto.Habit;

public class HabitUpdateService {
    private final Habit habit;

    public HabitUpdateService(Habit habit) {
        this.habit = habit;
    }

    public void updateTitle(String title) {
        habit.setTitle(title);
    }

    public void updateText(String text) {
        habit.setText(text);
    }

    public void updateFrequency(String frequency) {
        habit.setFrequency(frequency);
    }
}
