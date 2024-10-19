package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.PersonDto;
import ru.ylab.service.ScannerService;

@AllArgsConstructor
public class StatisticsController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void statistics() {
        System.out.println("Статистика и аналитика пользователя " + person);

        AccountController accountController = new AccountController();
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
            case "0": {
                System.out.println("Вернуться в личный кабинет");
                accountController.account(person);
            }
            default:
                accountController.account(person);
        }
    }
}
