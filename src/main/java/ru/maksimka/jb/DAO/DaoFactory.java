package ru.maksimka.jb.DAO;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactory {

    private static DataSource dataSource;

    private static DataSource getDataSource() {
        if (dataSource == null) {
            HikariDataSource ds = new HikariDataSource();
            //ds.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
            ds.setJdbcUrl(System.getProperty("jdbcUrl","jdbc:postgresql://localhost:5432/postgres"));
            //ds.setUsername("postgres");
            ds.setUsername(System.getProperty("jdbcUsername","postgres"));
            //ds.setPassword("524655");
            ds.setPassword(System.getProperty("jdbcPassword","524655"));

            dataSource = ds;

            initDataBase();
        }

        return dataSource;
    }

    private static void initDataBase() {
        try {
            DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
            Liquibase liquibase = new Liquibase(
                    "liquibase.xml",
                    new ClassLoaderResourceAccessor(),
                    database
            );

            liquibase.update(new Contexts());
        } catch (SQLException | LiquibaseException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

}
