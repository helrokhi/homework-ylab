package ru.ylab.repository;

import lombok.NoArgsConstructor;
import ru.ylab.config.DriverDB;
import ru.ylab.dto.*;
import ru.ylab.dto.enums.StatusType;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

@NoArgsConstructor
public class HabitHistoryRepository implements DriverDB {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneId.of("Europe/Moscow"));

    public ArrayList<StatusDto> getHistory(Long habitId) {
        ArrayList<StatusDto> history = new ArrayList<>(0);

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            history = selectHistory(habitId, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return history;
    }

    public StatusDto getStatusDtoById(Long statusId) {
        StatusDto statusDto = new StatusDto();

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            statusDto = selectStatusById(statusId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return statusDto;
    }

    public StatusDto createStatus(HabitDto habitDto, RegStatus regStatus) {
        StatusDto statusDto = setStatusDtoByStatus(regStatus);
        Long statusId = 0L;

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            Long lastId = getLastId(connection);

            statusDto.setId(lastId + 1);
            statusDto.setHabitId(habitDto.getId());

            statusId = insertStatus(statusDto, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getStatusDtoById(statusId);
    }

    public StatusDto getLastStatusByHabitId(Long habitId) {
        Long statusId = 0L;

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {

            statusId = selectStatusIdByHabitId(habitId, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getStatusDtoById(statusId);
    }

    private ArrayList<StatusDto> selectHistory(Long habitId, Connection connection) throws SQLException {
        ArrayList<StatusDto> history = new ArrayList<>(0);
        Statement statement = connection.createStatement();
        String sql =
                "SELECT " +
                        "id, " +
                        "habit_id, " +
                        "status, " +
                        "time " +
                        "FROM tracking_habit.habit_history h " +
                        "WHERE h.habit_id = '" + habitId + "'";

        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            StatusDto statusDto = StatusDto.builder()
                    .id(resultSet.getLong(1))
                    .habitId(resultSet.getLong(2))
                    .statusType(StatusType.valueOf(resultSet.getString(3).toUpperCase(Locale.ROOT)))
                    .time(ZonedDateTime.parse(resultSet.getString(4), formatter).toOffsetDateTime())
                    .build();

            history.add(statusDto);
        }
        resultSet.close();
        return history;
    }

    private StatusDto selectStatusById(Long statusId, Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        StatusDto statusDto = new StatusDto();

        Statement statement = connection.createStatement();
        String sqlPerson =
                "SELECT " +
                        "id, " +
                        "habit_id, " +
                        "status, " +
                        "time " +
                        "FROM tracking_habit.habit_history h " +
                        "WHERE h.id = '" + statusId + "'";

        ResultSet resultSet = statement.executeQuery(sqlPerson);
        while (resultSet.next()) {
            statusDto.setId(resultSet.getLong(1));
            statusDto.setHabitId(resultSet.getLong(2));
            statusDto.setStatusType(StatusType.valueOf(resultSet.getString(3).toUpperCase(Locale.ROOT)));
            statusDto.setTime(ZonedDateTime.parse(resultSet.getString(4), formatter).toOffsetDateTime());
        }
        resultSet.close();
        return statusDto;
    }

    private Long getLastId(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String lastIdSql =
                "SELECT id FROM tracking_habit.habit_history ORDER BY id DESC LIMIT 1";

        ResultSet resultSet = statement.executeQuery(lastIdSql);
        long lastId = 0L;
        while (resultSet.next()) {
            lastId = resultSet.getLong(1);
        }
        resultSet.close();
        return lastId;
    }

    private Long insertStatus(StatusDto statusDto, Connection connection) throws SQLException {
        String insertDataSql =
                "INSERT INTO " +
                        "tracking_habit.habit_history " +
                        "(id, " +
                        "habit_id, " +
                        "status, " +
                        "time) " +
                        "VALUES " +
                        "(?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertDataSql);
        preparedStatement.setLong(1, statusDto.getId());
        preparedStatement.setLong(2, statusDto.getHabitId());
        preparedStatement.setString(3, statusDto.getStatusType().name().toUpperCase(Locale.ROOT));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(statusDto.getTime().toLocalDateTime()));

        preparedStatement.executeUpdate();
        return statusDto.getId();
    }

    private Long selectStatusIdByHabitId(Long habitId, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String lastIdSql =
                "SELECT " +
                        "id " +
                        "FROM tracking_habit.habit_history h " +
                        "WHERE h.habit_id = '" + habitId + "' " +
                        "ORDER BY h.time " +
                        "DESC LIMIT 1";

        ResultSet resultSet = statement.executeQuery(lastIdSql);
        long lastId = 0L;
        while (resultSet.next()) {
            lastId = resultSet.getLong(1);
        }
        resultSet.close();
        return lastId;
    }

    private StatusDto setStatusDtoByStatus(RegStatus regStatus) {
        return StatusDto.builder()
                .statusType(regStatus.getStatusType())
                .time(regStatus.getTime())
                .build();
    }
}
