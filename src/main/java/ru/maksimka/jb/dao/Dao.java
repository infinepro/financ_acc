package ru.maksimka.jb.dao;

import java.util.List;

public interface Dao<DOMAIN, ID> {
    DOMAIN findById(ID id) throws Exception;

    List<DOMAIN> findByAll() throws Exception;

    DOMAIN insert(DOMAIN domain) throws Exception;

    DOMAIN update(DOMAIN domain) throws Exception;

    boolean delete(ID parameter) throws Exception;

}
