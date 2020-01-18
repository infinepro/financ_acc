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
        try {
            Connection connection = DAOFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT * FROM accounts WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Acct acct = new Acct();
                    acct.setBalance(rs.getInt("balance"));
                    acct.setId(rs.getInt("id"));
                    acct.setTypeAcct(rs.getInt("id_category_account"));
                    acct.setUserName(rs.getString("user_name"));
                    return acct;
                }
            } else return null;



        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                    acct = new AcctDTO(resultSet.getString("category_account"),
                            resultSet.getInt("balance"));
                    acct.setId(resultSet.getInt("id"));
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
            preparedStatement.setInt(2, acct.getTypeAcct());
            preparedStatement.setInt(3, acct.getBalance());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Acct acct) {
        try (Connection connection = DAOFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE accounts SET balance = ? WHERE id = ?");
            preparedStatement.setInt(1, acct.getBalance());
            preparedStatement.setInt(2, acct.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(Acct acct, Connection conn) {
        Connection connection = conn ;
       try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE accounts SET balance = ? WHERE id = ?");
            preparedStatement.setInt(1, acct.getBalance());
            preparedStatement.setInt(2, acct.getId());
            preparedStatement.executeUpdate();
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


