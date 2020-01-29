package ru.maksimka.jb.dao;


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

import org.springframework.context.annotation.Bean;
import ru.maksimka.jb.dao.implementations.UserDao;
import ru.maksimka.jb.entities.UserDataSet;
import ru.maksimka.jb.exceptions.UserNotFoundException;

import javax.sql.DataSource;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {

    private UserDao subj;
    private String testName;
    private UserDataSet userDataSet;

    @Bean
    public DataSource getDataSourceHdb() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(System.getProperty("jdbcUrl", "jdbc:h2:mem:testDB"));
        ds.setUsername(System.getProperty("jdbcUsername", "test"));
        ds.setPassword(System.getProperty("jdbcPassword", ""));

        return ds;
    }

    @Bean
    public Liquibase getliquibase(DataSource dataSource) throws Exception {
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
    public void setUp() {

        try {
            getliquibase(getDataSourceHdb());
        } catch (Exception e) {
            e.printStackTrace();
        }

        subj = new UserDao();
        subj.insert(new UserDataSet()
                .withName("test_name")
                .withEmail("test_email")
                .withPassword("test_password"));

        testName = "test_name";
    }

    @Test
    public void findBy_if_user_found() {
        try {
            assertEquals(subj.findBy(testName).getName(), "test_name");
        } catch (UserNotFoundException e) {
        }
    }

    @Test(expected = UserNotFoundException.class)
    public void findBy_if_user_not_found() throws UserNotFoundException {
        subj.findBy("bad_login");
    }

    @Test
    public void insert_if_ok() {
        assertTrue(subj.insert(new UserDataSet()
                .withName("ok_name")
                .withPassword("ok_password")
                .withEmail("ok_email")));
    }

    @Test
    public void insert_if_login_is_busy() {
        assertFalse(subj.insert(new UserDataSet()
                .withName("test_name")
                .withPassword("ok_password")
                .withEmail("ok_email")));
    }
}
