package ru.maksimka.jb.DAO;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {

    UserDAO subj;

    @Before
    public void setUp(){
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDB");
        System.setProperty("jdbcUsername", "test");
        System.setProperty("jdbcPassword","");

        subj = new UserDAO();


    }

    @Test
    public void findBy() {
    }

    @Test
    public void findByAll() {
    }

    @Test
    public void insert() {
    }
}
