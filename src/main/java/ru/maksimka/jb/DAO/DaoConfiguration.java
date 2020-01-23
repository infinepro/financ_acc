package ru.maksimka.jb.DAO;


import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@ComponentScan
@Configuration
public class DaoConfiguration {

    @Bean
    public DataSource dataSource(Environment env){

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(env.getProperty("jdbcUrl","jdbc:postgresql://localhost:5432/postgres"));
        ds.setUsername(env.getProperty("jdbcUsername","postgres"));
        ds.setPassword(env.getProperty("jdbcPassword","524655"));

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
}
