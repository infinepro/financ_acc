package ru.maksimka.jb.dao.implementations;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.Dao;
import ru.maksimka.jb.entities.AccountNamesEntity;

import java.util.List;


@Service
public class AccountNamesDao implements Dao<AccountNamesEntity, String> {

    @Override//NOT IMPLEMENTED
    public AccountNamesEntity findById(String accountName) throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public List<AccountNamesEntity> findByAll() throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public AccountNamesEntity insert(AccountNamesEntity accountNamesEntity) throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public AccountNamesEntity update(AccountNamesEntity accountNamesEntity) throws Exception {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public boolean delete(String accountName) throws Exception {
        return false;
    }

}
