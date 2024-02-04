package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getConnectionBD();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Users (\n" +
                "  id MEDIUMINT  NOT NULL AUTO_INCREMENT,\n" +
                "  name varchar(30) NOT NULL,\n" +
                "  lastName varchar(30) NOT NULL,\n" +
                "  age int NOT NULL,\n" +
                "  PRIMARY KEY (id)\n" +
                ")\n");) {


            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS users";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(dropTable);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Ошибка, не получилось удалить таблицу");
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO users (name, lastName, age) " + "VALUES(?, ?, ?)";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(saveUser);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка, не удалось сохранить юзера");
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String delete = "DELETE FROM users WHERE ID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Ошибка, не удалось удалить юзера по id");
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        String getAll = "select * from users";

        List<User> users = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getInt("age"));
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка, не удалось вывести всех юзеров");
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String deleteAllTable = "TRUNCATE table users";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteAllTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка, не удалось отчистить таблицу");
            throw new RuntimeException(e);
        }


    }
}
