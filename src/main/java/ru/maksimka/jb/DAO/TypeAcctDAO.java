package ru.maksimka.jb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeAcctDAO implements DAO<String, Integer> {

    public String findBy(Integer parameter) throws SQLException {

        try (Connection connection = DAOFactory.getConnection()) {

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

    public List<String> findByAll(){
        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM names_accounts");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> listTypeAccts = new ArrayList<>();
            while (resultSet.next()) {
                listTypeAccts.add(resultSet.getString("category_account"));
            }
            return listTypeAccts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(String type) {
        try(Connection connection = DAOFactory.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO names_accounts(category_account) VALUES (?)");
            preparedStatement.setString(1, type);
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
