package ru.maksimka.jb.DAO;

import ru.maksimka.jb.containers.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TransactionDAO implements DAO<Transaction, Integer> {
    @Override
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

    @Override
    public boolean update(Transaction transaction) {
        return false;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }
}
