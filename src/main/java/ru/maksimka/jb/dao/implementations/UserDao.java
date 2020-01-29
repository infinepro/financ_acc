package ru.maksimka.jb.dao.implementations;

import com.sun.istack.Nullable;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.Dao;
import ru.maksimka.jb.entities.UserEntity;
import ru.maksimka.jb.exceptions.LoginIsBusyException;
import ru.maksimka.jb.exceptions.UserNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import java.util.List;

import static ru.maksimka.jb.SpringContext.getContext;

@Service
public class UserDao implements Dao<UserEntity, String> {

    private EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<UserEntity> findByAll() throws Exception {
        return em.createQuery("SELECT a FROM UserEntity a").getResultList();
    }

    @Override
    @Nullable
    public UserEntity findBy(String login) {
        try {
            return (UserEntity) em.createQuery(
                    "SELECT a FROM UserEntity a WHERE a.name = :name")
                    .setParameter("name", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public UserEntity insert(UserEntity userEntity) throws LoginIsBusyException {
        //if login is busy
        if (findBy(userEntity.getName()) != null) {
            throw new LoginIsBusyException("логин занят другим пользователем, insert failed");
        }
        //insert
        EntityManager em = getContext().getBean(EntityManager.class);
        em.getTransaction().begin();
        em.persist(userEntity);
        em.getTransaction().commit();
        return userEntity;
    }

    @Override
    public UserEntity update(UserEntity userEntity) throws UserNotFoundException {
        //if login is busy
        UserEntity userEntityOld = findBy(userEntity.getName());
        if (userEntityOld == null) {
            throw new UserNotFoundException("пользователь не найден, update failed");
        }
        //update
        userEntityOld.setPassword(userEntity.getPassword());
        userEntityOld.setEmail(userEntity.getEmail());
        em.getTransaction().begin();
        em.merge(userEntityOld);
        em.getTransaction().commit();
        return userEntityOld;
    }

    @Override
    public boolean delete(String login) throws UserNotFoundException {
        UserEntity userEntityOld = em.merge(findBy(login));

        if (userEntityOld == null) {
            throw new UserNotFoundException("пользователь не найден, delete failed");
        }
        em.getTransaction().begin();
        em.remove(userEntityOld);
        em.getTransaction().commit();
        return true;
    }
}
