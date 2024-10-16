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

        HabitService habitService = new HabitService(person);
        ArrayList<HabitDto> habits = habitService.getHabits();

        System.out.println("Просмотр привычек:");
        switch (scannerService.menuViewHabits()) {
            case "1": {
                System.out.println("Список всех привычек пользователя");
                habitService.toStringListHabits(habits);
                view();
            }
            case "sort": {
                System.out.println("Список всех привычек пользователя," +
                        "отсортированный по дате создания");
                List<HabitDto> sortHabits = habitService.getSortHabitsByTime(habits);
                habitService.toStringListHabits(sortHabits);
                view();
            }
            case "execute": {
                System.out.println("Список всех привычек пользователя со статусом «Выполнена»");
                String status = StatusType.EXECUTE.name();
                //List<HabitDto> executeHabits = person.getSortHabitsByStatus(status);
                //toStringListHabits(executeHabits);
                view();
            }
            case "no": {
                System.out.println("Список всех привычек пользователя со статусом «Не выполнена»");
                String status = StatusType.NO.name();
                //List<HabitDto> noExecuteHabits = person.getSortHabitsByStatus(status);
                //toStringListHabits(noExecuteHabits);
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
