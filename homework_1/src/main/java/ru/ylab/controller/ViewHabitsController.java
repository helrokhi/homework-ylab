package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Habit;
import ru.ylab.dto.Person;
import ru.ylab.dto.enums.StatusType;
import ru.ylab.service.ScannerService;

import java.util.ArrayList;
import java.util.List;

public class ViewHabitsController {
    private final Person person;
    private final Database database;
    private final ScannerService scannerService = new ScannerService();

    public ViewHabitsController(Person person, Database database) {
        this.person = person;
        this.database = database;
    }

    public void view() {
        HabitsController habitsController = new HabitsController(person, database);
        ArrayList<Habit> habits = person.getHabits();

        System.out.println("\t\tПросмотр привычек:");
        switch (scannerService.menuViewHabits()) {
            case "1": {
                System.out.println("\t\t\tСписок всех привычек пользователя");
                person.toStringListHabits(habits);
                view();
            }
            case "sort": {
                System.out.println("\t\t\tСписок всех привычек пользователя," +
                                "отсортированный по дате создания");
                List<Habit> sortHabits = person.getSortHabitsByTime();
                person.toStringListHabits(sortHabits);
                view();
            }
            case "execute": {
                System.out.println("\t\t\tСписок всех привычек пользователя со статусом «Выполнена»");
                String status = StatusType.EXECUTE.name();
                List<Habit> executeHabits = person.getSortHabitsByStatus(status);
                person.toStringListHabits(executeHabits);
                view();
            }
            case "no": {
                System.out.println("\t\t\tСписок всех привычек пользователя со статусом «Не выполнена»");
                String status = StatusType.NO.name();
                List<Habit> noExecuteHabits = person.getSortHabitsByStatus(status);
                person.toStringListHabits(noExecuteHabits);
                view();
            }
            case "0": {
                System.out.println("\t\t\tВернуться в предыдущее меню нажмите 0");
                habitsController.habits();
            }
            default: habitsController.habits();
        }
    }
}
