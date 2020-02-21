package ru.maksimka.jb.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.domain.converters.to_entity_impl.UserToEntityConverter;
import ru.maksimka.jb.dao.entities.UserEntity;
import ru.maksimka.jb.dao.daoimpl.*;
import ru.maksimka.jb.domain.dto.UserDto;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.NotAuthorizedException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

@Service
public class AuthService implements Auth {

    private UserDao userDao;
    private UserEntity userEntity;

    @Autowired
    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Services getService() throws NotAuthorizedException {
        if (userEntity == null) {
            throw new NotAuthorizedException();
        }

        return new ServiceUsers(userEntity);
    }

    private void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public void registration(String login, String email, String password) throws AlreadyExistsException {
        UserDto userDto = new UserDto().withName(login).withEmail(email).withPassword(password);
        userDao.insert(new UserToEntityConverter().convert(userDto));
    }

    @Override
    public AuthStatus signIn(String login, String password)
            throws RecordNotFoundException, WrongUserPasswordException {

        UserEntity userEntity = userDao.findBy(login);
        if (userEntity == null) {
            throw new RecordNotFoundException("такого логина несуществует");
        } else if (!userEntity.getPassword().equals(password)) {
            throw new WrongUserPasswordException("неверный пароль или логин");
        } else {
            setUserEntity(userEntity);
            return AuthStatus.AUTH;
        }

    }

}
