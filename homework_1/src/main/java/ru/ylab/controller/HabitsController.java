package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.PersonDto;
import ru.ylab.service.ScannerService;
import ru.ylab.service.UserService;

@AllArgsConstructor
public class HabitsController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void habits() {
        UserService userService = new UserService();
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
            case "0": userService.account(person);
            default: userService.account(person);
        }
    }
}
