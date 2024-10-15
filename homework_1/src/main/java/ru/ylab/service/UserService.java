package ru.ylab.service;

import ru.ylab.dto.Database;
import ru.ylab.dto.Person;

public class UserService {
    private final Person person;
    private final Database database;

    public UserService(Person person, Database database) {
        this.person = person;
        this.database = database;
    }

    public void updateName(String name) {
        person.setName(name);
        System.out.println("Имя пользователя изменено " + person);
    }

    public void updateEmail(String email) {
        person.getUser().setEmail(email);
        System.out.println("Email пользователя изменен " + person);
    }

    public void updatePassword(String password) {
        person.getUser().setPassword(password);
        System.out.println("Пароль пользователя изменен " + person);
    }

    public void delete() {
        database.removePerson(person);
        database.removeUser(person.getUser());
        System.out.println("Пользователь удален " + person);
    }
}
