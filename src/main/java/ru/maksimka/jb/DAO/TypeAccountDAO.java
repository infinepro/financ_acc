package ru.maksimka.jb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeAccountDAO implements Dao<String, Integer>{
    @Override
    public String findBy(Integer parameter) throws SQLException {

        try (Connection connection = DaoFactory.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM names_accounts WHERE id = ?");
            preparedStatement.setInt(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String typeAccount = resultSet.getString("category_account");
                return typeAccount;
            }
        }
        return "";
    }

    @Override
    public List<String> findByAll() throws Exception {
        try (Connection connection = DaoFactory.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM names_accounts");

            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> listTypeAccounts = new ArrayList<>();

            while (resultSet.next()) {
               listTypeAccounts.add(resultSet.getString("category_account"));
            }
            return listTypeAccounts;
        }
    }

    @Override
    public boolean insert(String s) throws Exception {
        try(Connection connection = DaoFactory.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO names_accounts(category_account) VALUES (?)");
            preparedStatement.setString(1, s);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
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
