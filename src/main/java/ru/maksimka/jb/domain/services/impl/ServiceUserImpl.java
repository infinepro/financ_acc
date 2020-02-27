package ru.maksimka.jb.domain.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.maksimka.jb.dao.daoimpl.UserDao;
import ru.maksimka.jb.dao.entities.UserEntity;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

public class ServiceUserImpl extends AbstractService implements ServiceUser{

    @Autowired
    private UserDao userDao;

    public ServiceUserImpl(UserEntity userEntity) {
        super(userEntity);
    }

    @Override
    public boolean changePassword(String newPassword){
        userEntity.setPassword(newPassword);
        try {
            userDao.update(userEntity);
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean changeEmail(String newEmail) {
        userEntity.setEmail(newEmail);
        try {
            userDao.update(userEntity);
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void deleteUser() {
        try {
            userDao.delete(userEntity.getName());
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
        }
    }
}