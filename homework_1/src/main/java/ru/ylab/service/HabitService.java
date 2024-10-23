package ru.ylab.service;

import ru.ylab.dto.Habit;
import ru.ylab.dto.Person;
import ru.ylab.dto.Status;

import java.util.ArrayList;
import java.util.Set;

public class HabitService {
    private final Person person;

    public HabitService(Person person) {
        this.person = person;
    }

    public void create(Habit habit) {
        person.addHabit(habit);
    }

    public Habit getHabitByIndex(String index) {
        return person.getHabitByIndex(Integer.parseInt(index));
    }

    public void delete(Habit habit) {
        person.deleteHabit(habit);
        System.out.println("Привычка удалена " + habit);
    }

    public void updateStatusHistory(int index, Status status) {
        person.updateHabit(index, status);
    }

    public ArrayList<Status> getHistory(Habit habit) {
        int index = person.getHabits().indexOf(habit);
        return person.getHabitByIndex(index).getHistory();
    }
}
