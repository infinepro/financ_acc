package ru.maksimka.jborn.DAO;

import ru.maksimka.jborn.DAO.Domain.Transaction;

import java.util.List;

public class TransactionDao implements Dao<Transaction, Integer> {
    @Override
    public Transaction findBy(Integer integer) {
        return null;
    }

    @Override
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
