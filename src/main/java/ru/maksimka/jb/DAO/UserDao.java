package ru.maksimka.jb.DAO;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.DTO.UserDTO;
import ru.maksimka.jb.exceptions.UserNotFoundException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




@Service
public class UserDAO implements DAO<UserDTO, String> {

    private DataSource dataSource;

    public UserDAO (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public UserDTO findBy(String name) throws SQLException, UserNotFoundException {

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preState =
                    connection.prepareStatement(
                            "SELECT * FROM users WHERE name = ? ");
            preState.setString(1, name);
            ResultSet resultSet = preState.executeQuery();

            if (resultSet.next()) {
                return new UserDTO()
                        .withId(resultSet.getInt("id"))
                        .withEmail(resultSet.getString("email"))
                        .withName(resultSet.getString("name"))
                        .withPassword(resultSet.getString("password"));
            } else {
                throw new UserNotFoundException();
            }
        }
    }

    @Override
    public boolean insert(UserDTO userDTO) {

        try (Connection connection = this.dataSource.getConnection()) {
            try {
                if (findBy(userDTO.getName()) != null) {
                    return false;
                }
            } catch (UserNotFoundException e) {
                //ignore
            }

            PreparedStatement preState =
                    connection.prepareStatement(
                            "INSERT INTO users (name, password, email) VALUES  (?, ?, ?) ");
            preState.setString(1, userDTO.getName());
            preState.setString(2, userDTO.getPassword());
            preState.setString(3, userDTO.getEmail());
            preState.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(UserDTO userDTO) {
        return false;
    }

    @Override
    public boolean delete(String name) {
        return false;
    }
}
