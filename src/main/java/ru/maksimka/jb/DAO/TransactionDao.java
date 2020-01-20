package ru.maksimka.jb.DAO;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.containers.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static ru.maksimka.jb.Main.context;


@Service
public class TransactionDAO implements DAO<Transaction, Integer> {

    private DataSource dataSource;

    public TransactionDAO () {
        this.dataSource = context.getBean(DataSource.class);
    }

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
    public boolean insert(Transaction transaction) {
        return false;
    }

    //for transaction from to
    public boolean insert(Transaction transaction, Connection connection) {

        try {
            PreparedStatement preState =
                    connection.prepareStatement(
                            "INSERT INTO transactions (uniq_account_id, sum, date, category) VALUES (?, ?, CURRENT_DATE, ?)");
            preState.setInt(1, transaction.getUniq_account_id());
            preState.setInt(2, transaction.getSum());
            preState.setInt(3, transaction.getCategory());
            preState.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Transaction transaction) {
        return false;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }
}
