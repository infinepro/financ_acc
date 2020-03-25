package ru.maksimka.jb.dao.daoimpl;

import com.sun.istack.Nullable;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.Dao;
import ru.maksimka.jb.dao.entities.AccountNamesEntity;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;


@Service
public class AccountNamesDao implements Dao<AccountNamesEntity, Integer> {

    private EntityManager em;

    public AccountNamesDao(EntityManager em) {
        this.em = em;
    }

    @Override
    @Nullable
    public AccountNamesEntity findBy(Integer id) {
        try {
            return (AccountNamesEntity) em.createQuery(
                    "SELECT a FROM AccountNamesEntity a WHERE a.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<AccountNamesEntity> findByAll() {
        return em.createQuery("SELECT a FROM AccountNamesEntity a").getResultList();
    }

    @Override
    public AccountNamesEntity insert(AccountNamesEntity accountNamesEntity) throws AlreadyExistsException  {

        List<AccountNamesEntity> list;
        list = findByAll();
        for (AccountNamesEntity ac : list) {
            if (ac.getAccountName().equals(accountNamesEntity.getAccountName())) {
                throw new AlreadyExistsException("счет с таким названием уже имеется в базе, insert failed");
            }
        }
        System.out.println(accountNamesEntity.getAccountName());
        em.getTransaction().begin();
        em.persist(accountNamesEntity);
        em.getTransaction().commit();
        return accountNamesEntity;
    }

    @Override
    public AccountNamesEntity update(AccountNamesEntity accountNamesEntity) throws RecordNotFoundException {
        AccountNamesEntity accountNamesEntityOld = findBy(accountNamesEntity.getId());
        if (accountNamesEntityOld == null) {
            throw new RecordNotFoundException("данный тип счета не найден, update failed");
        }
        accountNamesEntityOld.withAccountName(accountNamesEntityOld.getAccountName());

        em.getTransaction().begin();
        em.merge(accountNamesEntityOld);
        em.getTransaction().commit();
        return accountNamesEntityOld;
    }

    @Override
    public boolean delete(Integer id) throws RecordNotFoundException {
        AccountNamesEntity accountNamesEntityOld = em.merge(findBy(id));

        if (accountNamesEntityOld == null) {
            throw new RecordNotFoundException("данный тип счета не найден, delete failed");
        }
        em.getTransaction().begin();
        em.remove(accountNamesEntityOld);
        em.getTransaction().commit();
        return true;
    }

}
