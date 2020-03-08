package ru.maksimka.jb.domain.services.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.daoimpl.UserDao;
import ru.maksimka.jb.dao.entities.UserEntity;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

@Service
public class WebServiceUser {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder encoder;

    public void changePassword(String username, String newPassword, String oldPassword) throws WrongUserPasswordException {
        UserEntity userEntity = userDao.findBy(username);
        if (userEntity != null) {
            if (encoder.matches(oldPassword, userEntity.getPassword())) {
                userEntity.setPassword(encoder.encode(newPassword));
                try {
                    userDao.update(userEntity);
                } catch (RecordNotFoundException e) {
                    e.printStackTrace();
                }
            } else throw new WrongUserPasswordException("bad password");
        }
    }

    public void changeEmail(String username, String email) {

    }
}
