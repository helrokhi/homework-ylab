package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.PersonDto;
import ru.ylab.dto.UserAuthDto;
import ru.ylab.dto.enums.Role;
import ru.ylab.service.PersonService;
import ru.ylab.service.ScannerService;
import ru.ylab.service.UserService;

@AllArgsConstructor
public class UserController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void user() {
        System.out.println("\t\tУправление пользователем " + person);

        AuthController authController = new AuthController();

        UserService userService = new UserService();
        PersonService personService = new PersonService(person);

        switch (scannerService.userManagementMenu()) {
            case "1": {
                String name = scannerService.updateNamePerson(person);
                personService.updateName(name);
                user();
            }
            case "2": {
                String email = scannerService.updateEmail(person);
                userService.updateEmail(person, email);
                user();
            }
            case "3": {
                String password = scannerService.updatePassword(person);
                userService.updatePassword(person, password);
                user();
            }
            case "DELETE": {
                personService.delete(person);
                authController.start();
            }
            case "0": userService.account(person);
            default: userService.account(person);
        }
    }
}
