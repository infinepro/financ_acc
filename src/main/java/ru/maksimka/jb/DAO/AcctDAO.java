package ru.maksimka.jb.DAO;

import ru.maksimka.jb.DTO.AcctDTO;
import ru.maksimka.jb.containers.Acct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AcctDAO implements DAO<Acct, String> {

    public Acct findBy(Integer id) {

        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement preState =
                    connection.prepareStatement("" +
                            "SELECT * FROM accounts WHERE id = ?");
            preState.setInt(1, id);
            ResultSet resultSet = preState.executeQuery();

            if (resultSet.next()) {
                return new Acct()
                        .setBalance(resultSet.getInt("balance"))
                        .setId(resultSet.getInt("id"))
                        .setTypeAcct(resultSet.getInt("id_category_account"))
                        .setUserName(resultSet.getString("user_name"));
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AcctDTO> findByAll(String login) {

        List<AcctDTO> acctList = new ArrayList<>();

        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement preState =
                    connection.prepareStatement(
                            "SELECT * FROM accounts " +
                                    "JOIN names_accounts na on accounts.id_category_account = na.id WHERE user_name = ?");
            preState.setString(1, login);
            ResultSet resultSet = preState.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    acctList.add(new AcctDTO()
                            .setNameAcct(resultSet.getString("category_account"))
                            .setBalance(resultSet.getInt("balance"))
                            .setId(resultSet.getInt("id")));
                }
            }
            return acctList;
        } catch (Exception e) {
            e.printStackTrace();
            return acctList;
        }
    }

    @Override
    public boolean insert(Acct acct) {

        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement preState =
                    connection.prepareStatement(
                            "INSERT INTO accounts(user_name, id_category_account, balance, date) VALUES (?,?,?, CURRENT_DATE )");
            preState.setString(1, acct.getUserName());
            preState.setInt(2, acct.getTypeAcct());
            preState.setInt(3, acct.getBalance());
            preState.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Acct acct) {

        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement preState =
                    connection.prepareStatement(
                            "UPDATE accounts SET balance = ? WHERE id = ?");
            preState.setInt(1, acct.getBalance());
            preState.setInt(2, acct.getId());
            preState.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Acct acct, Connection connection) {

        try {
            PreparedStatement preState =
                    connection.prepareStatement(
                            "UPDATE accounts SET balance = ? WHERE id = ?");
            preState.setInt(1, acct.getBalance());
            preState.setInt(2, acct.getId());
            preState.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(String integer) {
        return false;
    }
}


