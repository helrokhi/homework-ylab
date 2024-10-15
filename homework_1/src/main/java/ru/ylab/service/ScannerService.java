package ru.ylab.service;

import ru.ylab.dto.Habit;
import ru.ylab.dto.Person;
import ru.ylab.dto.User;

import java.util.Scanner;

public class ScannerService {
    private Scanner scanner;

    public String startMenu() {
        scanner = new Scanner(System.in);
        System.out.println("Приложение для отслеживания привычек\n");
        System.out.println(
                "Для регистрации нового пользователь нажмите 1\n" +
                        "Для авторизации нажмите 2");

        return scanner.nextLine().trim();
    }

    public String menuAccount() {
        scanner = new Scanner(System.in);
        System.out.println(
                "\tУправление пользователем нажмите 1\n" +
                        "\tУправление привычками нажмите 2\n" +
                        "\tОтслеживание выполнения привычек пользователя нажмите 3\n" +
                        "\tСтатистика и аналитика нажмите 4\n" +
                        "\tВыйти из личного кабинета нажмите 0");
        return scanner.nextLine().trim();
    }

    public String userManagementMenu() {
        scanner = new Scanner(System.in);
        System.out.println(
                "\t\t\tИзменить имя нажмите 1\n" +
                        "\t\t\tИзменить email нажмите 2\n" +
                        "\t\t\tИзменить пароль нажмите 3\n" +
                        "\t\t\tУдалить аккаунт напишите delete\n" +
                        "\t\t\tВернуться в личный кабинет нажмите 0");
        return scanner.nextLine().trim();
    }

    public String menuHabits() {
        scanner = new Scanner(System.in);
        System.out.println(
                "\t\t\tПросмотр привычек нажмите 1\n" +
                        "\t\t\tРабота с привычкой нажмите 2\n" +
                        "\t\t\tВернуться в личный кабинет нажмите 0");
        return scanner.nextLine().trim();
    }

    public String menuViewHabits() {
        scanner = new Scanner(System.in);
        System.out.println(
                "\t\t\tСписок всех привычек пользователя нажмите 1\n" +
                        "\t\t\tСписок всех привычек пользователя," +
                        "отсортированный по дате создания, напишите sort\n" +
                        "\t\t\tСписок всех привычек пользователя со статусом «Выполнена» напишите execute\n" +
                        "\t\t\tСписок всех привычек пользователя со статусом «Не выполнена» напишите no\n" +
                        "\t\t\tВернуться в предыдущее меню нажмите 0");
        return scanner.nextLine().trim();
    }

    public String habitManagementMenu (){
        scanner = new Scanner(System.in);
        System.out.println(
                "\t\t\tСоздание привычки нажмите 1\n" +
                        "\t\t\tРедактирование привычки: нажмите 2\n" +
                        "\t\t\tУдаление привычки: напишите delete\n" +
                        "\t\t\tВернуться в предыдущее меню нажмите 0");
        return scanner.nextLine().trim();
    }

    public String menuUpdateHabit() {
        scanner = new Scanner(System.in);
        System.out.println(
                "\t\t\tИзменить название нажмите 1\n" +
                        "\t\t\tИзменить описание нажмите 2\n" +
                        "\t\t\tИзменить частоту нажмите 3\n" +
                        "\t\t\tВернуться в личный кабинет нажмите 0");
       return scanner.nextLine().trim();
    }

    public String trackingHabitsMenu(){
        scanner = new Scanner(System.in);
        System.out.println(
                "\t\t\tОтметить выполнение привычки нажмите 1\n" +
                        "\t\t\tПосмотреть историю выполнения привычки нажмите 2\n" +
                        "\t\t\tСтатистика выполнения привычки за указанный период (день, неделя, месяц) 3\n" +
                        "\t\t\tВернуться в личный кабинет нажмите 0");
        return scanner.nextLine().trim();
    }

    public String statisticsMenu(){
        scanner = new Scanner(System.in);
        System.out.println(
                "\t\t\tПодсчет текущих серий выполнения привычек нажмите 1\n" +
                        "\t\t\tПроцент успешного выполнения привычек за определенный период нажмите 2\n" +
                        "\t\t\tФормирование отчета для пользователя по прогрессу выполнения 3\n" +
                        "\t\t\tВернуться в личный кабинет нажмите 0");
        return scanner.nextLine().trim();
    }

    public User createUser() {
        scanner = new Scanner(System.in);
        System.out.println("Введите e-mail пользователя");
        String email = scanner.nextLine().trim();

        System.out.println("Введите пароль пользователя");
        String password = scanner.nextLine().trim();

        return (!email.isBlank() && !password.isBlank())? new User(email, password) : null;
    }

    public String createIndexHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Укажите индекс привычки");
        return scanner.nextLine().trim();
    }

    public Habit createHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Напишите название привычки");
        String title = scanner.nextLine().trim();
        System.out.println("Напишите описание привычки");
        String text = scanner.nextLine().trim();
        System.out.println("Напишите частоту привычки (ежедневно (daily), " +
                "еженедельно (weekly), по умолчанию единожды (daily)) ");
        String frequency = scanner.nextLine().trim();
        return new Habit(title, text, frequency);
    }

    public String updateNamePerson(Person person) {
        scanner = new Scanner(System.in);
        System.out.println("Изменение имени " + person.getName() + "\n" +
                "Добавить новое имя");
        return scanner.nextLine().trim();
    }

    public String updateEmail(Person person) {
        scanner = new Scanner(System.in);
        System.out.println("Изменение email " + person + "\n" +
                "Добавить новый email");
        return scanner.nextLine().trim();
    }

    public String updatePassword(Person person) {
        scanner = new Scanner(System.in);
        System.out.println("Изменение пароль " + person + "\n" +
                "Добавить новый пароль");
        return scanner.nextLine().trim();
    }

    public String updateTitleHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Напишите новое название привычки");
        return scanner.nextLine().trim();
    }

    public String updateTextHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Напишите новое описание привычки");
        return scanner.nextLine().trim();
    }

    public String updateFrequencyHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Напишите новую частоту привычки (ежедневно (daily), " +
                "еженедельно (weekly), ежемесячно (monthly), " +
                "по умолчанию единожды (once)) ");
        return scanner.nextLine().trim();
    }
}
