package ru.maksimka.jb.dao.daoimpl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.Dao;
import ru.maksimka.jb.dao.entities.AccountEntity;
import ru.maksimka.jb.dao.entities.TransactionEntity;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;


@Service
public class TransactionDao implements Dao<TransactionEntity, Integer> {

    private EntityManager em;

    public TransactionDao(EntityManager em) {
        this.em = em;
    }

    public List<TransactionEntity> findByDate(Integer id) {
        return em.createQuery("SELECT a FROM TransactionEntity a WHERE a.date = :date").getResultList();
    }

    @Override
    @Nullable
    public TransactionEntity findBy(Integer id) {
        try {
            return (TransactionEntity) em.createQuery(
                    "SELECT a FROM TransactionEntity a WHERE a.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<TransactionEntity> findByAll() {
        return em.createQuery("SELECT a FROM TransactionEntity a").getResultList();
    }

    @Override
    public TransactionEntity insert(TransactionEntity transactionEntity) {
        em.getTransaction().begin();
        em.persist(transactionEntity);
        em.getTransaction().commit();
        return transactionEntity;
    }


    //for console, depricated
    @Deprecated
    public boolean insert(TransactionEntity transactionEntity, EntityManager em) {
        em.merge(transactionEntity);
        return true;
    }

    @Override

    public TransactionEntity update(TransactionEntity transactionEntity) throws RecordNotFoundException {
        TransactionEntity transactionEntityOld = findBy(transactionEntity.getId());
        if (transactionEntity == null) {
            throw new RecordNotFoundException("транзакция не найдена, update failed");
        }
        transactionEntityOld.withSum(transactionEntity.getSum())
                .withAccount(transactionEntity.getAccount())
                .withTransactionCategory(transactionEntity.getTransactionCategory());

        em.getTransaction().begin();
        em.merge(transactionEntityOld);
        em.getTransaction().commit();
        return transactionEntityOld;
    }

    @Override
    public boolean delete(Integer id) throws RecordNotFoundException {
        TransactionEntity transactionEntity = em.merge(findBy(id));

        if (transactionEntity == null) {
            throw new RecordNotFoundException("транзакция не найдена, delete failed");
        }
        em.getTransaction().begin();
        em.remove(transactionEntity);
        em.getTransaction().commit();
        return true;
    }

    public boolean delete(Integer id, @NotNull EntityManager em) throws RecordNotFoundException {
        TransactionEntity transactionEntity = em.merge(findBy(id));

        if (transactionEntity == null) {
            throw new RecordNotFoundException("транзакция не найдена, delete failed");
        }

        em.remove(transactionEntity);
        return true;
    }

}
