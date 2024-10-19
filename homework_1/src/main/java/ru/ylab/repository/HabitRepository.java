package ru.ylab.repository;

import lombok.NoArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.dto.enums.Frequency;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

@NoArgsConstructor
public class HabitRepository {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneId.of("Europe/Moscow"));

    public ArrayList<HabitDto> getHabits(Long personId)  {
        ArrayList<HabitDto> habitDtos = new ArrayList<>(0);
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            habitDtos = selectHabits(personId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return habitDtos;
    }

    public HabitDto createHabit(PersonDto personDto, RegHabit regHabit) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";
        HabitDto habitDto = setHabitDtoByRegHabit(regHabit);
        Long habitId = 0L;

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            Long lastId = getLastId(connection);

            habitDto.setId(lastId + 1);
            habitDto.setPersonId(personDto.getId());

            habitId = insertHabit(habitDto, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getHabitDtoById(habitId);
    }

    public HabitDto updateHabitTitle(Long habitId, String title) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            updateHabitDtoTitle(habitId, title, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getHabitDtoById(habitId);
    }

    public HabitDto updateHabitText(Long habitId, String text) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            updateHabitDtoText(habitId, text, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getHabitDtoById(habitId);
    }

    public HabitDto updateHabitFrequency(Long habitId, String frequency) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            updateHabitDtoFrequency(habitId, frequency, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getHabitDtoById(habitId);
    }

    public HabitDto getHabitDtoById(Long habitId) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        HabitDto habitDto = new HabitDto();

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            habitDto = selectHabitById(habitId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return habitDto;
    }

    public void deleteHabit(Long habitId) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password1);
            connection.setAutoCommit(false);

            deleteHabitDto(habitId, connection);
            deleteStatusDto(habitId, connection);

            connection.commit();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private ArrayList<HabitDto> selectHabits(Long personId, Connection connection) throws SQLException {
        ArrayList<HabitDto> habitDtos = new ArrayList<>(0);
        Statement statement = connection.createStatement();
        String sql =
                "SELECT " +
                        "id, " +
                        "title, " +
                        "text, " +
                        "time, " +
                        "frequency " +
                        "FROM tracking_habit.habit h " +
                        "WHERE h.person_id = '" + personId + "'";

        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            HabitDto habitDto = HabitDto.builder()
                    .id(resultSet.getLong(1))
                    .personId(personId)
                    .title(resultSet.getString(2))
                    .text(resultSet.getString(3))
                    .time(ZonedDateTime.parse(resultSet.getString(4), formatter).toOffsetDateTime())
                    .frequency(Frequency.valueOf(resultSet.getString(5).toUpperCase(Locale.ROOT)))
                    .build();
            habitDtos.add(habitDto);
        }
        resultSet.close();
        return habitDtos;
    }

    private HabitDto selectHabitById(Long habitId, Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        HabitDto habitDto = new HabitDto();

        Statement statement = connection.createStatement();
        String sqlPerson =
                "SELECT " +
                        "id, " +
                        "person_id, " +
                        "title, " +
                        "text, " +
                        "time, " +
                        "frequency " +
                        "FROM tracking_habit.habit h " +
                        "WHERE h.id = '" + habitId + "'";

        ResultSet resultSet = statement.executeQuery(sqlPerson);
        while (resultSet.next()) {
            habitDto.setId(resultSet.getLong(1));
            habitDto.setPersonId(resultSet.getLong(2));
            habitDto.setTitle(resultSet.getString(3));
            habitDto.setText(resultSet.getString(4));
            habitDto.setTime(ZonedDateTime.parse(resultSet.getString(5), formatter).toOffsetDateTime());
            habitDto.setFrequency(Frequency.valueOf(resultSet.getString(6).toUpperCase(Locale.ROOT)));
        }
        resultSet.close();
        return habitDto;
    }

    private Long getLastId(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String lastIdSql =
                "SELECT id FROM tracking_habit.habit ORDER BY id DESC LIMIT 1";

        ResultSet resultSet = statement.executeQuery(lastIdSql);
        long lastId = 0L;
        while (resultSet.next()) {
            lastId = resultSet.getLong(1);
        }
        resultSet.close();
        return lastId;
    }

    private Long insertHabit(HabitDto habitDto, Connection connection) throws SQLException {
        System.out.println("HabitDto "  + habitDto);
        String insertDataSql =
                "INSERT INTO " +
                        "tracking_habit.habit " +
                        "(id, " +
                        "person_id, " +
                        "title, " +
                        "text, " +
                        "time, " +
                        "frequency) " +
                        "VALUES " +
                        "(?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertDataSql);
        preparedStatement.setLong(1, habitDto.getId());
        preparedStatement.setLong(2, habitDto.getPersonId());
        preparedStatement.setString(3, habitDto.getTitle());
        preparedStatement.setString(4, habitDto.getText());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(habitDto.getTime().toLocalDateTime()));
        preparedStatement.setString(6, habitDto.getFrequency().name().toUpperCase(Locale.ROOT));

        preparedStatement.executeUpdate();
        return habitDto.getId();
    }

    private void updateHabitDtoTitle(Long habitId, String title, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String updateDataSql =
                "UPDATE " +
                        "tracking_habit.habit h " +
                        "SET title = " +
                        "'" + title + "' " +
                        "WHERE h.id = '" + habitId + "'";

        ResultSet resultSet = statement.executeQuery(updateDataSql);
        resultSet.close();
    }

    private void updateHabitDtoText(Long habitId, String text, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String updateDataSql =
                "UPDATE " +
                        "tracking_habit.habit h " +
                        "SET text = " +
                        "'" + text + "' " +
                        "WHERE h.id = '" + habitId + "'";

        ResultSet resultSet = statement.executeQuery(updateDataSql);
        resultSet.close();
    }

    private void updateHabitDtoFrequency(Long habitId, String frequency, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String updateDataSql =
                "UPDATE " +
                        "tracking_habit.habit h " +
                        "SET frequency = " +
                        "'" + frequency + "' " +
                        "WHERE h.id = '" + habitId + "'";

        ResultSet resultSet = statement.executeQuery(updateDataSql);
        resultSet.close();
    }

    private void deleteHabitDto(Long habitId, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String deleteDataSql =
                "DELETE " +
                        "FROM tracking_habit.habit h " +
                        "WHERE h.id = '" + habitId + "'";

        statement.execute(deleteDataSql);
    }

    private void deleteStatusDto(Long habitId, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String deleteDataSql =
                "DELETE " +
                        "FROM tracking_habit.habit_history h " +
                        "WHERE h.habit_id = '" + habitId + "'";

        statement.execute(deleteDataSql);
    }

    private HabitDto setHabitDtoByRegHabit(RegHabit regHabit) {
        return HabitDto.builder()
                .title(regHabit.getTitle())
                .text(regHabit.getText())
                .frequency(regHabit.getFrequency())
                .time(regHabit.getTime())
                .build();
    }
}
