package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.PersonDto;
import ru.ylab.service.ScannerService;

@AllArgsConstructor
public class HabitsController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void habits() {
        AccountController accountController = new AccountController();
        HabitController habitController = new HabitController(person);
        ViewHabitsController viewHabitsController = new ViewHabitsController(person);

        System.out.println("Управление привычками " + person);
        switch (scannerService.menuHabits()) {
            case "1": {
                viewHabitsController.view();
            }
            case "2": {
                habitController.habit();
            }
            case "0": {
                accountController.account(person);
            }
            default:
                accountController.account(person);
        }
    }
}
