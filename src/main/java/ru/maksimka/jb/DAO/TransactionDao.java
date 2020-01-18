package ru.maksimka.jb.DAO;

import ru.maksimka.jb.containers.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TransactionDAO implements DAO<Transaction, Integer> {


    public Transaction findBy(Integer integer) {
        return null;
    }

    public boolean addNewTypeTransaction(String type){
        try (Connection connection = DAOFactory.getConnection()){
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO category_transaction (name_category) VALUES (?) ");
            ps.setString(1, type);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Transaction> findByAll() {
        return null;
    }

    @Override
    public boolean insert(Transaction transaction) {
        return false;
    }

    public boolean insert(Transaction transaction, Connection conn) {
        Connection connection = conn;

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO transactions (uniq_account_id, sum, date, category) VALUES (?, ?, CURRENT_DATE, ?)");
            ps.setInt(1, transaction.getUniq_account_id());
            ps.setInt(2, transaction.getSum());
            ps.setInt(3, transaction.getCategory());
            ps.executeUpdate();
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
