package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.dto.enums.StatusType;
import ru.ylab.service.HabitService;
import ru.ylab.service.ScannerService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ViewHabitsController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void view() {
        HabitsController habitsController = new HabitsController(person);

        HabitService habitService = new HabitService();
        ArrayList<HabitDto> habits = habitService.getHabits(person.getId());

        System.out.println("Просмотр привычек:");
        switch (scannerService.menuViewHabits()) {
            case "1": {
                System.out.println("Список всех привычек пользователя");
                habitService.toStringListHabits(habits);
                view();
            }
            case "SORT": {
                System.out.println("Список всех привычек пользователя," +
                        "отсортированный по дате создания");
                List<HabitDto> sortHabits = habitService.getSortHabitsByTime(habits);
                habitService.toStringListHabits(sortHabits);
                view();
            }
            case "EXECUTE": {
                System.out.println("Список всех привычек пользователя со статусом «Выполнена»");
                List<HabitDto> executeHabits = habitService.getSortHabitsByStatus(habits, StatusType.EXECUTE);
                habitService.toStringListHabits(executeHabits);
                view();
            }
            case "NO": {
                System.out.println("Список всех привычек пользователя со статусом «Не выполнена»");
                List<HabitDto> executeHabits = habitService.getSortHabitsByStatus(habits, StatusType.NO);
                habitService.toStringListHabits(executeHabits);
                view();
            }
            case "0": {
                habitsController.habits();
            }
            default:
                habitsController.habits();
        }
    }
}
