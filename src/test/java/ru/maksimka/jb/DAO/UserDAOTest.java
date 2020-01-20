package ru.maksimka.jb.DAO;


import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import ru.maksimka.jb.containers.User;
import ru.maksimka.jb.exceptions.UserNotFoundException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {

    private UserDAO subj;
    private String testName;
    private User user;

    @Bean("dataSourceH2")
    public DataSource dataSourceHdb(Environment env){
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(env.getProperty("jdbcUrl","jdbc:h2:mem:testDB"));
        ds.setUsername(env.getProperty("jdbcUsername","test"));
        ds.setPassword(env.getProperty("jdbcPassword",""));

        return ds;
    }

    @Bean
    public Liquibase liquibase(DataSource dataSource) throws Exception{
        DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
        Liquibase liquibase = new Liquibase(
                "liquibase.xml",
                new ClassLoaderResourceAccessor(),
                database);

        liquibase.update(new Contexts());
        return liquibase;
    }

    @Before
    public void setUp(){
        ApplicationContext contextTest = new AnnotationConfigApplicationContext("ru.maksimka.jb");

        subj = new UserDAO(contextTest.getBean(DataSource.class, "dataSourceH2" ));

        try (Connection connection = contextTest.getBean(DataSource.class, "dataSourceH2").getConnection()) {
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
