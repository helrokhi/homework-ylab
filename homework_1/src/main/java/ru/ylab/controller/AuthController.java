package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Person;
import ru.ylab.dto.User;
import ru.ylab.service.AuthService;
import ru.ylab.service.ScannerService;

public class AuthController {
    private final Database database;
    private final AuthService authService;
    private final ScannerService scannerService = new ScannerService();

    public AuthController(Database database) {
        this.database = database;
        authService = new AuthService(database);
    }

    public void start() {
        Person person = login(scannerService.startMenu());
    }

    public Person login(String query) {
        while (true) {
            switch (query) {
                case "1": {
                    System.out.println("Регистрация пользователя");
                    User user = scannerService.createUser();
                    if (user == null) {
                        System.out.println("Пользователя создать невозможно");
                        start();
                    }
                    if (user != null && !database.isSuchUser(user.getEmail())) {
                        return authService.registration(user);
                    }
                    System.out.println("Пользователь с таким e-mail существует");
                    start();
                }
                case "2": {
                    System.out.println("Авторизация пользователя");
                    User user = scannerService.createUser();
                    if (user != null && database.isSuchUser(user.getEmail())) {
                        return authService.authorization(user);
                    }
                    System.out.println("Пользователя с таким e-mail не существует");
                    start();
                }
                default: {
                    start();
                }
            }
        }
    }
}
