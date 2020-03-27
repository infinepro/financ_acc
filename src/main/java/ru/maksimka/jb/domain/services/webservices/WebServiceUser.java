package ru.maksimka.jb.domain.services.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.daoimpl.*;
import ru.maksimka.jb.dao.entities.*;
import ru.maksimka.jb.domain.converters.to_dto_impl.AccountNameToDtoConverter;
import ru.maksimka.jb.domain.converters.to_dto_impl.AccountToDtoConverter;
import ru.maksimka.jb.domain.converters.to_dto_impl.TransactionCategoryToDtoConverter;
import ru.maksimka.jb.domain.converters.to_dto_impl.TransactionToDtoConverter;
import ru.maksimka.jb.domain.dto.AccountDto;
import ru.maksimka.jb.domain.dto.AccountNameDto;
import ru.maksimka.jb.domain.dto.TransactionCategoryDto;
import ru.maksimka.jb.domain.dto.TransactionDto;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebServiceUser {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private AccountNamesDao accountNamesDao;
    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private TransactionCategoriesDao transactionCategoriesDao;
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

    public List<AccountNameDto> getAllTypeAccounts() {
        return accountNamesDao.findByAll()
                .stream()
                .map(new AccountNameToDtoConverter()::convert)
                .collect(Collectors.toList());
    }

    public void addNewAccount(String username, BigDecimal balance, Integer typeId) {
        UserEntity userEntity = userDao.findBy(username);
        AccountNamesEntity accountNamesEntity = accountNamesDao.findBy(typeId);
        AccountEntity accountEntity = new AccountEntity()
                .withAccountName(accountNamesEntity)
                .withOwner(userEntity)
                .withBalance(balance);

        accountDao.insert(accountEntity);
    }

    public void addNewTypeAccount(String type) throws AlreadyExistsException {
        AccountNamesEntity accountNamesEntity = new AccountNamesEntity().withAccountName(type);
        accountNamesDao.insert(accountNamesEntity);
    }

    public void changeAccount(AccountDto accountDto) throws RecordNotFoundException {
        //UserEntity userEntity = userDao.findBy(accountDto.getOwner());
        AccountNamesEntity accountNamesEntity = accountNamesDao.findBy(accountDto.getTypeId());
        AccountEntity accountEntity = new AccountEntity()
                .setAccountName(accountNamesEntity)
                .setBalance(accountDto.getBalance())
                .setId(accountDto.getId());

        accountDao.update(accountEntity);
    }

    public void addNewTransaction(TransactionDto transactionDto) {
        TransactionCategoriesEntity transactionCategoriesEntity = transactionCategoriesDao
                .findBy(transactionDto.getTransactionCategoryId());
        AccountEntity accountEntity = accountDao.findBy(transactionDto.getAccountId());

        //change account balance
        accountEntity.setBalance(accountEntity.getBalance().subtract(transactionDto.getSum()));

        //setup current date
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());

        TransactionEntity transactionEntity = new TransactionEntity()
                .withTransactionCategory(transactionCategoriesEntity)
                .withAccount(accountEntity)
                .withSum(transactionDto.getSum())
                .withDate(sqlDate);


        transactionDao.insert(transactionEntity);
    }

    public List<TransactionCategoryDto> getAllCategoryTransactions() {
        return transactionCategoriesDao.findByAll()
                .stream()
                .map(new TransactionCategoryToDtoConverter()::convert)
                .collect(Collectors.toList());
    }

    public void addNewCategoryTransaction(TransactionCategoryDto transactionCategoryDto) {
        TransactionCategoriesEntity transactionCategoriesEntity = new TransactionCategoriesEntity()
                .withNameCategory(transactionCategoryDto.getCategoryName());
        transactionCategoriesDao.insert(transactionCategoriesEntity);
    }

    public List<TransactionDto> getAllTransactionsOnThisAccount(AccountDto accountDto) {
        AccountEntity accountEntity = accountDao.findBy(accountDto.getId());
        return accountEntity.getTransactionsList()
                .stream()
                .map(new TransactionToDtoConverter()::convert)
                .collect(Collectors.toList());
    }
}
