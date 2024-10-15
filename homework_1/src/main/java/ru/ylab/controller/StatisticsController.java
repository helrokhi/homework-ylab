package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Person;
import ru.ylab.service.ScannerService;

public class StatisticsController {
    private final Person person;
    private final Database database;
    private final ScannerService scannerService = new ScannerService();

    public StatisticsController(Person person, Database database) {
        this.person = person;
        this.database = database;
    }

    public void statistics(){
        System.out.println("\t\tСтатистика и аналитика пользователя " + person);

        AccountController accountController = new AccountController(person, database);
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
                System.out.println("Вернуться в личный кабинет нажмите 0");
                accountController.account();
            }
            default: accountController.account();
        }
    }
}
