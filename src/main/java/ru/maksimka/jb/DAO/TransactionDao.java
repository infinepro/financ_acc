package ru.maksimka.jb.DAO;

import ru.maksimka.jb.containers.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO implements DAO<Transaction, Integer> {

    public boolean addNewTypeTransaction(String type) {

        try (Connection connection = DAOFactory.getConnection()) {
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
