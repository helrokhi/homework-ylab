package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.PersonDto;
import ru.ylab.service.ScannerService;
import ru.ylab.service.UserService;

@AllArgsConstructor
public class StatisticsController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void statistics() {
        System.out.println("Статистика и аналитика пользователя " + person);

        UserService userService = new UserService();
        switch (scannerService.statisticsMenu()) {
            case "1": {
                System.out.println("Подсчет текущих серий выполнения привычек\n");
                //* какой-то код
                statistics();
            }
            case "2": {
                System.out.println("Процент успешного выполнения привычек за определенный период");
                //* какой-то код
                statistics();
            }
            case "3": {
                System.out.println("Формирование отчета для пользователя по прогрессу выполнения");
                //* какой-то код
                statistics();
            }
            case "0": userService.account(person);
            default: userService.account(person);
        }
    }
}
