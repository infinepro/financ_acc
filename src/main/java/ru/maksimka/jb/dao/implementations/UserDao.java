package ru.maksimka.jb.dao.implementations;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.Dao;
import ru.maksimka.jb.entities.UserEntity;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import java.util.List;

@Service
public class UserDao implements Dao<UserEntity, String> {

    private EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<UserEntity> findByAll() {
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
    public UserEntity insert(UserEntity userEntity) throws AlreadyExistsException {
        if (findBy(userEntity.getName()) != null) {
            throw new AlreadyExistsException("логин занят другим пользователем, insert failed");
        }
        em.getTransaction().begin();
        em.persist(userEntity);
        em.getTransaction().commit();
        return userEntity;
    }

    @Override
    public UserEntity update(@NotNull UserEntity userEntity) throws RecordNotFoundException {
        UserEntity userEntityOld = findBy(userEntity.getName());
        if (userEntityOld == null) {
            throw new RecordNotFoundException("пользователь не найден, update failed");
        }
        userEntityOld.setPassword(userEntity.getPassword());
        userEntityOld.setEmail(userEntity.getEmail());
        em.getTransaction().begin();
        em.merge(userEntityOld);
        em.getTransaction().commit();
        return userEntityOld;
    }

    @Override
    public boolean delete(String login) throws RecordNotFoundException {
        UserEntity userEntityOld = em.merge(findBy(login));

        if (userEntityOld == null) {
            throw new RecordNotFoundException("пользователь не найден, delete failed");
        }
        em.getTransaction().begin();
        em.remove(userEntityOld);
        em.getTransaction().commit();
        return true;
    }
}
