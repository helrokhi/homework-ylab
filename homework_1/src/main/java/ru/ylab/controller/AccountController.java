package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Person;
import ru.ylab.service.ScannerService;

public class AccountController {
    private final Person person;
    private final Database database;
    private final ScannerService scannerService = new ScannerService();

    public AccountController(Person person, Database database) {
        this.person = person;
        this.database = database;
    }

    public void account() {
        AuthController authController = new AuthController(database);
        UserController userController = new UserController(person, database);
        HabitsController habitsController = new HabitsController(person, database);
        TrackingHabitsController trackingHabitsController =
                new TrackingHabitsController(person, database);
        StatisticsController statisticsController = new StatisticsController(person, database);

        System.out.println("Добро пожаловать в личный кабинет пользователь: " + person);
        switch (scannerService.menuAccount()) {
            case "0":
                System.out.println("Вы вышли из личного кабинета");
                authController.start();
            case "1":
                userController.user();
            case "2":
                habitsController.habits();
            case "3":
                trackingHabitsController.tracking();
            case "4":
                statisticsController.statistics();
            default:
                authController.start();
        }
    }
}
