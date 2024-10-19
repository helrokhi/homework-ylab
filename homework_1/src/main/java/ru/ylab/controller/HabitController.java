package ru.ylab.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.service.HabitService;
import ru.ylab.service.PersonService;
import ru.ylab.service.ScannerService;
import ru.ylab.service.UserService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class HabitController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void habit() {
        HabitService habitService = new HabitService(person);
        System.out.println("Работа с привычкой пользователя " + person);
        ArrayList<HabitDto> habits = habitService.getHabits();
        switch (scannerService.habitManagementMenu()) {
            case "1": {
                System.out.println("Создание новой привычки");
                RegHabit regHabit = scannerService.createRegHabit();
                HabitDto habitDto = habitService.create(regHabit);
                System.out.println("Новая привычка " + habitDto);
                habit();
            }
            case "2": {
                if (habits.isEmpty()) {
                    System.out.println("Обновление невозможно список привычек пуст");
                    habit();
                }
                System.out.println("Обновление привычки");
                habitService.toStringListHabits(habits);
                String index = scannerService.createIndexHabit();
                HabitDto habit = habitService.getHabitByIndex(Long.valueOf(index));
                new HabitUpdateController(habit, person).habitUpdate();
                System.out.println("Обновлена привычка " + habit);
                habit();
            }
            case "delete": {
                if (habits.isEmpty()) {
                    System.out.println("Удаление невозможно список привычек пуст");
                    habit();
                }
                System.out.println("Удаление привычки");
                habitService.toStringListHabits(habits);
                String index = scannerService.createIndexHabit();
                HabitDto habit = habitService.getHabitByIndex(Long.valueOf(index));
                habitService.delete(habit);
                habit();
            }
            case "0": {
                new HabitsController(person).habits();
            }
            default:
                new HabitsController(person).habits();
        }
    }
}
