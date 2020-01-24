package ru.maksimka.jb.DAO;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.DTO.TransactionDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



@Service
public class TransactionDAO implements DAO<TransactionDTO, Integer> {

    private DataSource dataSource;

    public TransactionDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean addNewTypeTransaction(String type) {

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preState =
                    connection.prepareStatement(
                            "INSERT INTO category_transaction (name_category) VALUES (?) ");
            preState.setString(1, type);
            preState.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insert(TransactionDTO transactionDTO) {
        return false;
    }

    //for transaction from to
    public boolean insert(TransactionDTO transactionDTO, Connection connection) {

        try {
            PreparedStatement preState =
                    connection.prepareStatement(
                            "INSERT INTO transactions (uniq_account_id, sum, date, category) VALUES (?, ?, CURRENT_DATE, ?)");
            preState.setInt(1, transactionDTO.getUniq_account_id());
            preState.setBigDecimal(2, transactionDTO.getSum());
            preState.setInt(3, transactionDTO.getCategory());
            preState.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(TransactionDTO transactionDTO) {
        return false;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }
}
