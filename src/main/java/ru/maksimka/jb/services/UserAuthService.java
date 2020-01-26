package ru.maksimka.jb.services;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.DAO.UserDAO;
import ru.maksimka.jb.DTO.UserDTO;
import ru.maksimka.jb.exceptions.LoginBusyException;
import ru.maksimka.jb.exceptions.UserNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

import java.sql.SQLException;

@Service
public class UserAuthService {

    private final UserDAO userDao;

    public UserAuthService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public Integer authUser(String login, String password) throws WrongUserPasswordException {

        try {
            UserDTO userDTO = userDao.findBy(login);
            if (userDTO != null) {
                if (userDTO.getPassword().equals(password)) {
                    return userDTO.getId();
                } else throw new WrongUserPasswordException("Неправильный пароль");
            } else return -1;
        } catch (SQLException e) {
            return -1;
        } catch (UserNotFoundException e) {
            System.err.println("Неправильный логин");
            return -1;
        }
    }


    public Integer registerUser(String login, String password, String email) throws LoginBusyException {

        UserDTO userDTO = new UserDTO()
                .withName(login)
                .withPassword(password)
                .withEmail(email);

        try {
            try {
                if (userDao.findBy(login) != null) {
                    throw new LoginBusyException("\n\tЛогин занят, придумайте другой");
                }
            } catch (UserNotFoundException e) {
                if (userDao.insert(userDTO)) {
                    return userDao.findBy(login).getId();
                }
                return -1;
            }

        } catch (SQLException | UserNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }
}
