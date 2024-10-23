package ru.ylab.controller;

import org.junit.jupiter.api.Test;
import ru.ylab.dto.Database;
import ru.ylab.dto.Habit;
import ru.ylab.dto.Person;
import ru.ylab.dto.User;

import static org.junit.jupiter.api.Assertions.*;
class AuthControllerTest {
    private Database database;
    public void setUp() {
        User user0 = new User("thuel@yahoo.com", "0abC!dFg");
        User user1 = new User("bmetz@gmail.com", "1abC!dFg");
        User user2 = new User("shayne75@fay.com", "2abC!dFg");
        User user3 = new User("iblock@hotmail.com", "3abC!dFg");
        User user4 = new User("ernie04@heller.biz", "4abC!dFg");
        User user5 = new User("beth.kunde@yahoo.com", "5abC!dFg");

        Person person0 = new Person(user0);
        Person person1 = new Person(user1);
        Person person2 = new Person(user2);
        Person person3 = new Person(user3);
        Person person4 = new Person(user4);
        Person person5 = new Person(user5);

        database.addUser(user0);
        database.addPerson(person0);

        database.addUser(user1);
        database.addPerson(person1);

        database.addUser(user2);
        database.addPerson(person2);

        database.addUser(user3);
        database.addPerson(person3);

        database.addUser(user4);
        database.addPerson(person4);

        database.addUser(user5);
        database.addPerson(person5);

        Habit habit0 = new Habit("Habit0", "Text0", "daily");
        Habit habit1 = new Habit("Habit1", "Text1", "daily");
        Habit habit2 = new Habit("Habit2", "Text2", "daily");
        Habit habit3 = new Habit("Habit3", "Text3", "daily");
        Habit habit4 = new Habit("Habit4", "Text4", "daily");

        person0.addHabit(habit0);
        person0.addHabit(habit1);
        person0.addHabit(habit2);
        person0.addHabit(habit3);
        person0.addHabit(habit4);

        person1.addHabit(habit0);
        person1.addHabit(habit1);
        person1.addHabit(habit2);
        person1.addHabit(habit3);
        person1.addHabit(habit4);

    }

    @Test
    void start() {
    }

    @Test
    void login() {
    }
}