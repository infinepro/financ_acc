package ru.maksimka.jb.dao.implementations;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.hibernate.QueryException;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.Dao;
import ru.maksimka.jb.entities.AccountEntity;
import ru.maksimka.jb.entities.TransactionCategoriesEntity;
import ru.maksimka.jb.entities.TransactionEntity;
import ru.maksimka.jb.entities.UserEntity;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.List;


@Service
public class AccountDao implements Dao<AccountEntity, Integer> {

    private EntityManager em;

    public AccountDao(EntityManager em) {
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
        return em.createQuery("SELECT a FROM AccountEntity a WHERE a.owner = :owner").setParameter("owner", owner).getResultList();
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
        accountEntityOld.withAccountName(accountEntity.getAccountName())
                .withBalance(accountEntity.getBalance());

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
        AccountEntity accountEntityOld = em.merge(findBy(id));

        if (accountEntityOld == null) {
            throw new RecordNotFoundException("счет не найден, delete failed");
        }
        em.getTransaction().begin();
        em.remove(accountEntityOld);
        em.getTransaction().commit();
        return true;
    }

}


