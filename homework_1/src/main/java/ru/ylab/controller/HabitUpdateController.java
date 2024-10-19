package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.service.HabitUpdateService;
import ru.ylab.service.ScannerService;

@AllArgsConstructor
public class HabitUpdateController {
    private HabitDto habit;
    private PersonDto person;

    private final ScannerService scannerService = new ScannerService();

    public void habitUpdate() {
        HabitUpdateService habitUpdateService = new HabitUpdateService(habit);
        System.out.println("Редактирование привычки " + habit);
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
                new HabitController(person).habit();
            }
            default:
                new HabitController(person).habit();
        }
    }
}
