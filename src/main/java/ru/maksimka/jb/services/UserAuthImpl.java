package ru.maksimka.jb.services;

import ru.maksimka.jb.DAO.UserDAO;
import ru.maksimka.jb.containers.User;
import ru.maksimka.jb.exceptions.UserNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

import java.sql.SQLException;

public class UserAuthImpl implements UserAuth<String, Integer> {

    private final UserDAO userDao;

    public UserAuthImpl() {
        this.userDao = new UserDAO();
    }

    public UserAuthImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean authUser(String login, String password) throws WrongUserPasswordException, UserNotFoundException {
        try {
            User user = userDao.findBy(login);
            if (user.getPassword().equals(password)) {
                return true;
            } else
                throw new WrongUserPasswordException("Неправильный пароль");
        } catch (SQLException e) {
            return false;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean registerUser(String login, String password, String email) {

        User user = new User();
        user.setEmail(email);
        user.setName(login);
        user.setPassword(password);
        try {
            userDao.findBy(login);
        } catch (UserNotFoundException e) {
            return userDao.insert(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
