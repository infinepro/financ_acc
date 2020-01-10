package ru.maksimka.jborn.DAO;

import java.sql.SQLException;
import java.util.List;

public interface Dao<DOMAIN, ID> {

    DOMAIN findBy(ID parameter) throws Exception;
    List<DOMAIN> findByAll() throws Exception;
    boolean insert(DOMAIN domain) throws Exception;
    boolean update(DOMAIN domain) throws Exception;
    boolean delete(ID parameter) throws Exception;

}
