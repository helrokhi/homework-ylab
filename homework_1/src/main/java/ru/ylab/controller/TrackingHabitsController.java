package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.service.*;

import java.util.ArrayList;

@AllArgsConstructor
public class TrackingHabitsController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void tracking() {
        System.out.println("\t\tОтслеживание выполнения привычек пользователем " + person);
        AccountController accountController = new AccountController();
        HabitService habitService = new HabitService(person);
        HabitHistoryService habitHistoryService = new HabitHistoryService();

        ArrayList<HabitDto> habits = habitService.getHabits();

        switch (scannerService.trackingHabitsMenu()) {
            case "1": {
                System.out.println("Отметить выполнение привычки");

                if (habits.isEmpty()) {
                    System.out.println("Отметить выполнение невозможно список привычек пуст");
                    tracking();
                }
                habitService.toStringListHabits(habits);
                String index = scannerService.createIndexHabit();
                HabitDto habit = habitService.getHabitByIndex(Long.valueOf(index));
                RegStatus regStatus = new RegStatus("EXECUTE");

                habitHistoryService.createStatus(habit, regStatus);
                System.out.println("Выполнение отмечено " + habit);
                tracking();
            }
            case "2": {
                System.out.println("Посмотреть историю выполнения привычки");
                if (habits.isEmpty()) {
                    System.out.println("Посмотреть историю выполнения привычки" +
                            " невозможно список привычек пуст");
                    tracking();
                }
                habitService.toStringListHabits(habits);
                String index = scannerService.createIndexHabit();
                HabitDto habit = habitService.getHabitByIndex(Long.valueOf(index));
                System.out.println("История привычки " + habit);
                System.out.println(habitHistoryService.getHistory(habit.getId()));
                tracking();
            }
            case "3": {
                System.out.println("Статистика выполнения привычки " +
                        "за указанный период (день, неделя, месяц)");
                //* какой-то код

                tracking();
            }
            case "0": {
                System.out.println("Вернуться в личный кабинет");
                accountController.account(person);
            }
            default:
                accountController.account(person);
        }
    }
}
