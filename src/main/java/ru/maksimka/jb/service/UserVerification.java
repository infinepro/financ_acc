package ru.maksimka.jb.service;

import ru.maksimka.jb.DAO.Domain.User;
import ru.maksimka.jb.DAO.UserDao;

import java.sql.SQLException;

public class UserVerification {

    public boolean verificationUser(String login, String password) {
        User user;
        UserDao userDao = UserDao.getUserDao();
        try {
            user = userDao.findBy(login);
            if (user.getPassword().equals(password)) {
                return true;
            } else return false;
        } catch (SQLException e) {
            return false;
        }
    }

    // duplicate search
    public boolean findUserByName(String login) {
        UserDao userDao = UserDao.getUserDao();
        try {
            userDao.findBy(login);
            return false;
        } catch (SQLException e) {
            return true;
        }
    }

    public boolean registerUser(String login, String password, String email) {
        User user = new User();
        user.setName(login);
        user.setEmail(email);
        user.setPassword(password);

        UserDao userDao = UserDao.getUserDao();
        try {
            userDao.insert(user);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
