package ru.maksimka.jb.DAO;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.DTO.AcctDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class AcctDAO implements DAO<AcctDTO, String> {

    private DataSource dataSource;

    public AcctDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public AcctDTO findBy(Integer id) {

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preState =
                    connection.prepareStatement("" +
                            "SELECT * FROM accounts WHERE id = ?");
            preState.setInt(1, id);
            ResultSet resultSet = preState.executeQuery();

            if (resultSet.next()) {
                return new AcctDTO()
                        .withBalance(resultSet.getBigDecimal("balance"))
                        .withId(resultSet.getInt("id"))
                        .withTypeAcctId(resultSet.getInt("id_category_account"))
                        .withUserName(resultSet.getString("user_name"));
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AcctDTO> findByAll(String login) {

        List<AcctDTO> acctList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preState =
                    connection.prepareStatement(
                            "SELECT * FROM accounts " +
                                    "JOIN names_accounts na on accounts.id_category_account = na.id WHERE user_name = ?");
            preState.setString(1, login);
            ResultSet resultSet = preState.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    acctList.add(new AcctDTO()
                            .withNameAcct(resultSet.getString("category_account"))
                            .withBalance(resultSet.getBigDecimal("balance"))
                            .withId(resultSet.getInt("id")));
                }
            }
            return acctList;
        } catch (Exception e) {
            e.printStackTrace();
            return acctList;
        }
    }

    @Override
    public boolean insert(AcctDTO acct) {

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preState =
                    connection.prepareStatement(
                            "INSERT INTO accounts(user_name, id_category_account, balance, date) VALUES (?,?,?, CURRENT_DATE )");
            preState.setString(1, acct.getUserName());
            preState.setInt(2, acct.getTypeAcctId());
            preState.setBigDecimal(3, acct.getBalance());
            preState.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(AcctDTO acct) {

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preState =
                    connection.prepareStatement(
                            "UPDATE accounts SET balance = ? WHERE id = ?");
            preState.setBigDecimal(1, acct.getBalance());
            preState.setInt(2, acct.getId());
            preState.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //for transaction from to
    public boolean update(AcctDTO acct, Connection connection) {

        try {
            PreparedStatement preState =
                    connection.prepareStatement(
                            "UPDATE accounts SET balance = ? WHERE id = ?");
            preState.setBigDecimal(1, acct.getBalance());
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


