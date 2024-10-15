package ru.ylab.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Person {
    private final User user;
    private String name;
    private Boolean isBlocked;
    private final ArrayList<Habit> habits;

    public Person(User user) {
        this.user = user;
        name = "";
        isBlocked = false;
        habits = new ArrayList<>(0);
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Habit> getHabits() {
        return habits;
    }

    public Habit getHabitByIndex(int index) {
        return habits.get(index);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public void addHabit(Habit habit) {
        habits.add(habit);
    }

    public void deleteHabit(Habit habit) {
        habits.remove(habit);
    }

    public void updateHabit(int index, Status status) {
        habits.get(index).updateStatusHistory(status);
    }

    public List<Habit> getSortHabitsByTime() {
        return habits
                .stream()
                .sorted(Comparator.comparing(Habit::getTime))
                .collect(Collectors.toList());
    }

    public List<Habit> getSortHabitsByStatus(String status) {
        return habits
                .stream()
                .filter(habit -> habit.getStatus().getStatusType().name().equals(status))
                .collect(Collectors.toList());
    }

    public void toStringListHabits(List<Habit> habits) {
        if (habits.isEmpty()) {
            System.out.println("Нет привычек у пользователя");
        } else {
            System.out.println("\t\tСписок привычек пользователя");
            for (int i = 0; i < habits.size(); i++) {
                System.out.println("\t\t" + i + ": " + habits.get(i));
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Person person = (Person) object;
        return Objects.equals(user, person.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "Person{" +
                "user=" + user.getEmail() +
                ", name='" + name + '\'' +
                ", isBlocked=" + isBlocked +
                ", habits=" + habits +
                '}';
    }
}
