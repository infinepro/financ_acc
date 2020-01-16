package ru.maksimka.jb.DAO;

import ru.maksimka.jb.containers.User;
import ru.maksimka.jb.exceptions.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO implements DAO<User, String> {

    private static UserDAO userDao;

    @Override
    public User findBy(String name) throws SQLException, UserNotFoundException {
        User user;
        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "SELECT * FROM users WHERE name = ? ");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                throw new UserNotFoundException("Пользователь не найден, неверный логин");
            }

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }
        return null;
    }

    public List<User> findByAll() {
        return null;
    }

    @Override
    public boolean insert(User user){
        User tmpUser = user;
        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO users (name, password, email) VALUES  (?, ?, ?) ");
            preparedStatement.setString(1, tmpUser.getName());
            preparedStatement.setString(2, tmpUser.getPassword());
            preparedStatement.setString(3, tmpUser.getEmail());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
