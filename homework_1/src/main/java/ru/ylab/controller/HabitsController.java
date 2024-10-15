package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Person;
import ru.ylab.service.ScannerService;

public class HabitsController {
    private final Person person;
    private final ScannerService scannerService = new ScannerService();
    private final HabitController habitController;
    private final AccountController accountController;

    public HabitsController(Person person, Database database) {
        this.person = person;
        accountController = new AccountController(person, database);
        habitController = new HabitController(person, database);
    }

    public void habits() {
        System.out.println("\t\tУправление привычками " + person);
        switch (scannerService.menuHabits()) {
            case "1": {
                person.getAllHabits();
                habits();
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
