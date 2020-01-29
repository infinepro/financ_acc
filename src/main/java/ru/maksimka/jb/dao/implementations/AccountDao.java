package ru.maksimka.jb.dao.implementations;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.Dao;
import ru.maksimka.jb.entities.AccountEntity;

import java.util.List;


@Service
public class AccountDao implements Dao<AccountEntity, String> {

    @Override//NOT IMPLEMENTED
    public AccountEntity findBy(String accountName) throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public List<AccountEntity> findByAll() throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public AccountEntity insert(AccountEntity accountEntity) throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public AccountEntity update(AccountEntity accountEntity) throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public boolean delete(String accountName) throws Exception {
        return false;
    }
}


