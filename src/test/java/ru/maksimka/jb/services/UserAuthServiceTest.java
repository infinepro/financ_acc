package ru.maksimka.jb.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.maksimka.jb.dao.implementations.UserDao;
import ru.maksimka.jb.entities.UserDataSet;
import ru.maksimka.jb.exceptions.LoginBusyException;
import ru.maksimka.jb.exceptions.UserNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthServiceTest {


    private UserAuthService subj;
    private UserDataSet testUserDataSet;
    private UserDao userDao;
    private String login;
    private String password;
    private String email;

    @Before
    public void setUp() {
        testUserDataSet = new UserDataSet();
        testUserDataSet.setPassword("password");
        testUserDataSet.setName("login");
        userDao = mock(UserDao.class);
        subj = new UserAuthService(userDao);
        login = "login";
        password = "password";
        email = "123@mail.ru";
    }

    @Test
    public void authUser_if_login_found_and_password_ok() throws Exception {
        when(userDao.findBy(login)).thenReturn(testUserDataSet);
        assertNotEquals(subj.authUser(login, password), new Integer(-1));
    }

    @Test(expected = WrongUserPasswordException.class)
    public void authUser_if_login_found_and_wrong_password() throws Exception {
        testUserDataSet.setPassword("wrong password");
        when(userDao.findBy(login)).thenReturn(testUserDataSet);
        assertNotEquals(subj.authUser(login, password), new Integer(-1));
    }

    @Test//(expected = UserNotFoundException.class)
    public void authUser_if_login_not_found() throws Exception {
        testUserDataSet.setName("wrong name");
        when(userDao.findBy(login)).thenThrow(UserNotFoundException.class);
        assertEquals(subj.authUser(login, password), new Integer(-1));
    }

    @Test
    public void registerUser_if_login_is_busy() {
        try {
            when(userDao.findBy("login")).thenReturn(testUserDataSet);
            assertFalse(subj.registerUser(login, password, email) != -1);
        } catch (Exception e) {
            System.out.println("ТЕСТ registerUser_if_login_is_busy провалился");
        }


    }

    @Test
    public void registerUser_if_all_right() {
        try {
            when(userDao.findBy(login)).thenThrow(UserNotFoundException.class);
            when(userDao.insert(anyObject())).thenReturn(true);
            assertTrue(subj.registerUser(login, password, email) != -1);
        } catch (UserNotFoundException | LoginBusyException e) {
            e.printStackTrace();
        }
    }
}
