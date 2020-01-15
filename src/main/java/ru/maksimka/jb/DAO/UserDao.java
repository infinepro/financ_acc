package ru.maksimka.jb.DAO;

import ru.maksimka.jb.containers.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO implements DAO<User, String> {

    private static UserDAO userDao;

    @Override
    public User findBy(String name) throws SQLException {
        User user = null;
        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "SELECT * FROM users WHERE name = ? ");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("Пользователь не найден, неверный логин");
                return new User();
            }

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
        }
        return null;
    }


    public List<User> findByAll() {
        return null;
    }

    @Override
    public boolean insert(User user) throws SQLException {
        User tmpUser = user;
        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO users (name, password, email) VALUES  (?, ?, ?) ");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();
            return true;
        }
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(String name) {
        return false;
    }
}
