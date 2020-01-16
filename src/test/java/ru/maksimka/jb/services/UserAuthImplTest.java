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

    @Before
    public void setUp() throws Exception {
        testUser = new User();
        testUser.setPassword("password");
        testUser.setName("login");
        userDao = mock(UserDAO.class);
        subj = new UserAuthImpl(userDao);
        login = "login";
        password = "password";
    }

    @Test
    public void authUser_if_login_found() throws Exception {
        when(userDao.findBy(login)).thenReturn(testUser);
        assertTrue(subj.authUser(login, password));
    }

    @Test(expected = WrongUserPasswordException.class)
    public void authUser_if_wrong_password() throws Exception{
        testUser.setPassword("wrong password");
        when(userDao.findBy(login)).thenReturn(testUser);
        assertTrue(subj.authUser(login, password));
    }

    @Test(expected = UserNotFoundException.class)
    public void authUser_if_login_not_found() throws Exception{
        testUser.setName("wrong name");
        when(userDao.findBy(login)).thenReturn(null);
        assertTrue(subj.authUser(login, password));
    }

    @Test
    public void registerUser() {

    }
}
