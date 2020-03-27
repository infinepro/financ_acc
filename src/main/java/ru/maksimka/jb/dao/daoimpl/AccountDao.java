package ru.maksimka.jb.dao.daoimpl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.Dao;
import ru.maksimka.jb.dao.entities.AccountEntity;
import ru.maksimka.jb.dao.entities.AccountNamesEntity;
import ru.maksimka.jb.dao.entities.UserEntity;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountDao implements Dao<AccountEntity, Integer> {

    private EntityManager em;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountNamesDao accountNamesDao;

    @Autowired
    public AccountDao(@Qualifier("createEntityManager") EntityManager em) {
        this.em = em;
    }

    @Override
    @Nullable
    public AccountEntity findBy(Integer id) {
        try {
            return (AccountEntity) em.createQuery(
                    "SELECT a FROM AccountEntity a WHERE a.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<AccountEntity> findByAllOnePerson(UserEntity owner) throws QueryException {
        em.clear();
        return em.createQuery("SELECT a FROM AccountEntity a WHERE a.owner = :owner")
                .setParameter("owner", owner)
                .getResultList();
    }

    @Override
    public List<AccountEntity> findByAll() {
        return em.createQuery("SELECT a FROM AccountEntity a").getResultList();
    }

    @Override
    public AccountEntity insert(@NotNull AccountEntity accountEntity) {
        em.getTransaction().begin();
        em.persist(accountEntity);
        em.getTransaction().commit();
        return accountEntity;
    }

    @Override
    public AccountEntity update(AccountEntity accountEntity) throws RecordNotFoundException {
        AccountEntity accountEntityOld = findBy(accountEntity.getId());
        if (accountEntityOld == null) {
            throw new RecordNotFoundException("счет не найден, update failed");
        }
        accountEntityOld
                .setAccountName(accountEntity.getAccountName())
                .setBalance(accountEntity.getBalance());

        em.getTransaction().begin();
        em.merge(accountEntityOld);
        em.getTransaction().commit();
        return accountEntityOld;
    }

    public boolean update(AccountEntity accountEntity, EntityManager em) throws RecordNotFoundException {
        AccountEntity accountEntityOld = findBy(accountEntity.getId());
        if (accountEntityOld == null) {
            throw new RecordNotFoundException("счет не найден, update failed");
        }
        accountEntityOld.setBalance(accountEntity.getBalance());
        em.merge(accountEntityOld);
        return true;
    }

    @Override
    public boolean delete(Integer id) throws RecordNotFoundException {
        em.clear();
        AccountEntity accountEntity = em.find(AccountEntity.class, id);
        if (accountEntity == null) {
            throw new RecordNotFoundException("account not found");
        }

        em.getTransaction().begin();
        em.remove(accountEntity);
        em.getTransaction().commit();

        return true;
    }

}


