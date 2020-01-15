package ru.maksimka.jb.services;

import ru.maksimka.jb.DAO.UserDAO;
import ru.maksimka.jb.containers.User;

import java.sql.SQLException;

public class UserAuthImpl implements UserAuth<String, Integer> {

    private UserDAO userDao;

    public UserAuthImpl() {
        userDao = new UserDAO();
    }

    @Override
    public boolean authUser(String login, String password) {
        User user;
        try {
            user = userDao.findBy(login);
            if (user != null) {
                if (user.getPassword() == password) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean registerUser(String login, String password, String email) {

        User user = new User();
        user.setEmail(email);
        user.setName(login);
        user.setPassword(password);
        userDao.insert(user);
        return true;

    }
}
