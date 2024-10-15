package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Habit;
import ru.ylab.dto.Person;
import ru.ylab.dto.Status;
import ru.ylab.dto.enums.StatusType;
import ru.ylab.service.HabitService;
import ru.ylab.service.ScannerService;

import java.time.OffsetDateTime;
import java.util.ArrayList;

public class TrackingHabitsController {
    private final Person person;
    private final Database database;
    private final ScannerService scannerService = new ScannerService();

    private final HabitService habitService;

    public TrackingHabitsController(Person person, Database database) {
        this.person = person;
        this.database = database;
        habitService = new HabitService(person);
    }

    public void tracking() {
        System.out.println("\t\tОтслеживание выполнения привычек пользователем " + person);
        AccountController accountController = new AccountController(person, database);

        ArrayList<Habit> habits = person.getHabits();

        switch (scannerService.trackingHabitsMenu()) {
            case "1": {
                System.out.println("Отметить выполнение привычки");

                if (habits.isEmpty()) {
                    System.out.println("Отметить выполнение невозможно список привычек пуст");
                    tracking();
                }
                person.getAllHabits();
                Habit habit = getHabitByIndex();
                int index = habits.indexOf(habit);
                Status status = new Status(StatusType.EXECUTE, OffsetDateTime.now());
                habitService.updateStatusHistory(index, status);
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
                person.getAllHabits();
                Habit habit = getHabitByIndex();
                System.out.println("История привычки " + habit);
                System.out.println(habitService.getHistory(habit));
                tracking();
            }
            case "3": {
                System.out.println("Статистика выполнения привычки " +
                        "за указанный период (день, неделя, месяц)");
                //* какой-то код

                tracking();
            }
            case "0": {
                System.out.println("Вернуться в личный кабинет нажмите 0");
                accountController.account();
            }
            default:
                accountController.account();
        }
    }

    private Habit getHabitByIndex() {
        String index = scannerService.createIndexHabit();
        Habit habit = null;
        try {
            habit = habitService.getHabitByIndex(index);
        } catch (Exception ex) {
            System.out.println("Такой привычки нет.");
            tracking();
        }
        return habit;
    }
}
