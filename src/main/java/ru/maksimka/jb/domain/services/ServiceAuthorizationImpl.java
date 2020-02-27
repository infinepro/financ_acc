package ru.maksimka.jb.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.domain.converters.to_entity_impl.UserToEntityConverter;
import ru.maksimka.jb.dao.entities.UserEntity;
import ru.maksimka.jb.dao.daoimpl.*;
import ru.maksimka.jb.domain.dto.UserDto;
import ru.maksimka.jb.domain.services.assistants.PasswordEncoder;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.NotAuthorizedException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

@Service
public class ServiceAuthorizationImpl implements ServiceAuthorization {

    private UserDao userDao;
    private UserEntity userEntity;
    private PasswordEncoder encoder;

    @Autowired
    public ServiceAuthorizationImpl(UserDao userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    private void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public MainService getService() throws NotAuthorizedException {
        if (userEntity == null) {
            throw new NotAuthorizedException();
        }

        return new MainServiceImpl(userEntity);
    }

    @Override
    public void registration(UserDto userDto) throws AlreadyExistsException {
        userDto.setPassword(encoder.encript(userDto.getPassword()));
        userDao.insert(new UserToEntityConverter().convert(userDto));
    }

    @Override
    public void checkUser(UserDto userDto)
            throws RecordNotFoundException, WrongUserPasswordException {
        userDto.setPassword(encoder.encript(userDto.getPassword()));
        UserEntity userEntity = userDao.findBy(userDto.getName());
        if (userEntity == null) {
            throw new RecordNotFoundException("такого логина несуществует");
        } else if (!userEntity.getPassword().equals(userDto.getPassword())) {
            throw new WrongUserPasswordException("неверный пароль или логин");
        } else {
            setUserEntity(userEntity);
        }

    }

}
