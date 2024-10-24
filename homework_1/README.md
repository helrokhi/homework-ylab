Описание проекта:

Целью является разработка серверной части многопользовательского приложения для отслеживания привычек.
Приложение должно позволять пользователям регистрироваться, создавать, управлять своими привычками, 
отслеживать их выполнение и анализировать прогресс. 
Бэкэнд будет включать авторизацию, обработку CRUD-операций для привычек, 
работу с базой данных, API для фронтенда и отправку уведомлений.

Для реализации системы использованы: Java 17+.
Сборка Maven.

Приложение консольное.
Данные хранятся в коллекциях (в памяти).
Реализовано авторизацию и аутентификацию пользователей.
Реализовать CRUD (Create, Read, Update, Delete) операции для управления привычками

## Создание контейнера с базой данных PostgreSQL в Docker

После установки приложения Docker в командной строке ввести команду для запуска контейнера с PostgreSQL (образ при необходимости загрузится автоматически) :

    docker run -d --name habitdb -p 5432:5432 -e POSTGRES_USER=admin -e POSTGRES_DB=tracking_habit -e POSTGRES_PASSWORD=11111111 -e PGDATA=/var/lib/postgresql/data/pgdata postgres:16.0-alpine3.18

где

- `db` - имя контейнера;
- `data` - имя volume.

Для проверки работы контейнера можно выполнить следующие команды:

- переход к выполнению команд в запущенном контейнере:

      docker exec -it habitdb bash

- переход в консоль PostgreSQL:

      psql -h localhost -U admin tracking_habit