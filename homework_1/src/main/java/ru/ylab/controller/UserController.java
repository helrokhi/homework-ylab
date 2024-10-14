package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Person;
import ru.ylab.service.ScannerService;
import ru.ylab.service.UserService;

import java.util.Scanner;

public class UserController {
    private final Person person;
    private final Database database;

    private final ScannerService scannerService = new ScannerService();
    private final UserService userService;

    public UserController(Person person, Database database) {
        this.person = person;
        this.database = database;
        userService = new UserService(person, database);
    }

    public void user() {
        System.out.println("\t\tУправление пользователем " + person);

        AccountController accountController = new AccountController(person, database);
        AuthController authController = new AuthController(database);

        switch (scannerService.userManagementMenu()) {
            case "1": {
                String name = scannerService.updateNamePerson(person);
                userService.updateName(name);
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
            case "delete": {
                userService.delete();
                authController.start();
                user();
            }
            case "0": {
                accountController.account();
            }
            default:
                accountController.account();
        }
    }
}
