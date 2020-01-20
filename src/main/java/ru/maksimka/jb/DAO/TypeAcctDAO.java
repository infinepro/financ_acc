package ru.maksimka.jb.DAO;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Service
public class TypeAcctDAO implements DAO<String, Integer> {

    private DataSource dataSource;
    /*
    public TypeAcctDAO(){
        dataSource = context.getBean(DataSource.class);
    }
    */
    //for tests
    public TypeAcctDAO (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean insert(String type) {

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preState =
                    connection.prepareStatement(
                    "INSERT INTO names_accounts(category_account) VALUES (?)");
            preState.setString(1, type);
            preState.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(String s) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Integer parameter) throws Exception {
        return false;
    }
}
