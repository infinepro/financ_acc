package ru.maksimka.jborn.DAO;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory {

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
