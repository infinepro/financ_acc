package ru.maksimka.jb.dao.implementations;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.Dao;
import ru.maksimka.jb.entities.TransactionEntity;

import java.util.List;


@Service
public class TransactionDao implements Dao<TransactionEntity, Object> {

    @Override//NOT IMPLEMENTED
    public TransactionEntity findById(Object o) throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public List<TransactionEntity> findByAll() throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public TransactionEntity insert(TransactionEntity transactionEntity) throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public TransactionEntity update(TransactionEntity transactionEntity) throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public boolean delete(Object parameter) throws Exception {
        return false;
    }
}
