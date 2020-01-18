package ru.maksimka.jb.DAO;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.maksimka.jb.containers.User;
import ru.maksimka.jb.exceptions.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {

    private UserDAO subj;
    private String testName;
    private User user;

    @Before
    public void setUp(){
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDB");
        System.setProperty("jdbcUsername", "test");
        System.setProperty("jdbcPassword","");

        subj = new UserDAO();
        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement testStatement =
                    connection.prepareStatement("" +
                            "INSERT INTO users(name, password, email) VALUES (?, ?, ?)");
            testStatement.setString(1,"test_name");
            testStatement.setString(2,"test_password");
            testStatement.setString(3,"test_email");
            testStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        testName = "test_name";

    }

    @Test
    public void findBy_if_user_found() {
        try {
            assertEquals(subj.findBy(testName).getName(), "test_name" );
        } catch (SQLException | UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = UserNotFoundException.class)
    public void findBy_if_user_not_found() throws UserNotFoundException {
        try {
            subj.findBy("bad_login");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insert_if_ok() {
        assertTrue(subj.insert(new User().
                setName("ok_name").
                setPassword("ok_password").
                setEmail("ok_email")));
    }

    @Test
    public void insert_if_login_is_busy(){
        assertFalse(subj.insert(new User().
                setName("test_name").
                setPassword("ok_password").
                setEmail("ok_email")));
    }
}
