package ru.maksimka.jb.services;

import ru.maksimka.jb.DAO.UserDAO;
import ru.maksimka.jb.containers.User;
import ru.maksimka.jb.exceptions.LoginBusyException;
import ru.maksimka.jb.exceptions.UserNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

import java.sql.SQLException;

public class UserAuthService implements UserAuth<String, Integer> {

    private final UserDAO userDao;

    public UserAuthService() {
        this.userDao = new UserDAO();
    }

    public UserAuthService(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean authUser(String login, String password) throws WrongUserPasswordException, UserNotFoundException {
        try {
            User user = userDao.findBy(login);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    return true;
                } else
                    throw new WrongUserPasswordException("Неправильный пароль");
            } else return false;
        } catch (SQLException e) {
            return false;
        } catch (UserNotFoundException e) {
            System.err.println("Неправильный логин");
            return false;
        }
    }

    @Override
    public boolean registerUser(String login, String password, String email) throws LoginBusyException {

        User user = new User();
        user.setEmail(email);
        user.setName(login);
        user.setPassword(password);
        try {
            try {
                if (userDao.findBy(login) != null) {
                    throw new LoginBusyException("\n\tЛогин занят, придумайте другой");
                }
            } catch (UserNotFoundException e) {
                return userDao.insert(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
