package ru.maksimka.jb.services;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.configurations.SpringContext;
import ru.maksimka.jb.converters.to_entity_impl.UserToEntityConverter;
import ru.maksimka.jb.dao.implementations.AccountDao;
import ru.maksimka.jb.dao.implementations.AccountNamesDao;
import ru.maksimka.jb.dao.implementations.UserDao;
import ru.maksimka.jb.dto.AccountDto;
import ru.maksimka.jb.dto.TransactionDto;
import ru.maksimka.jb.dto.UserDto;
import ru.maksimka.jb.entities.AccountEntity;
import ru.maksimka.jb.entities.AccountNamesEntity;
import ru.maksimka.jb.entities.UserEntity;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.NotAuthorizedException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServiceUsers implements Services {

    private UserDao userDao;
    private AccountDao accountDao;
    private AccountNamesDao accountNamesDao;

    private UserEntity userEntity;
    private List<AccountEntity> list;

    private ServiceTransactions serviceTransactions;
    private ServiceAccounts serviceAccounts;

    public ServiceUsers(UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    private void setUserEntity(UserEntity userEntity){
        this.userEntity = userEntity;
        this.serviceAccounts = SpringContext.getContext().getBean(ServiceAccounts.class).setUserEntity(userEntity);
        this.serviceTransactions = SpringContext.getContext().getBean(ServiceTransactions.class).setUserEntity(userEntity);
    }

    private void checkUserEntityForNull() throws NotAuthorizedException {
        if (this.userEntity == null) {
            throw new NotAuthorizedException("пользователь неавторизован");
        }
    }

    @Override
    public boolean changePassword(String newPassword) throws NotAuthorizedException {
        checkUserEntityForNull();
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
    public boolean changeEmail(String newEmail) throws NotAuthorizedException{
        checkUserEntityForNull();
        userEntity.setPassword(newEmail);
        try {
            userDao.update(userEntity);
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void deleteUser() throws NotAuthorizedException{
        checkUserEntityForNull();
        try {
            userDao.delete(userEntity.getName());
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public boolean addNewAccount(Integer accNameId, BigDecimal balance) throws NotAuthorizedException {
        checkUserEntityForNull();

        AccountNamesEntity accountNamesEntity = accountNamesDao.findBy(accNameId);
        AccountEntity accountEntity = new AccountEntity()
                .withOwner(this.userEntity)
                .withAccountName(accountNamesEntity)
                .withBalance(balance);
        accountDao.insert(accountEntity);
        return true;
    }

    @Override
    public boolean deleteAccount(Integer id) {
        return false;
    }

    @Override
    public boolean addNewAccountName(String accountName) {
        return false;
    }

    @Override
    public boolean deleteAccountName(String accountName) {
        return false;
    }

    @Override
    public TransactionDto addNewTransaction(Integer typeId, Integer accountId, String sum) {
        return null;
    }

    @Override
    public boolean deleteTransaction(Integer id) {
        return false;
    }



    @Override
    public void registration(String login, String email, String password) throws AlreadyExistsException {
        UserDto userDto = new UserDto().withName(login).withEmail(email).withPassword(password);
        userDao.insert(new UserToEntityConverter().convert(userDto));
    }

    @Override
    public AuthStatus signIn(String login, String password) throws RecordNotFoundException, WrongUserPasswordException {

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
