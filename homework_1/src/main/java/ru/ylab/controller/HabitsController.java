package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Person;
import ru.ylab.service.ScannerService;

public class HabitsController {
    private final Person person;
    private final Database database;
    private final ScannerService scannerService = new ScannerService();

    public HabitsController(Person person, Database database) {
        this.person = person;
        this.database = database;
    }

    public void habits() {
        AccountController accountController = new AccountController(person, database);
        HabitController habitController = new HabitController(person, database);
        ViewHabitsController viewHabitsController = new ViewHabitsController(person, database);

        System.out.println("\t\tУправление привычками " + person);
        switch (scannerService.menuHabits()) {
            case "1": {
                viewHabitsController.view();
            }
            case "2": {
                habitController.habit();
            }
            case "0": {
                accountController.account();
            }
            default:
                accountController.account();
        }
    }
}
