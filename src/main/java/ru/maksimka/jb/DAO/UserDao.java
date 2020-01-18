package ru.maksimka.jb.DAO;

import ru.maksimka.jb.containers.User;
import ru.maksimka.jb.exceptions.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements DAO<User, String> {

    public User findBy(String name) throws SQLException, UserNotFoundException {

        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement preState =
                    connection.prepareStatement(
                            "SELECT * FROM users WHERE name = ? ");
            preState.setString(1, name);
            ResultSet resultSet = preState.executeQuery();

            if (resultSet.next()) {
                return new User()
                        .setId(resultSet.getInt("id"))
                        .setEmail(resultSet.getString("email"))
                        .setName(resultSet.getString("name"))
                        .setPassword(resultSet.getString("password"));
            } else {
                throw new UserNotFoundException();
            }
        }
    }

    @Override
    public boolean insert(User user) {

        try (Connection connection = DAOFactory.getConnection()) {
            try {
                if (findBy(user.getName()) != null) {
                    return false;
                }
            } catch (UserNotFoundException e) {
                //ignore
            }

            PreparedStatement preState =
                    connection.prepareStatement(
                            "INSERT INTO users (name, password, email) VALUES  (?, ?, ?) ");
            preState.setString(1, user.getName());
            preState.setString(2, user.getPassword());
            preState.setString(3, user.getEmail());
            preState.execute();
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
