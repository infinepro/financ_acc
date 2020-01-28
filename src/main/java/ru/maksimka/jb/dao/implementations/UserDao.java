package ru.maksimka.jb.dao.implementations;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.Dao;
import ru.maksimka.jb.entities.UserEntity;
import ru.maksimka.jb.exceptions.UserNotFoundException;

import javax.persistence.EntityManager;

import java.util.List;

import static ru.maksimka.jb.SpringContext.getContext;

@Service
public class UserDao implements Dao<UserEntity, String> {

    @Override//NOT IMPLEMENTED
    public List<UserEntity> findByAll() throws Exception {
        return null;
    }

    @Override
    public UserEntity findById(String login) throws UserNotFoundException {
        EntityManager em = getContext().getBean(EntityManager.class);
        UserEntity userEntity = em.find(UserEntity.class, login);
        if (userEntity == null) {
            throw new UserNotFoundException();
        }
        return userEntity;
    }

    @Override
    public UserEntity insert(UserEntity userEntity) {
        //if login is busy
        try {
            if (findById(userEntity.getName()) != null) {
                return null;
            }
        } catch (UserNotFoundException e) {
            //ignore
        }
        //insert
        EntityManager em = getContext().getBean(EntityManager.class);
        em.getTransaction().begin();
        em.merge(userEntity);
       // em.persist(userDataSet);
        em.getTransaction().commit();
        return userEntity;
    }

    @Override//NOT IMPLEMENTED
    public UserEntity update(UserEntity userEntity) {
        return null;
    }

    @Override//NOT IMPLEMENTED
    public boolean delete(String login) {
        return false;
    }
}
