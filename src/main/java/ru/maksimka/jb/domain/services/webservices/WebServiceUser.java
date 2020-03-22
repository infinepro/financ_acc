package ru.maksimka.jb.domain.services.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.daoimpl.AccountDao;
import ru.maksimka.jb.dao.daoimpl.UserDao;
import ru.maksimka.jb.dao.entities.AccountEntity;
import ru.maksimka.jb.dao.entities.UserEntity;
import ru.maksimka.jb.domain.converters.to_dto_impl.AccountToDtoConverter;
import ru.maksimka.jb.domain.dto.AccountDto;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebServiceUser {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private PasswordEncoder encoder;

    private @Nullable
    UserEntity findUserByName(String username) {
        return userDao.findBy(username);
    }

    public void changePassword(String username, String newPassword, String oldPassword) throws WrongUserPasswordException {
        UserEntity userEntity = findUserByName(username);
        if (userEntity != null) {
            if (encoder.matches(oldPassword, userEntity.getPassword())) {
                userEntity.setPassword(encoder.encode(newPassword));
                try {
                    userDao.update(userEntity);
                } catch (RecordNotFoundException e) {
                    e.printStackTrace();
                }
            } else throw new WrongUserPasswordException("wrong password");
        }
    }

    public void changeEmail(String username, String password, String email) throws WrongUserPasswordException {
        UserEntity userEntity = findUserByName(username);
        if (userEntity != null) {
            if (encoder.matches(password, userEntity.getPassword())) {
                userEntity.setEmail(email);
                try {
                    userDao.update(userEntity);
                } catch (RecordNotFoundException e) {
                    e.printStackTrace();
                }
            } else throw new WrongUserPasswordException("wrong password");
        }
    }

    public void deleteUser(String username, String password) throws WrongUserPasswordException {
        UserEntity userEntity = findUserByName(username);
        if (userEntity != null) {
            if (encoder.matches(password, userEntity.getPassword())) {
                try {
                    userDao.delete(username);
                } catch (RecordNotFoundException e) {
                    e.printStackTrace();
                }
            } else throw new WrongUserPasswordException("wrong password");
        }
    }

    public List<AccountDto> getAllAccountByNameUser(String username) {
        UserEntity userEntity = findUserByName(username);
        return userEntity.getAccountsList()
                .stream()
                .map(new AccountToDtoConverter()::convert)
                .collect(Collectors.toList());
    }

    public void deleteUserAccountById(Integer id) throws RecordNotFoundException {
        accountDao.delete(id);
    }


}
