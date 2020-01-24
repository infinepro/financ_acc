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

    public boolean authUser(String login, String password) throws WrongUserPasswordException{

        try {
            UserDTO userDTO = userDao.findBy(login);
            if (userDTO != null) {
                if (userDTO.getPassword().equals(password)) {
                    return true;
                } else throw new WrongUserPasswordException("Неправильный пароль");
            } else return false;
        } catch (SQLException e) {
            return false;
        } catch (UserNotFoundException e) {
            System.err.println("Неправильный логин");
            return false;
        }
    }

    public boolean registerUser(String login, String password, String email) throws LoginBusyException {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setName(login);
        userDTO.setPassword(password);
        try {
            try {
                if (userDao.findBy(login) != null) {
                    throw new LoginBusyException("\n\tЛогин занят, придумайте другой");
                }
            } catch (UserNotFoundException e) {
                return userDao.insert(userDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
