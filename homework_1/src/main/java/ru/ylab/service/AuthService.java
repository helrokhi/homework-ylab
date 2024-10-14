package ru.ylab.service;

import ru.ylab.controller.AccountController;
import ru.ylab.dto.Database;
import ru.ylab.dto.Person;
import ru.ylab.dto.User;

public class AuthService {
    private final Database database;

    public AuthService(Database database) {
        this.database = database;
    }

    public Person registration(User user) {
        database.addUser(user);
        Person person = new Person(user);
        database.addPerson(person);
        new AccountController(person, database).account();
        return person;
    }

    public Person authorization(User user) {
        Person person = database.getPerson(user);
        new AccountController(person, database).account();
        return person;
    }

    public boolean isValidEmail(String email) {
        String regexEmail = "^[a-z0-9._%+\\-]+@[a-z0-9.\\-]+(\\.[a-z]{2,}|\\.xn--[a-z0-9]+)$";
        return email.matches(regexEmail);
    }

    public boolean isValidPassword(String password) {
        String regexPassword = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}";
        return password.matches(regexPassword);
    }

    public boolean isValidUser(User user) {
        return (isValidEmail(user.getEmail()) && isValidPassword(user.getPassword()));
    }
}
