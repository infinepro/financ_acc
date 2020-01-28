package ru.maksimka.jb.dao.implementations;

import ru.maksimka.jb.dao.Dao;
import ru.maksimka.jb.entities.TransactionCategoriesEntity;

import java.util.List;

public class TransactionCategoriesDao implements Dao<TransactionCategoriesEntity, String> {

    @Override//NOT IMPLEMENTED
    public TransactionCategoriesEntity findById(String nameCategory) throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public List<TransactionCategoriesEntity> findByAll() throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public TransactionCategoriesEntity insert(TransactionCategoriesEntity transactionCategoriesEntity) throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public TransactionCategoriesEntity update(TransactionCategoriesEntity transactionCategoriesEntity) throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public boolean delete(String nameCategory) throws Exception {
        return false;
    }
}
