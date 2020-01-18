package ru.maksimka.jb.DAO;

public interface DAO<DOMAIN, ID> {
    //DOMAIN findBy(ID parameter) throws Exception;
    // List<DOMAIN> findByAll() throws Exception;
    boolean insert(DOMAIN domain) throws Exception;
    boolean update(DOMAIN domain) throws Exception;
    boolean delete(ID parameter) throws Exception;

}
