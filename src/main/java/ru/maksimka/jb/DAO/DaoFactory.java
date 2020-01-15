package ru.maksimka.jb.DAO;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactory {

    private static DataSource dataSource;

    private static DataSource getDataSource() {
        if (dataSource == null) {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
            ds.setUsername("postgres");
            ds.setPassword("524655");

            dataSource = ds;
        }

        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

}
