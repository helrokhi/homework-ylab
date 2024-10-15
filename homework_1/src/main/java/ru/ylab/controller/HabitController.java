package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Habit;
import ru.ylab.dto.Person;
import ru.ylab.service.HabitService;
import ru.ylab.service.ScannerService;

import java.util.ArrayList;

public class HabitController {
    private final Person person;
    private final Database database;
    private final ScannerService scannerService = new ScannerService();
    private final HabitService habitService;

    public HabitController(Person person, Database database) {
        this.person = person;
        this.database = database;
        habitService = new HabitService(person);
    }

    public void habit() {
        System.out.println("\t\tРабота с привычкой пользователя " + person);
        ArrayList<Habit> habits = person.getHabits();
        switch (scannerService.habitManagementMenu()) {
            case "1": {
                System.out.println("Создание новой привычки");
                Habit habit = scannerService.createHabit();
                habitService.create(habit);
                System.out.println("Новая привычка " + habit);
                habit();
            }
            case "2": {
                if (habits.isEmpty()) {
                    System.out.println("Обновление невозможно список привычек пуст");
                    habit();
                }
                System.out.println("Обновление привычки");
                person.toStringListHabits(habits);
                Habit habit = getHabitByIndex();
                new HabitUpdateController(habit, person, database).habitUpdate();
                System.out.println("Обновлена привычка " + habit);
                habit();
            }
            case "delete": {
                if (habits.isEmpty()) {
                    System.out.println("Удаление невозможно список привычек пуст");
                    habit();
                }
                System.out.println("Удаление привычки");
                person.toStringListHabits(habits);
                Habit habit = getHabitByIndex();
                habitService.delete(habit);
                habit();
            }
            case "0": {
                new HabitsController(person, database).habits();
            }
            default:
                new HabitsController(person, database).habits();
        }
    }

    private Habit getHabitByIndex() {
        String index = scannerService.createIndexHabit();
        Habit habit = null;
        try {
            habit = habitService.getHabitByIndex(index);
        } catch (Exception ex) {
            System.out.println("Такой привычки нет.");
            habit();
        }
        return habit;
    }
}
