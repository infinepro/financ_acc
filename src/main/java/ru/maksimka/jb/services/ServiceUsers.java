package ru.maksimka.jb.services;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.converters.to_dto_impl.AccountNameToDtoConverter;
import ru.maksimka.jb.converters.to_dto_impl.AccountToDtoConverter;
import ru.maksimka.jb.converters.to_dto_impl.TransactionCategoryToDtoConverter;
import ru.maksimka.jb.converters.to_entity_impl.UserToEntityConverter;
import ru.maksimka.jb.dao.implementations.*;
import ru.maksimka.jb.dto.*;
import ru.maksimka.jb.entities.AccountEntity;
import ru.maksimka.jb.entities.AccountNamesEntity;
import ru.maksimka.jb.entities.UserEntity;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.NotAuthorizedException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;
import sun.plugin2.gluegen.runtime.StructAccessor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceUsers implements Services {

    private UserDao userDao;
    private AccountDao accountDao;
    private AccountNamesDao accountNamesDao;
    private TransactionDao transactionDao;
    private TransactionCategoriesDao transactionCategoriesDao;

    private UserEntity userEntity;
    private List<AccountEntity> list;

    public ServiceUsers(UserDao userDao, AccountDao accountDao,
                        AccountNamesDao accountNamesDao,
                        TransactionDao transactionDao,
                        TransactionCategoriesDao transactionCategoriesDao) {

        this.userDao = userDao;
        this.accountDao = accountDao;
        this.accountNamesDao = accountNamesDao;
        this.transactionDao = transactionDao;
        this.transactionCategoriesDao = transactionCategoriesDao;
    }

    private void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
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
    public boolean changeEmail(String newEmail) throws NotAuthorizedException {
        checkUserEntityForNull();
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
    public void deleteUser() throws NotAuthorizedException {
        checkUserEntityForNull();
        try {
            userDao.delete(userEntity.getName());
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public List<AccountDto> getAllAccounts() throws NotAuthorizedException {
        checkUserEntityForNull();
        return accountDao.findByAllOnePerson(this.userEntity)
                .stream()
                .map(new AccountToDtoConverter()::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountNameDto> getAllAccountNames() {
        return accountNamesDao.findByAll()
                .stream()
                .map(new AccountNameToDtoConverter()::convert)
                .collect(Collectors.toList());
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
    public void deleteAccount(Integer accountNameId) throws RecordNotFoundException {
        accountDao.delete(accountNameId);
    }

    @Override
    public void addNewAccountName(String accountName) throws AlreadyExistsException {
        AccountNamesEntity accountNamesEntity = new AccountNamesEntity().withAccountName(accountName);
        accountNamesDao.insert(accountNamesEntity);
    }

    @Override
    public void deleteAccountName(Integer accountNameId) throws RecordNotFoundException {
        accountNamesDao.delete(accountNameId);
    }

    @Override
    public List<TransactionCategoryDto> getAllTransactionCategory() {
        List<TransactionCategoryDto> list = transactionCategoriesDao.findByAll()
                .stream()
                .map(new TransactionCategoryToDtoConverter()::convert)
                .collect(Collectors.toList());
        return list;
    }

    @Override///
    public TransactionDto addNewTransaction(Integer typeId, Integer accountId, String sum) {
        return null;
    }

    @Override////
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
