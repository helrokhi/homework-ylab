package ru.ylab.repository;

import lombok.NoArgsConstructor;
import ru.ylab.config.DriverDB;
import ru.ylab.dto.*;

import java.sql.*;

@NoArgsConstructor
public class PersonRepository implements DriverDB {
    public PersonDto getPersonDto(String email, String password) {
        PersonDto personDto = new PersonDto();

        UserRepository userRepository = new UserRepository();
        UserAuthDto userAuthDto = userRepository.getUserAuthDto(email, password);

        Long userId = userAuthDto.getId();

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            personDto = selectPerson(userId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return personDto;
    }

    public PersonDto getPersonDtoById(Long personId) {
        PersonDto personDto = new PersonDto();

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            personDto = selectPersonById(personId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return personDto;
    }

    public PersonDto createPerson(UserAuthDto userAuthDto, RegPerson regPerson) {
        PersonDto personDto = setPersonDtoByRegPerson(regPerson);
        Long personId = 0L;

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
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
        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            updatePersonDtoName(personId, name, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getPersonDtoById(personId);
    }

    public PersonDto updatePerson(PersonDto personDto) {
        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            updatePersonDto(personDto, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getPersonDtoById(personDto.getId());
    }

    public void deletePerson(PersonDto personDto) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB);
            connection.setAutoCommit(false);

            deletePersonDto(personDto.getId(), connection);
            deleteUserAuthDto(personDto.getUserId(), connection);

            String habitIds = selectHabitIds(personDto.getId(), connection);

            if (!habitIds.isBlank()) {
                deleteHabitDtos(habitIds, connection);
                deleteStatusDtos(habitIds, connection);
            }

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

    private void updatePersonDto(PersonDto personDto, Connection connection) throws SQLException {
        String personId = personDto.getId().toString();
        String name = personDto.getName();
        Statement statement = connection.createStatement();
        String updateDataSql =
                "UPDATE " +
                        "tracking_habit.person p " +
                        "SET name = '" + name + "' " +
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
        StringBuilder sb =new StringBuilder();
        Statement statement = connection.createStatement();
        String sql =
                "SELECT " +
                        "id " +
                        "FROM tracking_habit.habit h " +
                        "WHERE h.person_id = '" + personId + "'";

        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
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
