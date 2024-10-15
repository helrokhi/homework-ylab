package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Habit;
import ru.ylab.dto.Person;
import ru.ylab.service.HabitUpdateService;
import ru.ylab.service.ScannerService;

public class HabitUpdateController {
    private final Habit habit;
    private final Person person;
    private final Database database;

    private final ScannerService scannerService = new ScannerService();

    private final HabitUpdateService habitUpdateService;

    public HabitUpdateController(Habit habit, Person person, Database database) {
        this.habit = habit;
        this.person = person;
        this.database = database;
        habitUpdateService = new HabitUpdateService(habit);
    }

    public void habitUpdate() {
        System.out.println("\t\tРедактирование привычки " + habit);
        switch (scannerService.menuUpdateHabit()) {
            case "1": {
                System.out.println("Изменение названия привычки " + habit);
                habitUpdateService.updateTitle(scannerService.updateTitleHabit());
                habitUpdate();
            }
            case "2": {
                System.out.println("Изменение описания привычки " + habit);
                habitUpdateService.updateText(scannerService.updateTextHabit());
                habitUpdate();
            }
            case "3": {
                System.out.println("Изменение частоты привычки " + habit);
                habitUpdateService.updateFrequency(scannerService.updateFrequencyHabit());
                habitUpdate();
            }
            case "0": {
                new HabitController(person, database).habit();
            }
            default:
                new HabitController(person, database).habit();
        }
    }
}
