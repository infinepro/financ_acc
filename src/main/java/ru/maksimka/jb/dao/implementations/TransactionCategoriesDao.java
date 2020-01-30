package ru.maksimka.jb.dao.implementations;

import com.sun.istack.Nullable;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.Dao;
import ru.maksimka.jb.entities.TransactionCategoriesEntity;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Service
public class TransactionCategoriesDao implements Dao<TransactionCategoriesEntity, Integer> {

    private EntityManager em;

    public TransactionCategoriesDao(EntityManager em) {
        this.em = em;
    }

    @Override
    @Nullable
    public TransactionCategoriesEntity findBy(Integer id) {
        try {
            return (TransactionCategoriesEntity) em.createQuery(
                    "SELECT a FROM UserEntity a WHERE a.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<TransactionCategoriesEntity> findByAll() {
        return em.createQuery("SELECT a FROM TransactionCategoriesEntity a").getResultList();
    }

    @Override
    public TransactionCategoriesEntity insert(TransactionCategoriesEntity transactionCategoriesEntity) {

        em.getTransaction().begin();
        em.persist(transactionCategoriesEntity);
        em.getTransaction().commit();
        return transactionCategoriesEntity;
    }

    @Override
    public TransactionCategoriesEntity update(TransactionCategoriesEntity transactionCategoriesEntity)
            throws RecordNotFoundException {

        TransactionCategoriesEntity transactionCategoriesEntityOld = findBy(transactionCategoriesEntity.getId());
        if (transactionCategoriesEntityOld == null) {
            throw new RecordNotFoundException("тип транзакции не найден, update failed");
        }
        transactionCategoriesEntityOld
                .setNameCategory(transactionCategoriesEntity.getNameCategory());

        em.getTransaction().begin();
        em.merge(transactionCategoriesEntityOld);
        em.getTransaction().commit();
        return transactionCategoriesEntityOld;
    }

    @Override
    public boolean delete(Integer id) throws RecordNotFoundException {
        TransactionCategoriesEntity transactionCategoriesEntityyOld = em.merge(findBy(id));

        if (transactionCategoriesEntityyOld == null) {
            throw new RecordNotFoundException("тип транзакции не найден, delete failed");
        }
        em.getTransaction().begin();
        em.remove(transactionCategoriesEntityyOld);
        em.getTransaction().commit();
        return true;
    }
}
