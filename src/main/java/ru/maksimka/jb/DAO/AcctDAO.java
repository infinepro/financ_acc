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

    @Override
    public Acct findBy(String id) {
        return null;
    }

    public List<AcctDTO> findByAll(String login) throws Exception {
        List<AcctDTO> acctList = new ArrayList<>();
        AcctDTO acct;

        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "SELECT * FROM accounts " +
                                    "JOIN names_accounts na on accounts.id_category_account = na.id WHERE user_name = ?");
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    acct = new AcctDTO( resultSet.getString("category_account"),
                                        resultSet.getInt("balance"));
                    acctList.add(acct);
                }
            }
            return acctList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Acct acct) {
        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO accounts(user_name, id_category_account, balance, date) VALUES (?,?,?, CURRENT_DATE )");
            preparedStatement.setString(1, acct.getUserName());
            preparedStatement.setInt(2, acct.getId());
            preparedStatement.setInt(3, acct.getBalance());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Acct acct) {
        return false;
    }

    @Override
    public boolean delete(String integer) {
        return false;
    }
}


