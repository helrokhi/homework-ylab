package ru.ylab.repository;

import lombok.NoArgsConstructor;
import ru.ylab.dto.*;

import java.sql.*;
import java.util.ArrayList;

@NoArgsConstructor
public class PersonRepository {
    public PersonDto getPersonDtoByEmail(String email, String password) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        PersonDto personDto = new PersonDto();

        UserRepository userRepository = new UserRepository();
        UserAuthDto userAuthDto = userRepository.getUserAuthDto(email, password);

        Long userId = userAuthDto.getId();

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            personDto = selectPerson(userId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return personDto;
    }

    public PersonDto getPersonDtoById(Long personId) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        PersonDto personDto = new PersonDto();

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            personDto = selectPersonById(personId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return personDto;
    }

    public PersonDto createPerson(UserAuthDto userAuthDto, RegPerson regPerson) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        PersonDto personDto = setPersonDtoByRegPerson(regPerson);
        Long personId = 0L;

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            Long lastId = getLastId(connection);

            personDto.setId(lastId + 1);
            personDto.setUserId(userAuthDto.getId());

            personId = insertPerson(personDto, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getPersonDtoById(personId);
    }

    public PersonDto updatePersonName(Long personId, String name) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            updatePersonDtoName(personId, name, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getPersonDtoById(personId);
    }

    public void deletePerson(PersonDto personDto) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, password1);
            connection.setAutoCommit(false);

            deletePersonDto(personDto.getId(), connection);
            deleteUserAuthDto(personDto.getUserId(), connection);

            String habitIds = selectHabitIds(personDto.getId(), connection);

            deleteHabitDtos(habitIds, connection);
            deleteStatusDtos(habitIds, connection);

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

    private PersonDto selectPerson(Long userId, Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        PersonDto personDto = new PersonDto();
        Statement statement = connection.createStatement();
        String sqlPerson =
                "SELECT " +
                        "id, " +
                        "name, " +
                        "is_blocked " +
                        "FROM tracking_habit.person p " +
                        "WHERE p.user_id = '" + userId + "'";

        ResultSet resultSet = statement.executeQuery(sqlPerson);
        while (resultSet.next()) {
            personDto.setId(resultSet.getLong(1));
            personDto.setUserId(userId);
            personDto.setName(resultSet.getString(2));
            personDto.setIsBlocked(resultSet.getBoolean(3));
        }
        resultSet.close();
        return personDto;
    }

    private PersonDto selectPersonById(Long personId, Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        PersonDto personDto = new PersonDto();
        Statement statement = connection.createStatement();
        String sqlPerson =
                "SELECT " +
                        "id, " +
                        "user_id, " +
                        "name, " +
                        "is_blocked " +
                        "FROM tracking_habit.person p " +
                        "WHERE p.id = '" + personId + "'";

        ResultSet resultSet = statement.executeQuery(sqlPerson);
        while (resultSet.next()) {
            personDto.setId(resultSet.getLong(1));
            personDto.setUserId(resultSet.getLong(2));
            personDto.setName(resultSet.getString(3));
            personDto.setIsBlocked(resultSet.getBoolean(4));
        }
        resultSet.close();
        return personDto;
    }

    private void updatePersonDtoName(Long personId, String name, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String updateDataSql =
                "UPDATE " +
                        "tracking_habit.person p " +
                        "SET name = " +
                        "'" + name + "' " +
                        "WHERE p.id = '" + personId + "'";

        statement.execute(updateDataSql);
    }

    private void deletePersonDto(Long personId, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String deleteDataSql =
                "DELETE " +
                        "FROM tracking_habit.person p " +
                        "WHERE p.id = '" + personId + "'";

        statement.execute(deleteDataSql);
    }

    private void deleteUserAuthDto(Long userId, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String deleteDataSql =
                "DELETE " +
                        "FROM tracking_habit.user u " +
                        "WHERE u.id = '" + userId + "'";

        statement.execute(deleteDataSql);
    }

    private void deleteHabitDtos(String habitIds, Connection connection) throws SQLException {
        System.out.println("String " + habitIds);
        Statement statement = connection.createStatement();
        String deleteDataSql =
                "DELETE " +
                        "FROM tracking_habit.habit h " +
                        "WHERE h.id IN (" + habitIds + ")";

        statement.execute(deleteDataSql);
    }

    private void deleteStatusDtos(String habitIds, Connection connection) throws SQLException {
        System.out.println("String " + habitIds);
        Statement statement = connection.createStatement();
        String deleteDataSql =
                "DELETE " +
                        "FROM tracking_habit.habit_history h " +
                        "WHERE h.habit_id IN (" + habitIds + ")";

        statement.execute(deleteDataSql);
    }

    private Long getLastId(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String lastIdSql =
                "SELECT id FROM tracking_habit.person ORDER BY id DESC LIMIT 1";

        ResultSet resultSet = statement.executeQuery(lastIdSql);
        long lastId = 0L;
        while (resultSet.next()) {
            lastId = resultSet.getLong(1);
        }
        resultSet.close();
        return lastId;
    }

    private String selectHabitIds(Long personId, Connection connection) throws SQLException {
        ArrayList<Long> habitIds = new ArrayList<>(0);
        StringBuilder sb =new StringBuilder();
        Statement statement = connection.createStatement();
        String sql =
                "SELECT " +
                        "id " +
                        "FROM tracking_habit.habit h " +
                        "WHERE h.person_id = '" + personId + "'";

        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            habitIds.add(resultSet.getLong(1));
            sb.append(!sb.isEmpty()? ", " : "")
                    .append("'")
                    .append(resultSet.getLong(1))
                    .append("'");
        }
        resultSet.close();
        return sb.toString();
    }

    private Long insertPerson(PersonDto personDto, Connection connection) throws SQLException {
        String insertDataSql =
                "INSERT INTO " +
                        "tracking_habit.person " +
                        "(id, " +
                        "user_id, " +
                        "name, " +
                        "is_blocked) " +
                        "VALUES " +
                        "(?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertDataSql);
        preparedStatement.setLong(1, personDto.getId());
        preparedStatement.setLong(2, personDto.getUserId());
        preparedStatement.setString(3, personDto.getName());
        preparedStatement.setBoolean(4, personDto.getIsBlocked());
        preparedStatement.executeUpdate();

        return personDto.getId();
    }

    private PersonDto setPersonDtoByRegPerson(RegPerson regPerson) {
        return PersonDto.builder()
                .name(regPerson.getName())
                .isBlocked(regPerson.getIsBlocked())
                .build();
    }
}
