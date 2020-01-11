package ru.maksimka.jb.DAO;

import ru.maksimka.jb.DAO.Domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao implements Dao<User, String> {

    private static UserDao userDao;

    private UserDao() {
    }

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

/*
    @Override
   public User findById(Integer id) throws SQLException {
        User user = null;
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "SELECT * FROM users WHERE id = ? ");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
            return user;
        }
    }
*/

    @Override
    public User findBy(String name) throws SQLException {
        User user = null;
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "SELECT * FROM users WHERE name = ? ");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("Пользователь не найден, неверный логин");
                return new User();
            }
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
            return user;
        }
    }

    @Override
    public List<User> findByAll() {
        return null;
    }

    @Override
    public boolean insert(User user) throws SQLException {
        User tmpUser = user;
        try (Connection connection = DaoFactory.getConnection()) {
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
