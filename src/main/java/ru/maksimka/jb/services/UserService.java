package ru.maksimka.jb.services;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.converters.to_entity_impl.UserToEntityConverter;
import ru.maksimka.jb.dao.implementations.UserDao;
import ru.maksimka.jb.dto.UserDto;
import ru.maksimka.jb.entities.UserEntity;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

import static ru.maksimka.jb.services.AuthStatus.AUTH;
import static ru.maksimka.jb.services.AuthStatus.NOT_AUTH;

@Service
public class UserService {

    private UserDao userDao;
    private AuthStatus authStatus;
    //private AccountService accountService;
    //private TransactionService transactionService;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
        authStatus = NOT_AUTH;
    }

    public AuthStatus getAuthStatus() {
        return this.authStatus;
    }

    private void setAuthStatus(AuthStatus authStatus) {
        this.authStatus = authStatus;
    }

    //private void initServices(){}

    public void registration(UserDto userDto) throws AlreadyExistsException {
        userDao.insert(new UserToEntityConverter().convert(userDto));
    }

    public boolean signIn(UserDto userDto) throws RecordNotFoundException, WrongUserPasswordException {

        UserEntity userEntity = userDao.findBy(userDto.getName());
        if (userEntity == null) {
            setAuthStatus(NOT_AUTH);
            throw new RecordNotFoundException("такого логина несуществует");
        } else if (!userEntity.getPassword().equals(userDto.getPassword())) {
            setAuthStatus(NOT_AUTH);
            throw new WrongUserPasswordException("неверный пароль или логин");
        } else {
            setAuthStatus(AUTH);
            return true;
        }

    }
}
