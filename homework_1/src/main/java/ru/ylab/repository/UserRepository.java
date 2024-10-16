package ru.ylab.repository;

import lombok.NoArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.dto.enums.Role;

import java.sql.*;
import java.util.Locale;

@NoArgsConstructor
public class UserRepository {
    public UserAuthDto getUserAuthDto(String email, String password) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        UserAuthDto userAuthDto = new UserAuthDto();

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            userAuthDto = selectUser(email, password, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return userAuthDto;
    }

    public UserAuthDto getUserAuthDtoByEmail(String email) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        UserAuthDto userAuthDto = new UserAuthDto();

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            userAuthDto = selectUserByEmail(email, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return userAuthDto;
    }

    public UserAuthDto getUserAuthDtoById(Long userId) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        UserAuthDto userAuthDto = new UserAuthDto();

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            userAuthDto = selectUserById(userId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return userAuthDto;
    }

    public UserAuthDto createUser(RegUser regUser) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";
        Long userId = 0L;

        UserAuthDto userAuthDto = setUserAuthDtoByRegUser(regUser);

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            Long lastId = getLastId(connection);

            userAuthDto.setId(lastId + 1);

            userId = insertUser(userAuthDto, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getUserAuthDtoById(userId);
    }

    public UserAuthDto updateUserEmail(Long userId, String email) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            updateUserAuthDtoEmail(userId, email, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getUserAuthDtoById(userId);
    }

    public UserAuthDto updateUserPassword(Long userId, String name) {
        String url = "jdbc:postgresql://localhost:5432/tracking_habit";
        String user = "admin";
        String password1 = "11111111";

        try (Connection connection = DriverManager.getConnection(url, user, password1)) {
            updateUserAuthDtoPassword(userId, name, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getUserAuthDtoById(userId);
    }

    private UserAuthDto selectUser(String email, String password, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        UserAuthDto userAuthDto = new UserAuthDto();
        String sqlUser =
                "SELECT " +
                        "id, " +
                        "email, " +
                        "password, " +
                        "role " +
                        "FROM tracking_habit.user u " +
                        "WHERE u.email = '" + email + "'" + " AND " + "u.password = '" + password + "'";

        ResultSet resultSet = statement.executeQuery(sqlUser);
        while (resultSet.next()) {

            userAuthDto.setId(resultSet.getLong(1));
            userAuthDto.setEmail(resultSet.getString(2));
            userAuthDto.setPassword(resultSet.getString(3));
            userAuthDto.setRole(Role.valueOf(resultSet.getString(4)));
        }
        resultSet.close();
        return userAuthDto;
    }

    private UserAuthDto selectUserByEmail(String email, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        UserAuthDto userAuthDto = new UserAuthDto();
        String sqlUser =
                "SELECT " +
                        "id, " +
                        "email, " +
                        "password, " +
                        "role " +
                        "FROM tracking_habit.user u " +
                        "WHERE u.email = '" + email + "'";

        ResultSet resultSet = statement.executeQuery(sqlUser);
        while (resultSet.next()) {

            userAuthDto.setId(resultSet.getLong(1));
            userAuthDto.setEmail(resultSet.getString(2));
            userAuthDto.setPassword(resultSet.getString(3));
            userAuthDto.setRole(Role.valueOf(resultSet.getString(4)));
        }
        resultSet.close();
        return userAuthDto;
    }

    private UserAuthDto selectUserById(Long userId, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        UserAuthDto userAuthDto = new UserAuthDto();
        String sqlUser =
                "SELECT " +
                        "id, " +
                        "email, " +
                        "password, " +
                        "role " +
                        "FROM tracking_habit.user u " +
                        "WHERE u.id = '" + userId + "'";

        ResultSet resultSet = statement.executeQuery(sqlUser);
        while (resultSet.next()) {
            userAuthDto.setId(resultSet.getLong(1));
            userAuthDto.setEmail(resultSet.getString(2));
            userAuthDto.setPassword(resultSet.getString(3));
            userAuthDto.setRole(Role.valueOf(resultSet.getString(4)));
        }
        resultSet.close();
        return userAuthDto;
    }

    private Long insertUser(UserAuthDto userAuthDto, Connection connection) throws SQLException {
        String insertDataSql =
                "INSERT INTO " +
                        "tracking_habit.user " +
                        "(id, " +
                        "email, " +
                        "password, " +
                        "role) " +
                        "VALUES " +
                        "(?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertDataSql);
        preparedStatement.setLong(1, userAuthDto.getId());
        preparedStatement.setString(2, userAuthDto.getEmail());
        preparedStatement.setString(3, userAuthDto.getPassword());
        preparedStatement.setString(4, userAuthDto.getRole().name().toUpperCase(Locale.ROOT));
        preparedStatement.executeUpdate();
        return userAuthDto.getId();
    }

    private void updateUserAuthDtoEmail(Long userId, String email, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String updateDataSql =
                "UPDATE " +
                        "tracking_habit.user u " +
                        "SET email = " +
                        "'" + email + "' " +
                        "WHERE u.id = '" + userId + "'";

        statement.execute(updateDataSql);
    }

    private void updateUserAuthDtoPassword(Long userId, String password, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String updateDataSql =
                "UPDATE " +
                        "tracking_habit.user u " +
                        "SET password = " +
                        "'" + password + "' " +
                        "WHERE u.id = '" + userId + "'";

        statement.execute(updateDataSql);
    }

    private Long getLastId(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String lastIdSql =
                "SELECT id FROM tracking_habit.user ORDER BY id DESC LIMIT 1";

        ResultSet resultSet = statement.executeQuery(lastIdSql);
        long lastId = 0L;
        while (resultSet.next()) {
            lastId = resultSet.getLong(1);
        }
        resultSet.close();
        return lastId;
    }

    private UserAuthDto setUserAuthDtoByRegUser(RegUser regUser) {
        return UserAuthDto.builder()
                .email(regUser.getEmail())
                .password(regUser.getPassword())
                .role(regUser.getRole())
                .build();
    }
}
