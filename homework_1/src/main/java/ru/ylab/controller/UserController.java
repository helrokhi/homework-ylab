package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.PersonDto;
import ru.ylab.service.PersonService;
import ru.ylab.service.ScannerService;
import ru.ylab.service.UserService;

@AllArgsConstructor
public class UserController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void user() {
        System.out.println("\t\tУправление пользователем " + person);

        AccountController accountController = new AccountController();
        AuthController authController = new AuthController();

        UserService userService = new UserService(person);
        PersonService personService = new PersonService(person);

        switch (scannerService.userManagementMenu()) {
            case "1": {
                String name = scannerService.updateNamePerson(person);
                personService.updateName(name);
                user();
            }
            case "2": {
                String email = scannerService.updateEmail(person);
                userService.updateEmail(email);
                user();
            }
            case "3": {
                String password = scannerService.updatePassword(person);
                userService.updatePassword(password);
                user();
            }
            case "DELETE": {
                personService.delete(person);
                authController.start();
            }
            case "0": {
                accountController.account(person);
            }
            default:
                accountController.account(person);
        }
    }
}
