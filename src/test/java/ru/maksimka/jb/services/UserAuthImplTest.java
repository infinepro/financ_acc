package ru.maksimka.jb.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.maksimka.jb.DAO.DAO;
import ru.maksimka.jb.DAO.UserDAO;
import ru.maksimka.jb.containers.User;
import ru.maksimka.jb.exceptions.UserNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthImplTest {


    UserAuth subj;
    User testUser;
    UserDAO userDao;
    String login;
    String password;
    String email;

    @Before
    public void setUp() throws Exception {
        testUser = new User();
        testUser.setPassword("password");
        testUser.setName("login");
        userDao = mock(UserDAO.class);
        subj = new UserAuthImpl(userDao);
        login = "login";
        password = "password";
        email = "123@mail.ru";
    }

    @Test
    public void authUser_if_login_found_and_password_ok() throws Exception {
        when(userDao.findBy(login)).thenReturn(testUser);
        assertTrue(subj.authUser(login, password));
    }

    @Test(expected = WrongUserPasswordException.class)
    public void authUser_if_login_found_and_wrong_password() throws Exception{
        testUser.setPassword("wrong password");
        when(userDao.findBy(login)).thenReturn(testUser);
        assertTrue(subj.authUser(login, password));
    }

    @Test//(expected = UserNotFoundException.class)
    public void authUser_if_login_not_found() throws Exception{
        testUser.setName("wrong name");
        when(userDao.findBy(login)).thenThrow(UserNotFoundException.class);
        assertFalse(subj.authUser(login, password));
    }

    @Test
    public void registerUser_if_login_is_busy() {
        try {
            when(userDao.findBy("login")).thenReturn(testUser);
            assertFalse(subj.registerUser(login, password, email));
        } catch (Exception e) {
            System.out.println("ТЕСТ registerUser_if_login_is_busy провалился");
        }


    }

    @Test
    public void registerUser_if_all_right() {
        try {
            when(userDao.findBy(login)).thenThrow(UserNotFoundException.class);
            when(userDao.insert(anyObject())).thenReturn(true);
            subj.registerUser(login, password, email);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
