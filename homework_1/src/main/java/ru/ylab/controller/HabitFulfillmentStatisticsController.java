package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.PersonDto;
import ru.ylab.service.ScannerService;
@AllArgsConstructor
public class HabitFulfillmentStatisticsController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void fulfillment(HabitDto habitDto) {
        TrackingHabitsController trackingHabitsController =
                new TrackingHabitsController(person);

        System.out.println("Статистика выполнения привычки " + habitDto);
        switch (scannerService.habitFulfillmentStatisticsMenu()) {
            case "DAY": {
                System.out.println("Статистика выполнения привычки " + habitDto + " за день");
                int days = 1;

            }
            case "WEEK": {
                System.out.println("Статистика выполнения привычки " + habitDto + " за неделю");
                int days = 7;

            }
            case "MONTH": {
                System.out.println("Статистика выполнения привычки " + habitDto + " за месяц");
                int days = 30;

            }
            case "0": trackingHabitsController.tracking();
            default: trackingHabitsController.tracking();
        }
    }

}
