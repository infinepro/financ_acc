package ru.maksimka.jb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class TypeAcctDAO implements DAO<String, Integer> {

    @Override
    public boolean insert(String type) {

        try(Connection connection = DAOFactory.getConnection()){
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
